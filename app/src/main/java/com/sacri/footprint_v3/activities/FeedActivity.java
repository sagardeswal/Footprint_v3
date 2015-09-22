package com.sacri.footprint_v3.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
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
import com.sacri.footprint_v3.R;
import com.sacri.footprint_v3.callback.GetEventCallback;
import com.sacri.footprint_v3.callback.GetPlaceCallback;
import com.sacri.footprint_v3.dbaccess.ServerRequests;
import com.sacri.footprint_v3.entity.EventDetails;
import com.sacri.footprint_v3.entity.PlaceDetails;
import com.sacri.footprint_v3.utils.FeedPagerAdaptor;
import com.sacri.footprint_v3.utils.UserLocalStore;

import java.util.ArrayList;

public class FeedActivity extends AppCompatActivity implements ActionBar.TabListener,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener  {

    private static final String FOOTPRINT_LOGGER = "com.sacri.footprint_v3";
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
        isNetworkAndGPSAvailable();
    }

    private void onActivityStart(){

            buildGoogleApiClient();
            getPlacesInBackground();
            getEventsInBackground();

    }

    private void displayFeedPager(){
        final ActionBar actionBar = getSupportActionBar();
        // Specify that the Home/Up button should not be enabled, since there is no hierarchical
        // parent.
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(false);
        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        FeedPagerAdaptor mFeedPagerAdaptor = new FeedPagerAdaptor(getSupportFragmentManager());
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
        switch(id){

            case R.id.action_settings : return true;
            case R.id.mnAddEvent:
                Intent intentAddEvent = new Intent(FeedActivity.this,AddEventActivity.class);
                startActivity(intentAddEvent);
                return true;
            case R.id.mnAddPlace:
                Intent intentAddPlace = new Intent(FeedActivity.this,AddPlaceActivity.class);
                startActivity(intentAddPlace);
                return true;
            case R.id.mnLogout:
                UserLocalStore userLocalStore = new UserLocalStore(this);
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);
                finish();
                Intent intent = new Intent(FeedActivity.this, LoginActivity.class);
                startActivity(intent);
                return true;
            default:return super.onOptionsItemSelected(item);
        }
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
        Log.i(FOOTPRINT_LOGGER, "FeedActivity onConnected()");
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(mLastLocation!=null)
            Log.i(FOOTPRINT_LOGGER, "mLastLocation" + mLastLocation.toString());
        else {
            Log.i(FOOTPRINT_LOGGER, "mLastLocation is null");
        }
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
        if(mGoogleApiClient!=null)
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
    protected void onResume() {
        super.onResume();
//        isNetworkAndGPSAvailable();
    }

    @Override
    protected void onStart() {
        Log.i(FOOTPRINT_LOGGER, "FeedActivity onStart()");
        super.onStart();
//        isNetworkAndGPSAvailable();
        if(mGoogleApiClient!=null)
            mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        Log.i(FOOTPRINT_LOGGER, "FeedActivity onStop()");
        super.onStop();
        if (mGoogleApiClient!=null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    public Location getmLastLocation() {
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
                    showErrorMessage("No Places Found.");

                } else {
                    Log.i(FOOTPRINT_LOGGER, "placeDetailsArrayList: " + placeDetailsArrayList.get(0).getTitle());
                    setMPlaceDetailsArrayList(placeDetailsArrayList);
                }
                /*
                Placed in getPlacesInBackground thread so that data is available before the view is drawn
                 */
                displayFeedPager();
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
        serverRequests.fetchEventDataInBackground("-1",new GetEventCallback() {
            @Override
            public void done(ArrayList<EventDetails> eventDetailsArrayList) {

                if (eventDetailsArrayList == null) {
                    Log.i(FOOTPRINT_LOGGER, "eventDetailsArrayList is null");
                    showErrorMessage("No Events Found.");

                } else {
                    Log.i(FOOTPRINT_LOGGER, "eventDetailsArrayList: " + eventDetailsArrayList.size());
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

    private void showErrorMessage(String message){
        Log.i(FOOTPRINT_LOGGER, "showErrorMessage()");
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setMessage(message);
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();
    }

    private void isNetworkAndGPSAvailable(){
        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_enabled && !network_enabled) {
            // notify user
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage(this.getResources().getString(R.string.gps_network_not_enabled));
            dialog.setPositiveButton(this.getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                    //get gps
                }
            });
            dialog.setNegativeButton(this.getString(R.string.Cancel), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    paramDialogInterface.cancel();
                    finish();

                }
            });
            dialog.show();
        }
        else{
            onActivityStart();
        }
    }

}
