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
import com.sacri.footprint_v3.callback.GetEventCallback;
import com.sacri.footprint_v3.dbaccess.ServerRequests;
import com.sacri.footprint_v3.entity.EventDetails;
import com.sacri.footprint_v3.adaptor.FeedEventRecyclerAdaptor;

import java.util.ArrayList;

/**
 * Created by Sagar Deswal
 */
public class FeedEventFragment extends Fragment {
    private static final String FOOTPRINT_LOGGER = "com.sacri.footprint_v3";
    private RecyclerView rv;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FeedEventRecyclerAdaptor feedEventRecyclerAdaptor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_feed_event, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout)v.findViewById(R.id.swipeRefreshLayoutEvent);
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

    private void setupRecyclerView(RecyclerView recyclerView ) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        getEventsInBackground();
        feedEventRecyclerAdaptor = new FeedEventRecyclerAdaptor(getActivity(), new ArrayList<EventDetails>());
        recyclerView.setAdapter(feedEventRecyclerAdaptor);
    }

    private void getEventsInBackground(){
        ServerRequests serverRequests = new ServerRequests(getActivity());
        serverRequests.fetchEventDataInBackground("-1", new GetEventCallback() {
            @Override
            public void done(ArrayList<EventDetails> eventDetailsArrayList) {

                if (eventDetailsArrayList == null) {
                    Log.i(FOOTPRINT_LOGGER, "eventDetailsArrayList is null");

                } else {
                    Log.i(FOOTPRINT_LOGGER, "eventDetailsArrayList: " + eventDetailsArrayList.size());
                    if (feedEventRecyclerAdaptor != null) {
                        ((FeedActivity)getActivity()).setmEventDetailsArrayList(eventDetailsArrayList);
                        feedEventRecyclerAdaptor.setmValues(eventDetailsArrayList);
                        feedEventRecyclerAdaptor.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    void refreshItems() {
        Log.i(FOOTPRINT_LOGGER,"refreshItems()");
        // Load items
        // ...
        feedEventRecyclerAdaptor.notifyDataSetChanged();
        // Load complete
        onItemsLoadComplete();
    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
