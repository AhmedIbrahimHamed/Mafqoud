package com.project.graduation.welcomeback;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import java.util.HashMap;
import java.util.List;

import static android.media.CamcorderProfile.get;

public class NotificationActivity extends AppCompatActivity {

    private List<Report> notificationArrayList;
    private RecyclerView notificationRecyclerView;
    private NotificationAdapter mAdapter;

    private FirebaseAuth mFirebaseAuth;                 //Used to get current user.

    private FirebaseDatabase mFirebaseDatabase;         //Firebase instance.

    private DatabaseReference mNotificationsRef;                 //Used to get the notification key of suspects reports.

    private DatabaseReference mSuspectsReportsRef;      //Used to get the suspect report report.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        notificationArrayList = new ArrayList<>();
        notificationRecyclerView = (RecyclerView) findViewById(R.id.list);
        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NotificationAdapter(getApplicationContext());
        notificationRecyclerView.setAdapter(mAdapter);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        String userID = mFirebaseAuth.getCurrentUser().getUid();
        mNotificationsRef = mFirebaseDatabase.getReference().child("Users")
                .child(userID).child("Notifications");

        mNotificationsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                LocalNotification mNotification = dataSnapshot.getValue(LocalNotification.class);
                String reportKeyRef = mNotification.getmRef();

                mSuspectsReportsRef = mFirebaseDatabase.getReference()
                        .child("reports").child("suspect_reports").child(reportKeyRef);

                mSuspectsReportsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Report report = dataSnapshot.getValue(Report.class);
                        notificationArrayList.add(report);
                        mAdapter.addReport(report);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        notificationRecyclerView.addOnItemTouchListener(new RecyclerItemClickHandler(getApplicationContext(), notificationRecyclerView, new RecyclerItemClickHandler.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int itemPosition = notificationRecyclerView.getChildLayoutPosition(view);
                Intent intent = new Intent(getApplicationContext(), ReportDetailsActivity.class);
                Report clickedReport = notificationArrayList.get(itemPosition);
                //Adds the report fields to the intent
                intent.putExtra("REPORT_PHOTO", clickedReport.getPhoto());
                intent.putExtra("REPORT_NAME", clickedReport.getName());
                intent.putExtra("REPORT_AGE", clickedReport.getAge());
                intent.putExtra("REPORT_GENDER", clickedReport.getGender());
                intent.putExtra("REPORT_LOCATION", clickedReport.getLocation());
                intent.putExtra("REPORT_MORE_INFO", clickedReport.getMoreInfo());
                intent.putExtra("REPORT_CONTACT_INFO", clickedReport.getContactInfo());
                //Starts the ReportDetailsActivity.
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));

    }

}