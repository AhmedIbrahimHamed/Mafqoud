package com.project.graduation.welcomeback.Home.MissingStepper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;

/**
 * Created by WinDows8 on 5/12/2017.
 */

public class ReportMissingStepperAdapter extends AbstractFragmentStepAdapter {

    public ReportMissingStepperAdapter(@NonNull FragmentManager fm, @NonNull Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {
        switch (position) {
            case 0:
                return ReportMissingStep1.newInstance();
            case 1:
                return ReportMissingStep2.newInstance();
            case 2:
                return ReportMissingStep3.newInstance();
            default:
                throw new IllegalArgumentException("Unsupported position: " + position);
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}