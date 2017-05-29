package com.project.graduation.welcomeback.Home;

/**
 * created by: Ahmed
 */

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.graduation.welcomeback.Home.Data.Report;
import com.project.graduation.welcomeback.Home.MissingStepper.ReportMissingStepper;
import com.project.graduation.welcomeback.R;

import java.util.ArrayList;

/**
 * this class represent the missing people that the user report about and make a new report.
 */
public class MissingFragment extends Fragment  {

    private FirebaseAuth mFirebaseAuth;                 //Used to get current user.

    private FirebaseDatabase mFirebaseDatabase;         //Firebase instance.

    private DatabaseReference mUserMissingReportsRef;   //Used to get user missing reporters.

    private FloatingActionButton mFloatingActionButton; //Used to add reports.

    private RecyclerView mRecyclerView;                 //Recycler view for photos in galleries.

    private ArrayList<Report> mMissingReports;          //List for the missing reports.

    private GalleriesAdapter mAdapter;                  //Adapter for the photos recycler view.

    private RecyclerView.LayoutManager layoutManager;   //Used to define how the photos will appear.

    private TextView mErrorMassage;                     //Used when the recycler is empty or has errors.

    private ChildEventListener mChildEventListener;     //Used to get data from the database.

    public MissingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_missing, container, false);

        mErrorMassage = (TextView) view.findViewById(R.id.missing_error_empty_recycler);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.missing_RecyclerView);

        mMissingReports = new ArrayList<>();

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

        //Get reference to the user's missing reports
        mUserMissingReportsRef = mFirebaseDatabase.getReference().child("Users")
                .child(mFirebaseAuth.getCurrentUser().getUid()).child("mMissingReportsRefrences");

        attachDatabaseReadListener();   //Attach a listener to read the data pass it to the adapter.

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickHandler(getContext(),
                mRecyclerView,new RecyclerItemClickHandler.OnItemClickListener(){

            @Override
            public void onItemClick(View view, int position) {
                //handle click events here
                int itemPosition = mRecyclerView.getChildLayoutPosition(view);
                Intent intent = new Intent(getActivity(),ReportDetailsActivity.class);
                Report clickedReport = mMissingReports.get(itemPosition);
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

        mFloatingActionButton =(FloatingActionButton) view.findViewById(R.id.missing_floating_button);
        // Gets called when floating button gets add and start the steppers for adding missing reports.
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReportMissingStepper.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void attachDatabaseReadListener() {
        //assign a listener to the database if there's non
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                //called when a new report is added and also called on existing reporters when first adding the listener.
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    //getting the string report from user in the database
                    String strReport = dataSnapshot.getValue(String.class);
                    //getting the key of the report
                    Uri uri = Uri.parse(strReport);
                    String path = uri.getPath();
                    String keyStr = path.substring(path.lastIndexOf('/') + 1);
                    //Reference for the report
                    DatabaseReference mMissingReportRef = mFirebaseDatabase.getReference().child("reports")
                            .child("missing_reports")
                            .child(keyStr);

                    mMissingReportRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Report report = dataSnapshot.getValue(Report.class);
                            mMissingReports.add(report);    //adding the report to my reports list.
                            String photo = report.getPhoto();
                            mAdapter.addPhotosUrl(photo);   //pass the photos of the reports to the adapter
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

                public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
                public void onChildRemoved(DataSnapshot dataSnapshot) {}
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
                public void onCancelled(DatabaseError databaseError) {}
            };
            mUserMissingReportsRef.addChildEventListener(mChildEventListener);  //Add a listener to the user's missing reports reference
        }
    }

}
