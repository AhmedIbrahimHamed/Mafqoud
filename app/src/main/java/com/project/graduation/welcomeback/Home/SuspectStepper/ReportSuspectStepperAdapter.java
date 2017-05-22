package com.project.graduation.welcomeback.Home.SuspectStepper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.project.graduation.welcomeback.Home.MissingStepper.ReportMissingStep1;
import com.project.graduation.welcomeback.Home.MissingStepper.ReportMissingStep2;
import com.project.graduation.welcomeback.Home.MissingStepper.ReportMissingStep3;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;

/**
 * Created by WinDows8 on 5/22/2017.
 */

public class ReportSuspectStepperAdapter extends AbstractFragmentStepAdapter {

    public ReportSuspectStepperAdapter(@NonNull FragmentManager fm, @NonNull Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {
        //TODO : change steps
        switch (position) {
            case 0:
                return ReportSuspectStep1.newInstance();
            case 1:
                return ReportSuspectStep2.newInstance();
            case 2:
                return ReportSuspectStep3.newInstance();
            default:
                throw new IllegalArgumentException("Unsupported position: " + position);
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}