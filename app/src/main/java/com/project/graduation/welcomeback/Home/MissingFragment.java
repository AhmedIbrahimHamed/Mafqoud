package com.project.graduation.welcomeback.Home;

/**
 * created by: Ahmed
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.graduation.welcomeback.Home.MissingStepper.ReportMissingStepper;
import com.project.graduation.welcomeback.R;


/**
 * this class represent the missing people that the user report about and make a new report.
 */
public class MissingFragment extends Fragment  {

    private FloatingActionButton mFloatingActionButton; //used to add reports

    private static final int REPORT_MISSING_CD = 1;        //used to identify the request code;

    public MissingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_missing, container, false);

        mFloatingActionButton =(FloatingActionButton) view.findViewById(R.id.missing_floating_button);
        // Gets called when floating button gets add and start the steppers
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReportMissingStepper.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
