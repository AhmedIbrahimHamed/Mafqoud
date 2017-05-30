package com.project.graduation.welcomeback;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.graduation.welcomeback.Home.Data.Report;

import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static android.R.attr.key;

/**
 * Created by ayyad on 5/22/2017.
 */

public class NotificationJob extends Job {
    static final String TAG = "show_notification_job_tag";

    @NonNull
    @Override
    protected Result onRunJob(Params params) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseDatabase.getInstance().getReference().child("Users")
                .child(user.getUid())
                .addListenerForSingleValueEvent
                        (new ValueEventListener() {
                             @Override
                             public void onDataChange(DataSnapshot dataSnapshot) {
                                 User user = dataSnapshot.getValue(User.class);
                                 List<String> reports = user.getmMissingReportsRefrences();

                                 for (final String report : reports) {
                                     Firebase.setAndroidContext(getContext());
                                     Firebase ref = new Firebase(report);
                                     ref.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                                         @Override
                                         public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                                             Report missingReport = dataSnapshot.getValue(Report.class);

                                             if (missingReport.isFound() == false) {
                                                 Utilities.recognizeInLostGallery(getContext(), missingReport.getPhoto(), report);
                                             }
                                         }

                                         @Override
                                         public void onCancelled(FirebaseError firebaseError) {

                                         }
                                     });
                                 }
                             }

                             @Override
                             public void onCancelled(DatabaseError databaseError) {

                             }
                         }
                        );


        return Result.SUCCESS;
    }

    static void schedulePeriodic() {
        new JobRequest.Builder(NotificationJob.TAG)
                .setPeriodic(TimeUnit.MINUTES.toMillis(15), TimeUnit.MINUTES.toMillis(5))
                .setUpdateCurrent(true)
                .setPersisted(true)
                .build()
                .schedule();
    }
}
