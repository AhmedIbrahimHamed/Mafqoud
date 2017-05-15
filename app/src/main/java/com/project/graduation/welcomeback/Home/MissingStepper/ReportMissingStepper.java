package com.project.graduation.welcomeback.Home.MissingStepper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.project.graduation.welcomeback.Home.Data.DataManger;
import com.project.graduation.welcomeback.Home.Data.Report;
import com.project.graduation.welcomeback.R;
import com.stepstone.stepper.StepperLayout;

import java.util.ArrayList;

import static android.icu.lang.UProperty.AGE;

public class ReportMissingStepper extends AppCompatActivity implements DataManger {

    private static final String CURRENT_STEP_POSITION_KEY = "position";

    private static final String DATA = "data";

    private ArrayList<String> mReportArrayList;

    private StepperLayout mStepperLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Report Missing");

        setContentView(R.layout.activity_report_missing_stepper);
        mStepperLayout =  (StepperLayout) findViewById(R.id.stepperLayout);
        int startingStepPosition = savedInstanceState != null ? savedInstanceState.getInt(CURRENT_STEP_POSITION_KEY) : 0;
        mReportArrayList = savedInstanceState != null ? savedInstanceState.getStringArrayList(DATA) : null;
        mStepperLayout.setAdapter(new ReportMissingStepperAdapter(getSupportFragmentManager(), this), startingStepPosition);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(CURRENT_STEP_POSITION_KEY, mStepperLayout.getCurrentStepPosition());
        outState.putStringArrayList(DATA,mReportArrayList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        final int currentStepPosition = mStepperLayout.getCurrentStepPosition();
        if (currentStepPosition > 0) {
            mStepperLayout.onBackClicked();
        } else {
            finish();
        }
    }

    @Override
    public void saveData(ArrayList<String> data) {
        mReportArrayList = data;
    }

    public ArrayList<String> getData() {
        return mReportArrayList;
    }

}