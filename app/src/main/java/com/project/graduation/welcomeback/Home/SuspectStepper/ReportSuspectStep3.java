package com.project.graduation.welcomeback.Home.SuspectStepper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.graduation.welcomeback.Home.Data.DataManger;
import com.project.graduation.welcomeback.Home.Data.Report;
import com.project.graduation.welcomeback.Home.MissingStepper.ReportMissingStep3;
import com.project.graduation.welcomeback.R;
import com.project.graduation.welcomeback.User;
import com.squareup.picasso.Picasso;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by WinDows8 on 5/22/2017.
 */

public class ReportSuspectStep3 extends Fragment implements BlockingStep, View.OnClickListener {

    private View view;

    private DataManger mDataManager;

    private FirebaseDatabase mFirebaseDatabase;

    private DatabaseReference mReportDatabaseReference;

    private FirebaseStorage mFirebaseStorage;

    private FirebaseAuth mFirebaseAuth;

    private StorageReference mReportPhotosStorageReference;


    private static final int PICK_IMAGE_REQUEST = 1;

    private ArrayList<String> mReportArrayList;

    private Uri mSelectedImage;

    private ImageView mImageView;

    public static ReportSuspectStep3 newInstance() {
        return new ReportSuspectStep3();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mSelectedImage = data.getData();

            Picasso.with(getContext())
                    .load(mSelectedImage)
                    .resize(150, 150)
                    .into(mImageView);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.report_suspect_step3_fragment, container, false);
        mImageView = (ImageView) view.findViewById(R.id.suspect_step3_image);
        mImageView.setOnClickListener(this);
        mDataManager = (DataManger) getActivity();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();
        mReportDatabaseReference = mFirebaseDatabase.getReference().child("reports").child("suspect_reports");
        mReportPhotosStorageReference = mFirebaseStorage.getReference().child("reports_photos").child("suspect_photos");

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DataManger) {
            mDataManager = (DataManger) context;
        } else {
            throw new IllegalStateException("Activity must implement DataManager interface!");
        }
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
        callback.complete();
        StorageReference photoRef = mReportPhotosStorageReference
                .child(mSelectedImage.getLastPathSegment());
        photoRef.putFile(mSelectedImage).addOnSuccessListener((Activity) getContext(), new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri photoUrlDownload = taskSnapshot.getDownloadUrl();
                mReportArrayList.add(6, photoUrlDownload.toString());
                Report mReport = new Report(mReportArrayList);
                final DatabaseReference key = mReportDatabaseReference.push();
                key.setValue(mReport);

                //TODO: add ur test here.

                getActivity().finish();

                mFirebaseAuth = FirebaseAuth.getInstance();

                mFirebaseDatabase.getReference().child("Users")
                        .child(mFirebaseAuth.getCurrentUser().getUid())
                        .addListenerForSingleValueEvent
                                (new ValueEventListener() {
                                     @Override
                                     public void onDataChange(DataSnapshot dataSnapshot) {
                                         User user = dataSnapshot.getValue(User.class);
                                         user.addSuspect(key.toString());

                                         Log.e("sss", user.getmName());


                                         mFirebaseDatabase.getReference().child("Users").child(mFirebaseAuth.getCurrentUser().getUid()).setValue(user);

                                     }

                                     @Override
                                     public void onCancelled(DatabaseError databaseError) {

                                     }
                                 }
                                );

            }
        });
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {


    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }

    @Override
    public VerificationError verifyStep() {
        if (mSelectedImage == null) {
            return new VerificationError("Please select a photo of the missing person.");
        }

        return null;
    }

    @Override
    public void onSelected() {
        mReportArrayList = mDataManager.getData();
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        Toast.makeText(getActivity(), error.getErrorMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        // Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }
}
