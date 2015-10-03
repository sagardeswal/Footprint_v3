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
import com.sacri.footprint_v3.callback.GetPlaceCallback;
import com.sacri.footprint_v3.dbaccess.ServerRequests;
import com.sacri.footprint_v3.entity.PlaceDetails;
import com.sacri.footprint_v3.adaptor.FeedPlaceRecyclerAdaptor;

import java.util.ArrayList;

public class FeedPlaceFragment extends Fragment {

    private static final String FOOTPRINT_LOGGER = "com.sacri.footprint_v3";
    private RecyclerView rv;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FeedPlaceRecyclerAdaptor feedPlaceRecyclerAdaptor;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_place_feed, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout)v.findViewById(R.id.swipeRefreshLayoutPlace);
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
        feedPlaceRecyclerAdaptor.notifyDataSetChanged();
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
        getPlacesInBackground();
        feedPlaceRecyclerAdaptor = new FeedPlaceRecyclerAdaptor(getActivity(), new ArrayList<PlaceDetails>());
        recyclerView.setAdapter(feedPlaceRecyclerAdaptor);
    }

    public void getPlacesInBackground(){
        ServerRequests serverRequests = new ServerRequests(getActivity());
        serverRequests.fetchPlaceDataInBackground("New Delhi", new GetPlaceCallback() {
            @Override
            public void done(ArrayList<PlaceDetails> placeDetailsArrayList) {

                if (placeDetailsArrayList == null) {
                    Log.i(FOOTPRINT_LOGGER, "placeDetailsArrayList is null");

                } else {
                    Log.i(FOOTPRINT_LOGGER, "placeDetailsArrayList.size(): " + placeDetailsArrayList.size());
                    if (feedPlaceRecyclerAdaptor != null) {
                        ((FeedActivity)getActivity()).setmPlaceDetailsArrayList(placeDetailsArrayList);
                        feedPlaceRecyclerAdaptor.setmValues(placeDetailsArrayList);
                        feedPlaceRecyclerAdaptor.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
