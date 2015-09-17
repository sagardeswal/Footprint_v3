package com.sacri.footprint_v3.activities;

import android.app.AlertDialog;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sacri.footprint_v3.R;
import com.sacri.footprint_v3.callback.GetEventCallback;
import com.sacri.footprint_v3.callback.GetPlaceCallback;
import com.sacri.footprint_v3.dbaccess.ServerRequests;
import com.sacri.footprint_v3.entity.EventDetails;
import com.sacri.footprint_v3.entity.PlaceDetails;
import com.sacri.footprint_v3.utils.FeedPagerAdaptor;

import java.util.ArrayList;

public class FeedActivity extends AppCompatActivity implements ActionBar.TabListener,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener  {

    private static final String FOOTPRINT_LOGGER = "com.sacri.footprint_v3";
    private FeedPagerAdaptor mFeedPagerAdaptor;
    private ViewPager feedPager;
    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;
    public static ArrayList<PlaceDetails> mPlaceDetailsArrayList;
    public static ArrayList<EventDetails> mEventDetailsArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        buildGoogleApiClient();
        final ActionBar actionBar = getSupportActionBar();

        getPlacesInBackground();
        getEventsInBackground();

        // Specify that the Home/Up button should not be enabled, since there is no hierarchical
        // parent.
        actionBar.setHomeButtonEnabled(false);

        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        mFeedPagerAdaptor = new FeedPagerAdaptor(getSupportFragmentManager());
        feedPager = (ViewPager) findViewById(R.id.pager);
        feedPager.setAdapter(mFeedPagerAdaptor);

        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        feedPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                // Tab.
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mFeedPagerAdaptor.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter.
            // Also specify this Activity object, which implements the TabListener interface, as the
            // listener for when this tab is selected.
            String pageTitle;
            switch(i){
                case 0: pageTitle = "Feed";
                    break;
                case 1: pageTitle = "Places";
                    break;
                case 2: pageTitle = "Events";
                    break;
                case 3: pageTitle = "Map";
                    break;
                default:pageTitle = "Home";
            }
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(pageTitle)
                            .setTabListener(this));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
        feedPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(FOOTPRINT_LOGGER,"AddPlaceActivity onConnected()");
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        Log.i(FOOTPRINT_LOGGER, "mLastLocation" + mLastLocation.toString());
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(FOOTPRINT_LOGGER, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(FOOTPRINT_LOGGER, "Connection suspended");
        mGoogleApiClient.connect();
    }

    /**
     * Builds a GoogleApiClient. Uses the addApi() method to request the LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        Log.i(FOOTPRINT_LOGGER,"buildGoogleApiClient()");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        Log.i(FOOTPRINT_LOGGER,"AddPlaceActivity onStart()");
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        Log.i(FOOTPRINT_LOGGER, "AddPlaceActivity onStop()");
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


    public GoogleApiClient getmGoogleApiClient(){
        Log.i(FOOTPRINT_LOGGER, "getmGoogleApiClient()");
        return mGoogleApiClient;
    }

    public Location getmLastLocation(){
        Log.i(FOOTPRINT_LOGGER,"getmLastLocation()");
        return mLastLocation;
    }

    private void getPlacesInBackground(){
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.fetchPlaceDataInBackground("New Delhi", new GetPlaceCallback() {
            @Override
            public void done(ArrayList<PlaceDetails> placeDetailsArrayList) {

                if (placeDetailsArrayList == null) {
                    Log.i(FOOTPRINT_LOGGER, "placeDetailsArrayList is null");
                    showErrorMessage();

                } else {
                    Log.i(FOOTPRINT_LOGGER, "placeDetailsArrayList: " + placeDetailsArrayList.get(0).getTitle());
                    setMPlaceDetailsArrayList(placeDetailsArrayList);
                }
            }
        });
    }

    public void setMPlaceDetailsArrayList(ArrayList<PlaceDetails> placeDetailsArrayList){
        mPlaceDetailsArrayList = placeDetailsArrayList;
    }

    public ArrayList<PlaceDetails> getMPlaceDetailsArrayList(){
        return mPlaceDetailsArrayList;
    }

    private void getEventsInBackground(){
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.fetchEventDataInBackground(new GetEventCallback() {
            @Override
            public void done(ArrayList<EventDetails> eventDetailsArrayList) {

                if (eventDetailsArrayList == null) {
                    Log.i(FOOTPRINT_LOGGER, "eventDetailsArrayList is null");
                    showErrorMessage();

                } else {
                    Log.i(FOOTPRINT_LOGGER, "eventDetailsArrayList: " + eventDetailsArrayList.toString());
                    setMEventDetailsArrayList(eventDetailsArrayList);
                }
            }
        });
    }

    public void setMEventDetailsArrayList(ArrayList<EventDetails> eventDetailsArrayList){
        mEventDetailsArrayList = eventDetailsArrayList;
    }

    public ArrayList<EventDetails> getMEventDetailsArrayList(){
        return mEventDetailsArrayList;
    }

    private void showErrorMessage(){
        Log.i(FOOTPRINT_LOGGER, "showErrorMessage()");
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setMessage("No Places Found");
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();
    }
}
