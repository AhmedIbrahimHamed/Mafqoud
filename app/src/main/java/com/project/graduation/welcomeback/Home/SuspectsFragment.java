package com.project.graduation.welcomeback.Home;

/**
 * created by: TODO: Enter your name here
 */

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import com.project.graduation.welcomeback.Home.SuspectStepper.ReportSuspectStepper;
import com.project.graduation.welcomeback.R;

import java.util.ArrayList;


/**
 * this class represent the suspects people that the user report about and make a new report.
 */
public class SuspectsFragment extends Fragment {

    private FirebaseAuth mFirebaseAuth;                 //Used to get current user.

    private FirebaseDatabase mFirebaseDatabase;         //Firebase instance.

    private DatabaseReference mUserSuspectsReportsRef;   //Used to get user missing reporters.

    private RecyclerView mRecyclerView;                 //Recycler view for photos in galleries.

    private UserGalleriesAdapter mAdapter;              //Adapter for the user galleries recycler view.

    private ArrayList<Report> mSuspectsReports;         //List of the user's suspect reporters.

    private RecyclerView.LayoutManager layoutManager;   //Used to define how the photos will appear.

    private TextView mErrorMassage;                     //Used when the recycler is empty or has errors.

    private ChildEventListener mChildEventListener;     //Used to get data from the database.

    private FloatingActionButton mFloatingActionButton; //used to add reports.

    public SuspectsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_suspects, container, false);

        mErrorMassage = (TextView) view.findViewById(R.id.suspect_error_empty_recycler);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.suspect_RecyclerView);

        mSuspectsReports = new ArrayList<>();

        layoutManager = new LinearLayoutManager(getContext());

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new UserGalleriesAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();

        //Get reference to the user's missing reports
        mUserSuspectsReportsRef = mFirebaseDatabase.getReference().child("Users")
                .child(mFirebaseAuth.getCurrentUser().getUid()).child("mSuspectsReportsRefrences");

        attachDatabaseReadListener();       //Attach a listener to read the data pass it to the adapter.

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickHandler(getContext(),
                mRecyclerView,new RecyclerItemClickHandler.OnItemClickListener(){

            @Override
            public void onItemClick(View view, int position) {
                //handle click events here
                int itemPosition = mRecyclerView.getChildLayoutPosition(view);
                Intent intent = new Intent(getActivity(),ReportDetailsActivity.class);
                Report clickedReport = mSuspectsReports.get(itemPosition);
                //Adds the report fields to the intent
                intent.putExtra("REPORT_NAME",clickedReport.getName());
                intent.putExtra("REPORT_AGE",clickedReport.getAge());
                intent.putExtra("REPORT_GENDER",clickedReport.getGender());
                intent.putExtra("REPORT_LOCATION",clickedReport.getLocation());
                intent.putExtra("REPORT_MORE_INFO",clickedReport.getMoreInfo());
                intent.putExtra("REPORT_CONTACT_INFO",clickedReport.getContactInfo());
                intent.putExtra("REPORT_PHOTO",clickedReport.getPhoto());
                //start the ReportDetailsActivity.
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));

        mFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.suspect_floating_button);
        //Starts the form of adding a suspect report.
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(getActivity(), ReportSuspectStepper.class);
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
                    //getting the string report from database
                    String strReport = dataSnapshot.getValue(String.class);
                    //getting the key of the report
                    Uri uri = Uri.parse(strReport);
                    String path = uri.getPath();
                    String keyStr = path.substring(path.lastIndexOf('/') + 1);
                    //Reference for the
                     DatabaseReference mSuspectsReportRef = mFirebaseDatabase.getReference().child("reports")
                            .child("suspect_reports")
                            .child(keyStr);

                    mSuspectsReportRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Report report = dataSnapshot.getValue(Report.class);
                            mSuspectsReports.add(report);       //Adding the report to the list
                            mAdapter.addReport(report);         //passing the reports to the adapter
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
            mUserSuspectsReportsRef.addChildEventListener(mChildEventListener);
        }
    }

}
