package com.project.graduation.welcomeback.Home;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * created by: TODO: Enter your name here
 */

import android.support.v4.app.Fragment;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.graduation.welcomeback.Home.Data.Report;
import com.project.graduation.welcomeback.R;

import java.util.ArrayList;
import java.util.List;

import static android.media.CamcorderProfile.get;

/**
 * this class represents the gallery of the missing and the suspects.
 * when we press on a pic it open details page of this picture.
 */
public class GalleryFragment extends Fragment {

    private FirebaseAuth mFirebaseAuth;                 //Used to get current user.

    private FirebaseDatabase mFirebaseDatabase;         //Firebase instance.

    private DatabaseReference mReportsRef;              //Used to get reporters reference in the database.

    private Switch mSwitchButton;                       //Used to change between suspects/missing reporters.

    private RecyclerView mRecyclerView;                 //Recycler view for photos in galleries.

    private ArrayList<Report> mSelectedReports;          //List for the missing reports.

    private GalleriesAdapter mAdapter;                  //Adapter for the photos recycler view.

    private RecyclerView.LayoutManager layoutManager;   //Used to define how the photos will appear.

    private TextView mErrorMassage;                     //Used when the recycler is empty or has errors.

    private ChildEventListener mChildEventListener;     //Used to get data from the database.

    public GalleryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        mErrorMassage = (TextView) view.findViewById(R.id.gallery_error_empty_recycler);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.gallery_RecyclerView);
        mSwitchButton = (Switch) view.findViewById(R.id.gallery_switch_button);

        mSelectedReports = new ArrayList<>();

        //Show 3 aligning photos when orientation is portrait and 4 when landscape
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(getContext(), 3);
        } else {
            layoutManager = new GridLayoutManager(getContext(), 4);
        }

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new GalleriesAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();

        //Getting reference to the missing reports.
        mReportsRef = mFirebaseDatabase.getReference().child("reports")
                .child("missing_reports");

        initiateDatabaseReadListener();

        addOnPhotoTouchListener();

        mSwitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mReportsRef.removeEventListener(mChildEventListener);
                mAdapter.clear();
                mSelectedReports.clear();
                if(isChecked){
                    mReportsRef = mFirebaseDatabase.getReference().child("reports")
                            .child("suspect_reports");
                }else {
                    mReportsRef = mFirebaseDatabase.getReference().child("reports")
                            .child("missing_reports");
                }
                mReportsRef.addChildEventListener(mChildEventListener);
            }
        });

        return view;
    }


    private void initiateDatabaseReadListener() {
        //assign a listener to the database if there's non
            mChildEventListener = new ChildEventListener() {
                //called when a new report is added and also called on existing reporters when first adding the listener.
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    //getting the report from reports reference in the database
                    Report mReport = dataSnapshot.getValue(Report.class);
                    mSelectedReports.add(mReport);
                    mAdapter.addPhotosUrl(mReport.getPhoto());
                    }

                public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
                public void onChildRemoved(DataSnapshot dataSnapshot) {}
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
                public void onCancelled(DatabaseError databaseError) {}
            };
        mReportsRef.addChildEventListener(mChildEventListener);      //Adds the listener to the missing reports in the first time.
        }

    private void addOnPhotoTouchListener(){

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickHandler(getContext(),
                mRecyclerView,new RecyclerItemClickHandler.OnItemClickListener(){

            @Override
            public void onItemClick(View view, int position) {
                //handle click events here
                int itemPosition = mRecyclerView.getChildLayoutPosition(view);
                Intent intent = new Intent(getActivity(),ReportDetailsActivity.class);
                Report clickedReport = mSelectedReports.get(itemPosition);
                //Adds the report fields to the intent
                intent.putExtra("REPORT_PHOTO",clickedReport.getPhoto());
                intent.putExtra("REPORT_NAME",clickedReport.getName());
                intent.putExtra("REPORT_AGE",clickedReport.getAge());
                intent.putExtra("REPORT_GENDER",clickedReport.getGender());
                intent.putExtra("REPORT_LOCATION",clickedReport.getLocation());
                intent.putExtra("REPORT_MORE_INFO",clickedReport.getMoreInfo());
                intent.putExtra("REPORT_CONTACT_INFO",clickedReport.getContactInfo());
                //Starts the ReportDetailsActivity.
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
    }


}
