package com.project.graduation.welcomeback;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.job.JobScheduler;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.evernote.android.job.Job;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kairos.*;
import com.project.graduation.welcomeback.Home.Data.Report;
import com.project.graduation.welcomeback.Home.MissingStepper.ReportMissingStepper;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;

import static android.R.attr.name;
import static java.lang.Boolean.TRUE;
import static java.security.AccessController.getContext;

/**
 * Created by ayyad on 5/15/2017.
 */

public class Utilities {

    private static Kairos mKairosRef;

    private final static String KAIROS_GALLERY = "galleryTest001";
    public static final String KAIROS_SELECTOR = "SETPOSE";
    public static final String KAIROS_MULTIPLE_FACES = "true";
    public static final String KAIROS_MIN_HEAD_SCALE = "0.125";
    public static final String KAIROS_MAX_RESULTS = "5";
    public static final String KAIROS_THRESHOLD = "0.70";
    public static final String KAIROS_API_ID = "76f8b3c5";
    public static final String KAIROS_API_KEY = "c9dcb07777b9954ff34106d333f9ce29";


    public static void enrollImageToKairosFoundGallery(Context context, String photoUrl, String name) {
        try {
            mKairosRef = new Kairos();
            mKairosRef.setAuthentication(context, KAIROS_API_ID, KAIROS_API_KEY);
            mKairosRef.enroll(photoUrl, name, KAIROS_GALLERY, KAIROS_SELECTOR,
                    KAIROS_MULTIPLE_FACES, KAIROS_MIN_HEAD_SCALE, new KairosListener() {
                        @Override
                        public void onSuccess(String s) {
                            Log.d("done", s);
                        }

                        @Override
                        public void onFail(String s) {
                            Log.d("fail", s);
                        }
                    }

            );
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    public static void recognizeInLostGallery(final Context context, final String photoUrl, final String report) {
        try {
            mKairosRef = new Kairos();
            mKairosRef.setAuthentication(context, KAIROS_API_ID, KAIROS_API_KEY);
            mKairosRef.recognize(photoUrl, KAIROS_GALLERY, KAIROS_SELECTOR, KAIROS_THRESHOLD, KAIROS_MIN_HEAD_SCALE, KAIROS_MAX_RESULTS, new KairosListener() {
                @Override
                public void onSuccess(String s) {
                    Log.d("done", s);

                    JSONObject rootNode = null;
                    try {
                        rootNode = new JSONObject(s);
                        JSONArray imgArray = rootNode.optJSONArray("images");
                        if (imgArray != null) {

                            JSONObject firstImgJSONObject = imgArray.optJSONObject(0);

                            JSONArray candidatesArray = firstImgJSONObject.optJSONArray("candidates");

                            if (candidatesArray != null) {
                                JSONObject firstCandidateJSONObject = candidatesArray.optJSONObject(0);

                                //get first key i.e. imageID in jsonObject


                                String imageID = firstCandidateJSONObject.getString("subject_id");
                                Log.i("match", imageID);
                                //TODO: what happend in

                                LocalNotification localNotification = new LocalNotification("-" + imageID);
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                databaseReference.child("Users")
                                        .child(user.getUid())
                                        .child("Notifications")
                                        .push()
                                        .setValue(localNotification);

                                PendingIntent pi = PendingIntent.getActivity(context, 0,
                                        new Intent(context, NotificationActivity.class), 0);

                                Notification notification = new NotificationCompat.Builder(context)
                                        .setContentTitle("Matched")
                                        .setContentText("We found a match check it.")
                                        .setAutoCancel(true)
                                        .setContentIntent(pi)
                                        .setSmallIcon(R.mipmap.ic_launcher)
                                        .setShowWhen(true)
                                        .setColor(Color.BLUE)
                                        .setLocalOnly(true)
                                        .build();
                                NotificationManagerCompat.from(context)
                                        .notify(new Random().nextInt(), notification);

                                Firebase.setAndroidContext(context);
                                final Firebase ref = new Firebase(report);
                                ref.addListenerForSingleValueEvent
                                        (new com.firebase.client.ValueEventListener() {
                                            @Override
                                            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                                                Report missingReport = dataSnapshot.getValue(Report.class);
                                                if (missingReport != null) {
                                                    missingReport.setFound(true);
                                                    ref.setValue(missingReport);
                                                } else {
                                                    Log.i("missing report", "null");
                                                }
                                            }

                                            @Override
                                            public void onCancelled(FirebaseError firebaseError) {

                                            }
                                         }
                                        );
                            }
                        } else {
                            Log.v("Face recognition", "No Matching");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFail(String s) {
                    Log.d("rajuSR", s);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
