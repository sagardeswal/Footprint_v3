package com.sacri.footprint_v3.fragments;


import android.app.AlertDialog;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sacri.footprint_v3.R;
import com.sacri.footprint_v3.activities.FeedActivity;
import com.sacri.footprint_v3.entity.EventDetails;
import com.sacri.footprint_v3.entity.PlaceDetails;
import java.util.ArrayList;

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
        LatLng latLng;
        try {
            mLastLocation = ((FeedActivity) getActivity()).getmLastLocation();
        }catch(Exception e){
        }
        if(mLastLocation==null){
            Log.i(FOOTPRINT_LOGGER, "mLastLocation is null");
            latLng = new LatLng(17.385044,78.486671);
        }else{
            latLng = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
        }
        try {
            ArrayList<PlaceDetails> placeDetailsArrayList = ((FeedActivity) getActivity()).getMPlaceDetailsArrayList();
            if (placeDetailsArrayList == null) {
                Log.i(FOOTPRINT_LOGGER, "placeDetailsArrayList is null");
                showErrorMessage();
            } else {
                Log.i(FOOTPRINT_LOGGER, "placeDetailsArrayList: " + placeDetailsArrayList.get(0).getTitle());
                drawPlaceMarkersOnMap(placeDetailsArrayList);
            }
        }catch(Exception e){
            //do nothing
        }

        try{
            ArrayList<EventDetails> eventDetailsArrayList = ((FeedActivity) getActivity()).getMEventDetailsArrayList();
            if(eventDetailsArrayList==null) {
                Log.i(FOOTPRINT_LOGGER,"eventDetailsArrayList is null");
            }
            else{
                Log.i(FOOTPRINT_LOGGER, "eventDetailsArrayList: " + eventDetailsArrayList.toString());
                drawEventMarkersOnMap(eventDetailsArrayList);
            }
        }catch(Exception e){
            //do nothing
        }

        drawCurrentLocationMarker(latLng);

        // Perform any camera updates here
        return v;
    }

    private void drawCurrentLocationMarker(LatLng latLng){
        // create marker
        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(latLng.latitude, latLng.longitude)).title("My Location");

        // Changing marker icon
        marker.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

        googleMap.addMarker(marker);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

    }

    private void drawPlaceMarkersOnMap(ArrayList<PlaceDetails> placeDetailsArrayList) {
        for (PlaceDetails placeDetails : placeDetailsArrayList) {
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

    private void drawEventMarkersOnMap(ArrayList<EventDetails> eventDetailsArrayList) {
        for (EventDetails eventDetails : eventDetailsArrayList) {
            // create marker
            MarkerOptions marker = new MarkerOptions().position(
                    new LatLng(eventDetails.getLatitude(), eventDetails.getLongitude())).title(eventDetails.getEventTitle());

            // Changing marker icon
            marker.icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_CYAN));

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