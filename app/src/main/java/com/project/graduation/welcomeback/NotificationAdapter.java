package com.project.graduation.welcomeback;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.core.Repo;
import com.project.graduation.welcomeback.Home.Data.Report;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhammad Attia on 5/24/2017.
 */




public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<Report> mNotificationList ;

    public NotificationAdapter() {
        this.mNotificationList = new ArrayList<>();
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        // Inflate the custom layout
        View view = LayoutInflater.from(context).inflate(R.layout.single_notification, parent, false);
        // Return a new holder instance
        NotificationViewHolder holder = new NotificationViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        //TODO : Add your code ya muhammad :D

        holder.name.setText(mNotificationList.get(position).getName());
        holder.age.setText(mNotificationList.get(position).getAge());
        holder.gender.setText(mNotificationList.get(position).getGender());
        holder.location.setText(mNotificationList.get(position).getLocation());
        holder.photo.setImageResource(Integer.valueOf(mNotificationList.get(position).getPhoto()));

    }

    @Override
    public int getItemCount() {
        return mNotificationList.size();
    }

    public void addReport(Report report){
        mNotificationList.add(report);
        notifyDataSetChanged();
    }


    public static class NotificationViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView age;
        public TextView gender;
        public TextView location;
        public ImageView photo ;


        public NotificationViewHolder(View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.name);
            age = (TextView)itemView.findViewById(R.id.age);
            gender = (TextView)itemView.findViewById(R.id.gender);
            location = (TextView)itemView.findViewById(R.id.location);
            photo =(ImageView)itemView.findViewById(R.id.person_image);
            
        }

    }

}
