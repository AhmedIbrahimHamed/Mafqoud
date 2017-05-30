package com.project.graduation.welcomeback;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.project.graduation.welcomeback.Home.RecyclerItemClickHandler;

import com.project.graduation.welcomeback.Home.ReportDetailsActivity;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    private ArrayList notificationArrayList;
    private RecyclerView notificationRecyclerView;
    private NotificationAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        notificationArrayList = new ArrayList<>();
        notificationRecyclerView = (RecyclerView) findViewById(R.id.list);
        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NotificationAdapter(notificationArrayList);
        notificationRecyclerView.setAdapter(mAdapter);

       notificationRecyclerView.addOnItemTouchListener(new RecyclerItemClickHandler(getApplicationContext(), notificationRecyclerView, new RecyclerItemClickHandler.OnItemClickListener() {
           @Override
           public void onItemClick(View view, int position) {
               int itemPosition = notificationRecyclerView.getChildLayoutPosition(view);
               Intent intent = new Intent(getApplicationContext(),ReportDetailsActivity.class);
               startActivity(intent);
           }
           @Override
           public void onItemLongClick(View view, int position) {

           }
       }));

    }


    }