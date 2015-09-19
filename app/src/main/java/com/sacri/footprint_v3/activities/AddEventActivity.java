package com.sacri.footprint_v3.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.sacri.footprint_v3.R;
import com.sacri.footprint_v3.callback.AddEventCallback;
import com.sacri.footprint_v3.dbaccess.ServerRequests;
import com.sacri.footprint_v3.entity.EventDetails;
import java.util.Date;

public class AddEventActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String FOOTPRINT_LOGGER = "com.sacri.footprint_v3";
    private EditText etTitle;
    private EditText etDescription;
    private EditText etLocation;
    private Switch swRepeatWeekly;
    private TextView tvStartDate;
    private DatePicker dpStartDate;
    private DatePicker dpEndDate;
    private TimePicker tpStartTime;
    private TimePicker tpEndTime;

    private EventDetails eventDetails;
    private int PLACE_PICKER_REQUEST = 1;
    private LatLng pickedLocation;

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        buildGoogleApiClient();
        eventDetails = new EventDetails();

        //Get reference to the widgets in the view
        etTitle = (EditText) findViewById(R.id.etTitle);
        etDescription = (EditText) findViewById(R.id.etDescription);
        etLocation = (EditText) findViewById(R.id.etLocation);
        swRepeatWeekly = (Switch) findViewById(R.id.swRepeatWeekly);
        if(swRepeatWeekly.isChecked()){
            tvStartDate.setText(R.string.event_date_label_text);
            dpStartDate = (DatePicker) findViewById(R.id.dpStartDate);
            dpEndDate.setVisibility(View.INVISIBLE);
        }else{
            dpStartDate = (DatePicker) findViewById(R.id.dpStartDate);
            dpEndDate = (DatePicker) findViewById(R.id.dpEndDate);
        }
        tpStartTime = (TimePicker) findViewById(R.id.tpStartTime);
        tpEndTime = (TimePicker) findViewById(R.id.tpEndTime);
        Button bnLocate = (Button) findViewById(R.id.bnLocate);
        bnLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(FOOTPRINT_LOGGER, "bnLocate clicked");

                // Construct an intent for the place picker
                try {
                    PlacePicker.IntentBuilder intentBuilder =
                            new PlacePicker.IntentBuilder();
                    Intent intent = intentBuilder.build(AddEventActivity.this);
                    // Start the intent by requesting a result,
                    // identified by a request code.
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });
        Button bnSave = (Button) findViewById(R.id.bnSave);
        bnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Set data in EventDetails
                int missing[] = {0,0,0};
                if(etTitle.getText()!=null && !etTitle.getText().toString().equals(""))
                    eventDetails.setEventTitle(etTitle.getText().toString());
                else{
                    missing[0] = 1;
                    Toast.makeText(AddEventActivity.this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
                }
                if(etDescription.getText()!=null && !etDescription.getText().toString().equals(""))
                    eventDetails.setEventDescription(etDescription.getText().toString());
                else{
                    missing[1] = 1;
                    Toast.makeText(AddEventActivity.this, "Description cannot be empty", Toast.LENGTH_SHORT).show();
                }
                if(swRepeatWeekly!=null)
                    eventDetails.setRepeatedWeekly(swRepeatWeekly.isChecked());
                else
                    eventDetails.setRepeatedWeekly(false);
                if(dpStartDate!=null)
                    eventDetails.setStartDate(new Date(dpStartDate.getYear(), dpStartDate.getMonth(), dpStartDate.getDayOfMonth()));
                if(dpEndDate!=null)
                    if (eventDetails.getRepeatedWeekly()) {
                        eventDetails.setEndDate(eventDetails.getStartDate());
                    } else {
                        eventDetails.setEndDate(new Date(dpEndDate.getYear(), dpEndDate.getMonth(), dpEndDate.getDayOfMonth()));
                    }
                if(tpStartTime!=null) {
                    eventDetails.setStartTimeHour(tpStartTime.getCurrentHour());
                    eventDetails.setStartTimeMinutes(tpStartTime.getCurrentMinute());
                }
                if(tpEndTime!=null) {
                    eventDetails.setEndTimeHour(tpEndTime.getCurrentHour());
                    eventDetails.setEndTimeMinutes(tpEndTime.getCurrentMinute());
                }

                if (pickedLocation == null) {
                    Log.i(FOOTPRINT_LOGGER, "pickedLocation is null");
                    eventDetails.setLatitude(mLastLocation.getLatitude());
                    eventDetails.setLongitude(mLastLocation.getLongitude());

                } else {
                    eventDetails.setLatitude(pickedLocation.latitude);
                    eventDetails.setLongitude(pickedLocation.longitude);
                }
                if(etLocation.getText()==null || etLocation.getText().toString().equals("")){
                    missing[2] = 1;
                    Toast.makeText(AddEventActivity.this, "Address cannot be empty", Toast.LENGTH_SHORT).show();
                }else if(eventDetails.getAddress()==null){
                    eventDetails.setAddress(etLocation.getText().toString());
                }

                if(missing[0]!=1 && missing[1]!=1 && missing[2]!=1) {
                    //Store eventdetails in the database
                    storeEventDataInBackground();
                }
            }
        });
        Button bnCancel = (Button) findViewById(R.id.bnCancel);
        bnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data) {
        Log.i(FOOTPRINT_LOGGER, "onActivityResult()");

        if (requestCode == PLACE_PICKER_REQUEST
                && resultCode == Activity.RESULT_OK) {

            // The user has selected a place. Extract the name and address.
            final Place place = PlacePicker.getPlace(data, AddEventActivity.this);

            final CharSequence name = place.getName();
            final CharSequence address = place.getAddress();
            pickedLocation = place.getLatLng();

            etLocation.setText(name + "\n" + address);
            eventDetails.setAddress(name + "\n" + address);
        }
    }

    private void storeEventDataInBackground(){
        Log.i(FOOTPRINT_LOGGER, "EventDetails: " + eventDetails.toString());

        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.addEventDataInBackground(eventDetails, new AddEventCallback() {
            @Override
            public void done(String response) {
                Toast.makeText(AddEventActivity.this, "Event Added Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_event, menu);
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
    protected void onStart() {
        Log.i(FOOTPRINT_LOGGER,"AddEventActivity onStart()");
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        Log.i(FOOTPRINT_LOGGER,"AddEventActivity onStop()");
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(FOOTPRINT_LOGGER,"AddEventActivity onConnected()");
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        Log.i(FOOTPRINT_LOGGER,"mLastLocation" + mLastLocation.toString());
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
}
