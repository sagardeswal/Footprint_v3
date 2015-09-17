package com.sacri.footprint_v3.fragments;


import android.app.AlertDialog;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sacri.footprint_v3.R;
import com.sacri.footprint_v3.activities.AddPlaceActivity;
import com.sacri.footprint_v3.activities.FeedActivity;
import com.sacri.footprint_v3.entity.PlaceDetails;
import java.util.ArrayList;
import java.util.Iterator;

public class DisplayOnMapFragment extends Fragment {

    private MapView mMapView;
    private GoogleMap googleMap;
    private static final String FOOTPRINT_LOGGER = "com.sacri.footprint_v3";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflat and return the layout
        View v = inflater.inflate(R.layout.fragment_display_on_map, container,
                false);
        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();
        googleMap.setMyLocationEnabled(true);
        Location mLastLocation = null;
        // adding marker
        LatLng latLng;
        try {
            mLastLocation = ((FeedActivity) getActivity()).getmLastLocation();
        }catch(Exception e){
            mLastLocation = ((AddPlaceActivity) getActivity()).getmLastLocation();
        }
        if(mLastLocation==null){
            Log.i(FOOTPRINT_LOGGER, "mLastLocation is null");
            latLng = new LatLng(17.385044,78.486671);
        }else{
            latLng = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
        }

        // create marker
        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude())).title("My Location");

        // Changing marker icon
        marker.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                try {
                    //set the properties for button
                    FrameLayout mapViewLayout = (FrameLayout) getActivity().findViewById(R.id.mapViewLayout);
                    Button bnDone = new Button(getActivity());
                    bnDone.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
                    bnDone.setText("Done");
                    //add button to the layout
                    mapViewLayout.addView(bnDone);

                    bnDone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getFragmentManager().popBackStackImmediate();
                        }
                    });
                }catch(Exception e){
                    //do nothing
                }
            }
        });

        // add marker
        googleMap.addMarker(marker);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
        try {
            ArrayList<PlaceDetails> placeDetailsArrayList = ((FeedActivity) getActivity()).getPlaceDetailsArrayList();
            if (placeDetailsArrayList == null) {
                Log.i(FOOTPRINT_LOGGER, "placeDetailsArrayList is null");
                showErrorMessage();
            } else {
                Log.i(FOOTPRINT_LOGGER, "placeDetailsArrayList: " + placeDetailsArrayList.get(0).getTitle());
                drawMarkersOnMap(placeDetailsArrayList);
            }
        }catch(Exception e){
            //do nothing
        }

        // Perform any camera updates here
        return v;
    }

    private void drawMarkersOnMap(ArrayList<PlaceDetails> placeDetailsArrayList) {
        Iterator<PlaceDetails> placeDetailsIterator = placeDetailsArrayList.iterator();
        while (placeDetailsIterator.hasNext()){
            PlaceDetails placeDetails = placeDetailsIterator.next();
            // create marker
            MarkerOptions marker = new MarkerOptions().position(
                    new LatLng(placeDetails.getLatitude(), placeDetails.getLongitude())).title(placeDetails.getTitle());

            // Changing marker icon
            marker.icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

            // add marker
            googleMap.addMarker(marker);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    private void showErrorMessage(){
        Log.i(FOOTPRINT_LOGGER, "showErrorMessage()");
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setMessage("No Places Found");
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();
    }
}