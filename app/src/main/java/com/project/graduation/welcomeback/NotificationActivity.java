package com.project.graduation.welcomeback;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.graduation.welcomeback.Home.Data.Report;
import com.project.graduation.welcomeback.Home.RecyclerItemClickHandler;

import com.project.graduation.welcomeback.Home.ReportDetailsActivity;

import java.util.ArrayList;

import static android.media.CamcorderProfile.get;

public class NotificationActivity extends AppCompatActivity {

    private ArrayList<Report> notificationArrayList;
    private RecyclerView notificationRecyclerView;
    private NotificationAdapter mAdapter;

    private FirebaseAuth mFirebaseAuth;                 //Used to get current user.

    private FirebaseDatabase mFirebaseDatabase;         //Firebase instance.

    private DatabaseReference mNotificationRef;         //Used to get the notification key of suspects reports.

    private DatabaseReference mSuspectsReportsRef;      //Used to get the suspect report report.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        notificationArrayList = new ArrayList<>();
        notificationRecyclerView = (RecyclerView) findViewById(R.id.list);
        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NotificationAdapter();
        notificationRecyclerView.setAdapter(mAdapter);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mNotificationRef = mFirebaseDatabase.getReference().child("Users")
                .child(mFirebaseAuth.getCurrentUser().getUid()).child("Notifications");

        mNotificationRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String suspectReportKey = dataSnapshot.getValue(String.class);
                mSuspectsReportsRef = mFirebaseDatabase.getReference().child("reports")
                        .child("suspect_reports")
                        .child(suspectReportKey);

                mSuspectsReportsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Report report = dataSnapshot.getValue(Report.class);
                        notificationArrayList.add(report);    //adding the report to my reports list.
                        mAdapter.addReport(report);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override

            public void onCancelled(DatabaseError databaseError) {}
        });

       notificationRecyclerView.addOnItemTouchListener(new RecyclerItemClickHandler(getApplicationContext(), notificationRecyclerView, new RecyclerItemClickHandler.OnItemClickListener() {
           @Override
           public void onItemClick(View view, int position) {
               int itemPosition = notificationRecyclerView.getChildLayoutPosition(view);
               Intent intent = new Intent(getApplicationContext(),ReportDetailsActivity.class);
               Report clickedReport = notificationArrayList.get(itemPosition);
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