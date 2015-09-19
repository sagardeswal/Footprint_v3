package com.sacri.footprint_v3.activities;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Switch;

import com.sacri.footprint_v3.R;
import com.sacri.footprint_v3.callback.GetEventCallback;
import com.sacri.footprint_v3.dbaccess.ServerRequests;
import com.sacri.footprint_v3.entity.EventDetails;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ViewEventActivity extends AppCompatActivity {

    private static final String FOOTPRINT_LOGGER = "com.sacri.footprint_v3";

    private EditText etTitle;
    private EditText etDescription;
    private Switch swRepeatWeekly;
    private EditText etStartDate;
    private EditText etEndDate;
    private EditText etStartTime;
    private EditText etEndTime;
    private EditText etAddress;

    private static EventDetails mEventDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        etTitle = (EditText) findViewById(R.id.etTitle);
        etDescription = (EditText) findViewById(R.id.etDescription);
        swRepeatWeekly = (Switch) findViewById(R.id.swRepeatWeekly);
        etStartDate = (EditText) findViewById(R.id.etStartDate);
        etEndDate = (EditText) findViewById(R.id.etEndDate);
        etStartTime = (EditText) findViewById(R.id.etStartTime);
        etEndTime = (EditText) findViewById(R.id.etEndTime);
        etAddress = (EditText) findViewById(R.id.etAddress);
        Bundle extras = getIntent().getExtras();
        Integer eventID = 0;
        if (extras != null) {
            eventID = extras.getInt("ev_id");
        }
        getEventInBackground(eventID);
    }

    private void displayEventDetails(){

        etTitle.setText(mEventDetails.getEventTitle());
        etDescription.setText(mEventDetails.getEventDescription());
        swRepeatWeekly.setChecked(mEventDetails.getRepeatedWeekly());
        DateFormat startDateFormat = new SimpleDateFormat("dd MM yyyy");
        String startDate = startDateFormat.format(mEventDetails.getStartDate());
        etStartDate.setText(startDate);
        DateFormat endDateFormat = new SimpleDateFormat("dd MM yyyy");
        String endDate = startDateFormat.format(mEventDetails.getEndDate());
        etEndDate.setText(endDate);
        etStartTime.setText(mEventDetails.getStartTimeHour() + " : " + mEventDetails.getStartTimeMinutes() + "Hrs");
        etEndTime.setText(mEventDetails.getEndTimeHour() + " : " + mEventDetails.getEndTimeMinutes() + "Hrs");
        etAddress.setText(mEventDetails.getAddress());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_event, menu);
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

    private void getEventInBackground(Integer eventID){
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.fetchEventDataInBackground(eventID.toString(),new GetEventCallback() {
            @Override
            public void done(ArrayList<EventDetails> eventDetailsArrayList) {

                if (eventDetailsArrayList == null) {
                    Log.i(FOOTPRINT_LOGGER, "eventDetailsArrayList is null");
                    showErrorMessage();

                } else if(eventDetailsArrayList.size()>1){
                    Log.i(FOOTPRINT_LOGGER, "eventDetailsArrayList size: " + eventDetailsArrayList.size());
                    Log.i(FOOTPRINT_LOGGER, "showErrorMessage()");
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ViewEventActivity.this);
                    dialogBuilder.setMessage("More than one events fetched");
                    dialogBuilder.setPositiveButton("Ok", null);
                    dialogBuilder.show();
                } else{
                    setMEventDetailsArrayList(eventDetailsArrayList);
                    displayEventDetails();
                }
            }
        });
    }

    public void setMEventDetailsArrayList(ArrayList<EventDetails> eventDetailsArrayList){
        mEventDetails = eventDetailsArrayList.get(0);
    }


    private void showErrorMessage(){
        Log.i(FOOTPRINT_LOGGER, "showErrorMessage()");
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setMessage("Could not fetch Event");
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();
    }
}
