package com.sacri.footprint_v3.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sacri.footprint_v3.fragments.FeedFragment;
import com.sacri.footprint_v3.fragments.ViewPlacesFragment;


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
                return new FeedFragment();

            case 1:

                return new ViewPlacesFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
