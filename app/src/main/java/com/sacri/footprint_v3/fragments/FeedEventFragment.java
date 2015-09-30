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
import com.sacri.footprint_v3.entity.EventDetails;
import com.sacri.footprint_v3.utils.FeedEventRecyclerAdaptor;

import java.util.ArrayList;

/**
 * Created by Sagar Deswal
 */
public class FeedEventFragment extends Fragment {
    private static final String FOOTPRINT_LOGGER = "com.sacri.footprint_v3";
    private RecyclerView rv;
    private ArrayList<EventDetails> eventDetailsArrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rv = (RecyclerView)inflater.inflate(R.layout.fragment_feed_event, container, false);

        eventDetailsArrayList =((FeedActivity) getActivity()).getMEventDetailsArrayList();

        if (eventDetailsArrayList == null) {
            Log.i(FOOTPRINT_LOGGER, "eventDetailsArrayList is null");

        } else {
            Log.i(FOOTPRINT_LOGGER, "eventDetailsArrayList: " + eventDetailsArrayList.size());
            setupRecyclerView(rv);
        }
        return rv;
    }

    private void setupRecyclerView(RecyclerView recyclerView ) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        FeedEventRecyclerAdaptor feedEventRecyclerAdaptor = new FeedEventRecyclerAdaptor(getActivity(), eventDetailsArrayList);
        ((FeedActivity)getActivity()).setFeedEventRecyclerAdaptor(feedEventRecyclerAdaptor);
        recyclerView.setAdapter(feedEventRecyclerAdaptor);
    }
}
