package com.sacri.footprint_v3.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sacri.footprint_v3.R;
import com.sacri.footprint_v3.activities.FeedActivity;
import com.sacri.footprint_v3.entity.Story;
import com.sacri.footprint_v3.utils.FeedStoryRecyclerAdaptor;

import java.util.ArrayList;

public class FeedStoryFragment extends Fragment {

    private static final String FOOTPRINT_LOGGER = "com.sacri.footprint_v3";
    private RecyclerView rv;
    private ArrayList<Story> storyArrayList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_place_feed, container, false);

        storyArrayList =((FeedActivity) getActivity()).getmStoryArrayList();

        if (storyArrayList == null) {
            Log.i(FOOTPRINT_LOGGER, "storyArrayList is null");

        } else {
            Log.i(FOOTPRINT_LOGGER, "storyArrayList size: " + storyArrayList.size());
            setupRecyclerView(rv);
        }

        return rv;
    }

    private void setupRecyclerView(RecyclerView recyclerView ) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        FeedStoryRecyclerAdaptor feedStoryRecyclerAdaptor = new FeedStoryRecyclerAdaptor(getActivity(), storyArrayList);
        ((FeedActivity)getActivity()).setFeedStoryRecyclerAdaptor(feedStoryRecyclerAdaptor);
        recyclerView.setAdapter(feedStoryRecyclerAdaptor);
    }

}
