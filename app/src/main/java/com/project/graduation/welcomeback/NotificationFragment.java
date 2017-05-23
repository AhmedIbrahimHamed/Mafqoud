package com.project.graduation.welcomeback;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static android.R.attr.key;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {

    FirebaseDatabase mFirebaseDatabase;
    FirebaseAuth mFirebaseAuth;
    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String userID = mFirebaseAuth.getCurrentUser().getUid();
        mFirebaseDatabase.getReference().child("Users")
                .child(userID)
                .addListenerForSingleValueEvent
                        (new ValueEventListener() {
                             @Override
                             public void onDataChange(DataSnapshot dataSnapshot) {
                                 User user = dataSnapshot.getValue(User.class);
                                 List<String> notifications = user.getmNotifications();
                                 //TODO: user notifications list to do what you want
                             }

                             @Override
                             public void onCancelled(DatabaseError databaseError) {

                             }
                         }
                        );
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

}
