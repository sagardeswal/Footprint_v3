package com.sacri.footprint_v3.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sacri.footprint_v3.R;
import com.sacri.footprint_v3.activities.FeedActivity;
import com.sacri.footprint_v3.callback.GetStoryCallback;
import com.sacri.footprint_v3.dbaccess.ServerRequests;
import com.sacri.footprint_v3.entity.Story;
import com.sacri.footprint_v3.adaptor.FeedStoryRecyclerAdaptor;

import java.util.ArrayList;

public class FeedStoryFragment extends Fragment {

    private static final String FOOTPRINT_LOGGER = "com.sacri.footprint_v3";
    private RecyclerView rv;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FeedStoryRecyclerAdaptor feedStoryRecyclerAdaptor;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_story_feed, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout)v.findViewById(R.id.swipeRefreshLayoutStory);
        rv = (RecyclerView) v.findViewById(R.id.recyclerview);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });

        setupRecyclerView(rv);
        return v;
    }

    void refreshItems() {
        Log.i(FOOTPRINT_LOGGER,"refreshItems()");
        // Load items
        // ...
        feedStoryRecyclerAdaptor.notifyDataSetChanged();
        // Load complete
        onItemsLoadComplete();
    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
    }
    private void setupRecyclerView(RecyclerView recyclerView ) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        getStoryDataInBackground();
        feedStoryRecyclerAdaptor = new FeedStoryRecyclerAdaptor(getActivity(), new ArrayList<Story>());
        recyclerView.setAdapter(feedStoryRecyclerAdaptor);
    }

    private void getStoryDataInBackground() {
        ServerRequests serverRequests = new ServerRequests(getActivity());
        serverRequests.fetchStoryDataInBackground(((FeedActivity)getActivity()).getLoggedUser().getUserID().toString(), new GetStoryCallback() {
            @Override
            public void done(ArrayList<Story> storyArrayList) {

                if (storyArrayList == null) {
                    Log.i(FOOTPRINT_LOGGER, "storyArrayList is null");

                } else {
                    Log.i(FOOTPRINT_LOGGER, "storyArrayList.size(): " + storyArrayList.size());
                    if (feedStoryRecyclerAdaptor != null) {
                        feedStoryRecyclerAdaptor.setmValues(storyArrayList);
                        feedStoryRecyclerAdaptor.notifyDataSetChanged();
                    }
                }
            }
        });
    }

}
