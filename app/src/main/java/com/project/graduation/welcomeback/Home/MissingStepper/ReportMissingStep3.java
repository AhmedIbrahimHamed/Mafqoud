package com.project.graduation.welcomeback.Home.MissingStepper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.project.graduation.welcomeback.Home.Data.DataManger;
import com.project.graduation.welcomeback.R;
import com.squareup.picasso.Picasso;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Ahmed on 5/15/2017.
 */

public class ReportMissingStep3  extends Fragment implements BlockingStep,View.OnClickListener {

    private View view;

    private DataManger mDataManager;

    private String mLocation,mContact,mMoreInfo;

    private static final int PICK_IMAGE_REQUEST = 1;

    private ArrayList<String> mReportArrayList;

    private Uri mSelectedImage;

    private ImageView mImageView;

    public static ReportMissingStep3 newInstance(){
        return new ReportMissingStep3();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mSelectedImage = data.getData();

            Picasso.with(getContext())
                    .load(mSelectedImage)
                    .resize(150,150)
                    .into(mImageView);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.report_missing_step3_fragment,container,false);
        mImageView = (ImageView) view.findViewById(R.id.missing_step3_image);
        mImageView.setOnClickListener(this);
        mDataManager= (DataManger) getActivity();

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
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        Toast.makeText(getActivity(),"sdasa",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }

    @Override
    public VerificationError verifyStep() {
        if(mSelectedImage == null){
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
        Toast.makeText(getActivity(),error.getErrorMessage(),Toast.LENGTH_LONG);
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
