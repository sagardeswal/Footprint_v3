package com.sacri.footprint_v3.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sacri.footprint_v3.fragments.DisplayOnMapFragment;
import com.sacri.footprint_v3.fragments.FeedEventFragment;
import com.sacri.footprint_v3.fragments.FeedPostFragment;
import com.sacri.footprint_v3.fragments.FeedPlaceFragment;


public class FeedPagerAdaptor extends FragmentPagerAdapter {
    public FeedPagerAdaptor(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                // The first section of the app is the most interesting -- it offers
                // a launchpad into the other demonstrations in this example application.
                return new FeedPostFragment();

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
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
