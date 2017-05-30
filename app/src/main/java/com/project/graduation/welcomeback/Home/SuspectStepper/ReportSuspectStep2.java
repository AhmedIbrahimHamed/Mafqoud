package com.project.graduation.welcomeback.Home.SuspectStepper;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.project.graduation.welcomeback.Home.Data.DataManger;
import com.project.graduation.welcomeback.R;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.ArrayList;

/**
 * Created by WinDows8 on 5/22/2017.
 */

public class ReportSuspectStep2 extends Fragment implements BlockingStep {

    private DataManger mDataManager;

    private String mLocation,mContact,mMoreInfo;

    private ArrayList<String> mReportArrayList;

    private EditText mLocationEditText,mMoreInfoEditText,mContactInfoEditText;

    public static ReportSuspectStep2 newInstance(){
        return new ReportSuspectStep2();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.report_suspect_step2_fragment,container,false);
        mDataManager= (DataManger) getActivity();
        mLocationEditText = (EditText) view.findViewById(R.id.suspect_step2_location_editText);
        mContactInfoEditText = (EditText) view.findViewById(R.id.suspect_step2_contact_info_editText);
        mMoreInfoEditText = (EditText) view.findViewById(R.id.suspect_step2_moreInfo_editText);

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
        mReportArrayList = mDataManager.getData();
        mReportArrayList.add(3,mLocation);
        mReportArrayList.add(4,mContact);
        mReportArrayList.add(5,mMoreInfo);

        mDataManager.saveData(mReportArrayList);
        callback.goToNextStep();
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();

    }

    @Override
    public VerificationError verifyStep() {
        mLocation = mLocationEditText.getText().toString();
        mContact = mContactInfoEditText.getText().toString();
        mMoreInfo = mMoreInfoEditText.getText().toString();

        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {
        Toast.makeText(getActivity(),error.getErrorMessage(),Toast.LENGTH_LONG).show();
    }

}
