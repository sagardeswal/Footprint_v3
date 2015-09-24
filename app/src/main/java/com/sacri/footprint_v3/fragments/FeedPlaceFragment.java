package com.sacri.footprint_v3.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.sacri.footprint_v3.R;
import com.sacri.footprint_v3.activities.FeedActivity;
import com.sacri.footprint_v3.activities.ViewPlaceProfileActivity;
import com.sacri.footprint_v3.entity.PlaceDetails;
import com.sacri.footprint_v3.utils.FeedPlaceAdaptor;

import java.util.ArrayList;

public class FeedPlaceFragment extends Fragment {

    private static final String FOOTPRINT_LOGGER = "com.sacri.footprint_v3";
    private ListAdapter feedPlaceAdaptor;
    private View v;
    private ArrayList<PlaceDetails> placeDetailsArrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_place_feed, container, false);

        placeDetailsArrayList =((FeedActivity) getActivity()).getMPlaceDetailsArrayList();

                if (placeDetailsArrayList == null) {
                    Log.i(FOOTPRINT_LOGGER, "placeDetailsArrayList is null");
                    showErrorMessage();

                } else {
                    Log.i(FOOTPRINT_LOGGER, "placeDetailsArrayList size: " + placeDetailsArrayList.size());
                    setListAdaptor();
                }

        return v;
    }

    private void setListAdaptor( ){
        feedPlaceAdaptor = new FeedPlaceAdaptor(getActivity(), placeDetailsArrayList);
        ListView contentList = (ListView) v.findViewById(R.id.lvPlaces);
        contentList.setAdapter(feedPlaceAdaptor);

        contentList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        PlaceDetails selectedPlace = placeDetailsArrayList.get(position);
                        Intent viewPlaceIntent = new Intent(getActivity(), ViewPlaceProfileActivity.class);
                        Bundle placeData = new Bundle();
                        Log.i(FOOTPRINT_LOGGER, "placeID=" + selectedPlace.getPlaceID());
                        placeData.putInt("placeID", selectedPlace.getPlaceID());
                        Log.i(FOOTPRINT_LOGGER, "placeTitle=" + selectedPlace.getTitle());
                        placeData.putString("placeTitle", selectedPlace.getTitle());
                        Log.i(FOOTPRINT_LOGGER, "placeLocID=" + selectedPlace.getLocID());
                        placeData.putInt("placeLocID", selectedPlace.getLocID());
                        viewPlaceIntent.putExtra("placeData",placeData);
                        startActivity(viewPlaceIntent);

                    }
                }
        );
    }

    private void showErrorMessage(){
        Log.i(FOOTPRINT_LOGGER, "showErrorMessage()");
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setMessage("No Places Found");
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();
    }
}
