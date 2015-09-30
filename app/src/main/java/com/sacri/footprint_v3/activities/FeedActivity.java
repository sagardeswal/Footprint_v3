package com.sacri.footprint_v3.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.sacri.footprint_v3.R;
import com.sacri.footprint_v3.callback.GetEventCallback;
import com.sacri.footprint_v3.callback.GetPlaceCallback;
import com.sacri.footprint_v3.callback.GetStoryCallback;
import com.sacri.footprint_v3.dbaccess.ServerRequests;
import com.sacri.footprint_v3.entity.EventDetails;
import com.sacri.footprint_v3.entity.PlaceDetails;
import com.sacri.footprint_v3.entity.Story;
import com.sacri.footprint_v3.entity.UserDetails;
import com.sacri.footprint_v3.fragments.DisplayOnMapFragment;
import com.sacri.footprint_v3.fragments.FeedEventFragment;
import com.sacri.footprint_v3.fragments.FeedPlaceFragment;
import com.sacri.footprint_v3.fragments.FeedStoryFragment;
import com.sacri.footprint_v3.utils.FeedEventRecyclerAdaptor;
import com.sacri.footprint_v3.utils.FeedPagerAdaptor;
import com.sacri.footprint_v3.utils.FeedPlaceRecyclerAdaptor;
import com.sacri.footprint_v3.utils.UserLocalStore;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class FeedActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener  {

    private static final String FOOTPRINT_LOGGER = "com.sacri.footprint_v3";
    private UserDetails loggedUser;
    private DrawerLayout mDrawerLayout;
    private ViewPager viewPager;
    private FeedPagerAdaptor mFeedPagerAdaptor;
    private FeedEventRecyclerAdaptor feedEventRecyclerAdaptor;
    private FeedPlaceRecyclerAdaptor feedPlaceRecyclerAdaptor;

    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;
    private static ArrayList<PlaceDetails> mPlaceDetailsArrayList;
    private static ArrayList<EventDetails> mEventDetailsArrayList;
    private static ArrayList<Story> mStoryArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        UserLocalStore userLocalStore = new UserLocalStore(this);
        loggedUser = userLocalStore.getLoggedInUser();
        Log.i(FOOTPRINT_LOGGER, "Logged In User:" + loggedUser.toString());
        isNetworkAndGPSAvailable();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            final ActionBar ab = getSupportActionBar();
            assert ab != null;
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);

            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            if (navigationView != null) {
                setupDrawerContent(navigationView);
            }

            viewPager = (ViewPager) findViewById(R.id.pager);
            if (viewPager != null) {
                setupViewPager();
            }

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            });

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        onActivityStart();
    }

    private void setupViewPager() {
        mFeedPagerAdaptor = new FeedPagerAdaptor(getSupportFragmentManager());
        mFeedPagerAdaptor.addFragment(new FeedStoryFragment(), "Stories");
        mFeedPagerAdaptor.addFragment(new FeedPlaceFragment(), "Places");
        mFeedPagerAdaptor.addFragment(new FeedEventFragment(), "Events");
        mFeedPagerAdaptor.addFragment(new DisplayOnMapFragment(), "Map");
        viewPager.setAdapter(mFeedPagerAdaptor);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        TextView tvUsername = (TextView) navigationView.findViewById(R.id.tvUsername);
        tvUsername.setText(loggedUser.getFullname());
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    private void onActivityStart(){

        buildGoogleApiClient();

        getPlacesInBackground();
        getStoryDataInBackground();

        getEventsInBackground();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
        mFeedPagerAdaptor.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        Log.i(FOOTPRINT_LOGGER, "FeedActivity onStart()");
        super.onStart();
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
        Log.i(FOOTPRINT_LOGGER, "getmLastLocation()");
        return mLastLocation;
    }

    private void getPlacesInBackground(){
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.fetchPlaceDataInBackground("New Delhi", new GetPlaceCallback() {
            @Override
            public void done(ArrayList<PlaceDetails> placeDetailsArrayList) {

                if (placeDetailsArrayList == null) {
                    Log.i(FOOTPRINT_LOGGER, "placeDetailsArrayList is null");

                } else {
                    Log.i(FOOTPRINT_LOGGER, "placeDetailsArrayList.size(): " + placeDetailsArrayList.size());
                    setMPlaceDetailsArrayList(placeDetailsArrayList);
                    feedEventRecyclerAdaptor.notifyDataSetChanged();
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
        serverRequests.fetchEventDataInBackground("-1", new GetEventCallback() {
            @Override
            public void done(ArrayList<EventDetails> eventDetailsArrayList) {

                if (eventDetailsArrayList == null) {
                    Log.i(FOOTPRINT_LOGGER, "eventDetailsArrayList is null");

                } else {
                    Log.i(FOOTPRINT_LOGGER, "eventDetailsArrayList: " + eventDetailsArrayList.size());
                    setMEventDetailsArrayList(eventDetailsArrayList);
                    feedPlaceRecyclerAdaptor.notifyDataSetChanged();
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


    private void getStoryDataInBackground() {
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.fetchStoryDataInBackground(loggedUser.getUserID().toString(), new GetStoryCallback() {
            @Override
            public void done(ArrayList<Story> storyArrayList) {

                if (storyArrayList == null) {
                    Log.i(FOOTPRINT_LOGGER, "storyArrayList is null");

                } else {
                    Log.i(FOOTPRINT_LOGGER, "storyArrayList.size(): " + storyArrayList.size());
                    setmStoryArrayList(storyArrayList);
                }
            }
        });
    }

    public static ArrayList<Story> getmStoryArrayList() {
        return mStoryArrayList;
    }

    public static void setmStoryArrayList(ArrayList<Story> storyArrayList) {
        mStoryArrayList = storyArrayList;
    }


    private void isNetworkAndGPSAvailable(){
        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        if(!gps_enabled && !network_enabled) {
            // notify user
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage(this.getResources().getString(R.string.gps_network_not_enabled));
            dialog.setPositiveButton(this.getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
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
    }

    public FeedEventRecyclerAdaptor getFeedEventRecyclerAdaptor() {
        return feedEventRecyclerAdaptor;
    }

    public void setFeedEventRecyclerAdaptor(FeedEventRecyclerAdaptor feedEventRecyclerAdaptor) {
        this.feedEventRecyclerAdaptor = feedEventRecyclerAdaptor;
    }

    public FeedPlaceRecyclerAdaptor getFeedPlaceRecyclerAdaptor() {
        return feedPlaceRecyclerAdaptor;
    }

    public void setFeedPlaceRecyclerAdaptor(FeedPlaceRecyclerAdaptor feedPlaceRecyclerAdaptor) {
        this.feedPlaceRecyclerAdaptor = feedPlaceRecyclerAdaptor;
    }
}
