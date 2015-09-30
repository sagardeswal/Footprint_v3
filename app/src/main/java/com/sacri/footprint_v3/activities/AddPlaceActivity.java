package com.sacri.footprint_v3.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.location.Location;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.sacri.footprint_v3.R;
import com.sacri.footprint_v3.callback.CameraActionCallback;
import com.sacri.footprint_v3.entity.PlaceDetails;
import com.sacri.footprint_v3.fragments.AddPlaceFragment;
import com.sacri.footprint_v3.fragments.DisplayOnMapFragment;

import java.io.File;

public class AddPlaceActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,CameraActionCallback {

    private static final String FOOTPRINT_LOGGER = "com.sacri.footprint_v3";
    private PlaceDetails newPlace;
    private File photoFile;
    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);
        buildGoogleApiClient();
        Log.i(FOOTPRINT_LOGGER, "mGoogleApiClient is connected = " + mGoogleApiClient.isConnected());
        newPlace = new PlaceDetails();

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragmentContainer) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            FragmentManager manager = getFragmentManager();
            Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

            if (fragment == null) {
                fragment = new AddPlaceFragment();
                manager.beginTransaction().add(R.id.fragmentContainer, fragment)
                        .commit();
            }
        }
    }

//    public void replaceAddPlaceFragmentWithMapFragment(){
//        // Create a new Fragment to be placed in the activity layout
//        DisplayOnMapFragment displayOnMapFragment = new DisplayOnMapFragment();
//
//        Bundle currentLocation = new Bundle();
//        currentLocation.putDouble("latitude", mLastLocation.getLatitude());
//        currentLocation.putDouble("longitude", mLastLocation.getLongitude());
//        displayOnMapFragment.setArguments(currentLocation);
//
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//
//        // Replace whatever is in the fragment_container view with this fragment,
//        // and add the transaction to the back stack so the user can navigate back
//        transaction.replace(R.id.fragmentContainer, displayOnMapFragment);
//        transaction.addToBackStack(null);
//
//        // Commit the transaction
//        transaction.commit();
//
//    }

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
        Log.i(FOOTPRINT_LOGGER, "buildGoogleApiClient()");
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
        Log.i(FOOTPRINT_LOGGER,"AddPlaceActivity onStop()");
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_place, menu);
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



    public PlaceDetails getCurrentPlaceDetails(){
        return newPlace;
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    public Location getmLastLocation(){
        Log.i(FOOTPRINT_LOGGER,"getmLastLocation()");
        return mLastLocation;
    }

    public File getPhotoFile() {
        return photoFile;
    }

    @Override
    public void onPhotoCaptured(File photo) {
        this.photoFile = photo;
    }
}
