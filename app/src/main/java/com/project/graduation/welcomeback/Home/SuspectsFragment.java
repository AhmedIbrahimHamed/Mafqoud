package com.project.graduation.welcomeback.Home;

/**
 * created by: TODO: Enter your name here
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.graduation.welcomeback.Home.MissingStepper.ReportMissingStepper;
import com.project.graduation.welcomeback.Home.SuspectStepper.ReportSuspectStepper;
import com.project.graduation.welcomeback.R;


/**
 * this class represent the suspects people that the user report about and make a new report.
 */

public class SuspectsFragment extends Fragment {

    private FloatingActionButton mFloatingActionButton; //used to add reports

    public SuspectsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_suspects, container, false);
        mFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.suspect_floating_button);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(getActivity(), ReportSuspectStepper.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
