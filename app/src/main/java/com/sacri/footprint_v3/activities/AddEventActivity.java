package com.sacri.footprint_v3.activities;

import android.app.Activity;
import android.content.Intent;
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
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.sacri.footprint_v3.R;
import com.sacri.footprint_v3.callback.AddEventCallback;
import com.sacri.footprint_v3.dbaccess.ServerRequests;
import com.sacri.footprint_v3.entity.EventDetails;
import java.util.Date;

public class AddEventActivity extends AppCompatActivity {

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
    private Button bnSave;
    private Button bnCancel;
    private Button bnLocate;

    private EventDetails eventDetails;
    private int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

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
        bnLocate = (Button) findViewById(R.id.bnLocate);
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

                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });
        bnSave = (Button) findViewById(R.id.bnSave);
        bnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Set data in EventDetails
                eventDetails.setEventTitle(etTitle.getText().toString());
                eventDetails.setEventDescription(etDescription.getText().toString());
                eventDetails.setRepeatedWeekly(swRepeatWeekly.isChecked());
                eventDetails.setStartDate(new Date(dpStartDate.getYear(), dpStartDate.getMonth(), dpStartDate.getDayOfMonth()));
                if (eventDetails.getRepeatedWeekly() == true) {
                    eventDetails.setEndDate(eventDetails.getStartDate());
                } else {
                    eventDetails.setEndDate(new Date(dpEndDate.getYear(), dpEndDate.getMonth(), dpEndDate.getDayOfMonth()));
                }
                eventDetails.setStartTimeHour(tpStartTime.getCurrentHour());
                eventDetails.setStartTimeMinutes(tpStartTime.getCurrentMinute());
                eventDetails.setEndTimeHour(tpEndTime.getCurrentHour());
                eventDetails.setEndTimeMinutes(tpStartTime.getCurrentMinute());

                //Store eventdetails in the database
                storeEventDataInBackground();
            }
        });
        bnCancel = (Button) findViewById(R.id.bnCancel);
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
        Log.i(FOOTPRINT_LOGGER,"onActivityResult()");

        if (requestCode == PLACE_PICKER_REQUEST
                && resultCode == Activity.RESULT_OK) {

            // The user has selected a place. Extract the name and address.
            final Place place = PlacePicker.getPlace(data, AddEventActivity.this);

            final CharSequence name = place.getName();
            final CharSequence address = place.getAddress();
            LatLng latLng = place.getLatLng();
            eventDetails.setLongitude(latLng.longitude);
            eventDetails.setLatitude(latLng.latitude);
            String attributions = PlacePicker.getAttributions(data);
            if (attributions == null) {
                attributions = "";
            }
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
}
