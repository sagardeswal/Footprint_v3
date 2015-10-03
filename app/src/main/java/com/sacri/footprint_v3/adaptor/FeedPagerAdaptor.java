package com.sacri.footprint_v3.adaptor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class FeedPagerAdaptor extends FragmentPagerAdapter {
    private final List<Fragment> mFragments = new ArrayList<>();
    private final List<String> mFragmentTitles = new ArrayList<>();
    private static final String FOOTPRINT_LOGGER = "com.sacri.footprint_v3";

    public FeedPagerAdaptor(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String title) {
        Log.i(FOOTPRINT_LOGGER, "Fragment Tag=" + fragment.getTag());
        mFragments.add(fragment);
        mFragmentTitles.add(title);
    }

    @Override
    public Fragment getItem(int position) { return mFragments.get(position);}

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitles.get(position);
    }
}
