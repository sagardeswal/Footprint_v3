package com.sacri.footprint_v3.fragments;

import android.app.AlertDialog;
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
import com.sacri.footprint_v3.callback.GetPlaceCallback;
import com.sacri.footprint_v3.dbaccess.ServerRequests;
import com.sacri.footprint_v3.entity.PlaceDetails;
import com.sacri.footprint_v3.utils.FeedPlaceAdaptor;
import java.util.ArrayList;

public class FeedPlaceFragment extends Fragment {

    private static final String FOOTPRINT_LOGGER = "com.sacri.footprint_v3";
    private ListAdapter feedPlaceAdaptor;
    private View v;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        String[] places = {"Qutab Minar", "India Gate", "Lotus Temple", "Connaught Place", "Chandni Chawk", "Hauz Khas Village", "Akshardham"};
        String location ="New Delhi";

        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_place_feed, container, false);


        ServerRequests serverRequests = new ServerRequests(getActivity());
        serverRequests.fetchPlaceDataInBackground(location, new GetPlaceCallback() {
            @Override
            public void done(ArrayList<PlaceDetails> placeDetailsArrayList) {

                if (placeDetailsArrayList == null) {
                    Log.i(FOOTPRINT_LOGGER, "placeDetailsArrayList is null");
                    showErrorMessage();

                } else {
                    Log.i(FOOTPRINT_LOGGER, "placeDetailsArrayList: " + placeDetailsArrayList.get(0).getTitle());
                    setListAdaptor(placeDetailsArrayList);
                }
            }
        });
        return v;
    }

    private void setListAdaptor(ArrayList<PlaceDetails> placeDetailsArrayList ){
        feedPlaceAdaptor = new FeedPlaceAdaptor(getActivity(), placeDetailsArrayList);
        ListView contentList = (ListView) v.findViewById(R.id.lvPlaces);
        contentList.setAdapter(feedPlaceAdaptor);

        contentList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String placeTitle = String.valueOf(parent.getItemAtPosition(position));
                        Toast.makeText(getActivity(), placeTitle, Toast.LENGTH_LONG).show();
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
