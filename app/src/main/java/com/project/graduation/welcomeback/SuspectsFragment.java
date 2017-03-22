package com.project.graduation.welcomeback;

/**
 * created by: TODO: Enter your name here
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * this class represent the suspects people that the user report about and make a new report.
 */

public class SuspectsFragment extends Fragment {


    public SuspectsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_suspects, container, false);
    }

}
