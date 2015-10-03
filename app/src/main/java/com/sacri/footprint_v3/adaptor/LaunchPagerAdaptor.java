package com.sacri.footprint_v3.adaptor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sacri.footprint_v3.fragments.DisplayOnMapFragment;
import com.sacri.footprint_v3.fragments.FeedEventFragment;
import com.sacri.footprint_v3.fragments.FeedPlaceFragment;
import com.sacri.footprint_v3.fragments.FeedStoryFragment;

/**
 * Created by Sagar Deswal on 21/09/15.
 */
public class LaunchPagerAdaptor extends FragmentStatePagerAdapter {

    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 2;


    public LaunchPagerAdaptor(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                // The first section of the app is the most interesting -- it offers
                // a launchpad into the other demonstrations in this example application.
                return new FeedStoryFragment();

            case 1:

                return new FeedPlaceFragment();

            case 2:

                return new FeedEventFragment();

            case 3:

//                return new FeedEventFragment();
                return new DisplayOnMapFragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}