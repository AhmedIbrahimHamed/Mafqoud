package com.project.graduation.welcomeback;

/**
 * created by: Ayyad Shenouda
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * this class represent the adapter hold the fragments.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    /** Context of the app */
    private Context mContext;

    public PagerAdapter(FragmentManager fm,Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new MissingFragment();
        } else if (position == 1) {
            return new SuspectsFragment();
        } else {
            return new GalleryFragment();
        }
    }

    /**
     * Return the total number of pages.
     */
    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.missing);
        } else if (position == 1) {
            return mContext.getString(R.string.suspects);
        } else {
            return mContext.getString(R.string.gallery);
        }
    }
}