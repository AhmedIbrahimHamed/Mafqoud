package com.project.graduation.welcomeback;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result=inflater.inflate(R.layout.fragment_home, container, false);

        // Inflate the layout for this fragment

        // Find the view pager that will allow the user to swipe between fragments
        // Create an adapter that knows which fragment should be shown on each page
        PagerAdapter myPagerAdapter = new PagerAdapter(getChildFragmentManager(),getContext());
        ViewPager viewPager = (ViewPager) result.findViewById(R.id.viewpager);
        // Set the adapter onto the view pager
        viewPager.setAdapter(myPagerAdapter);
        // Find the tab layout that shows the tabs
        TabLayout tabLayout = (TabLayout) result.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        return result;
    }

}
