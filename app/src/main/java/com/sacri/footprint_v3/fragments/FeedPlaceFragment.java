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
import com.sacri.footprint_v3.entity.PlaceDetails;
import com.sacri.footprint_v3.utils.FeedPlaceRecyclerAdaptor;

import java.util.ArrayList;

public class FeedPlaceFragment extends Fragment {

    private static final String FOOTPRINT_LOGGER = "com.sacri.footprint_v3";
    private RecyclerView rv;
    private ArrayList<PlaceDetails> placeDetailsArrayList;


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

        placeDetailsArrayList =((FeedActivity) getActivity()).getMPlaceDetailsArrayList();

                if (placeDetailsArrayList == null) {
                    Log.i(FOOTPRINT_LOGGER, "placeDetailsArrayList is null");

                } else {
                    Log.i(FOOTPRINT_LOGGER, "placeDetailsArrayList size: " + placeDetailsArrayList.size());
                    setupRecyclerView(rv);
                }

        return rv;
    }

    private void setupRecyclerView(RecyclerView recyclerView ) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        FeedPlaceRecyclerAdaptor feedPlaceRecyclerAdaptor = new FeedPlaceRecyclerAdaptor(getActivity(), placeDetailsArrayList);
        ((FeedActivity)getActivity()).setFeedPlaceRecyclerAdaptor(feedPlaceRecyclerAdaptor);
        recyclerView.setAdapter(feedPlaceRecyclerAdaptor);
    }
}
