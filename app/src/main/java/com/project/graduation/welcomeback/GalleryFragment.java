package com.project.graduation.welcomeback;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * created by: TODO: Enter your name here
 */

import android.support.v4.app.Fragment;

/**
 * this class represents the gallery of the missings and the suspects.
 * when we press on a pic it open details page of this pic.
 */
public class GalleryFragment extends Fragment {


    public GalleryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }

}
