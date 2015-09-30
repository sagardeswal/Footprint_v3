package com.sacri.footprint_v3.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.sacri.footprint_v3.R;
import com.sacri.footprint_v3.callback.GetEventCallback;
import com.sacri.footprint_v3.callback.GetStoriesForEventCallback;
import com.sacri.footprint_v3.dbaccess.ServerRequests;
import com.sacri.footprint_v3.entity.EventDetails;
import com.sacri.footprint_v3.entity.Story;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ViewEventActivity extends AppCompatActivity {

    private static final String FOOTPRINT_LOGGER = "com.sacri.footprint_v3";
    private final int REQUEST_CHECKIN_CODE = 0;
    private static EventDetails mEventDetails;
    private static ArrayList<Story> mStoryArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        Bundle eventData = getIntent().getBundleExtra("eventData");
        Integer eventID = eventData.getInt("eventID");
        Log.i(FOOTPRINT_LOGGER,"eventID="+eventID );
        getEventInBackground(eventID);
        getStoriesInBackground(eventID);
    }

    private void displayEventDetails(){

        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(mEventDetails.getEventTitle());
        TextView tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvDescription.setText(mEventDetails.getEventDescription());
        TextView tvStartDate = (TextView) findViewById(R.id.tvStartDate);
        tvStartDate.setText(mEventDetails.getStartDate().toString());
        TextView tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvAddress.setText(mEventDetails.getAddress());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent checkinIntent = new Intent(ViewEventActivity.this,CheckInActivity.class);
                Bundle data = new Bundle();
                data.putInt("placeID", mEventDetails.getPlaceID());
                data.putInt("eventID", mEventDetails.getEventID());
                data.putInt("locID", mEventDetails.getLocID());
                checkinIntent.putExtras(data);
//                Bundle placeData = getIntent().getBundleExtra("placeData");
//                checkinIntent.putExtra("placeData",placeData);
                startActivityForResult(checkinIntent, REQUEST_CHECKIN_CODE);
            }
        });
    }

    private void displayStories(){
        Log.i(FOOTPRINT_LOGGER,"Stories:" + mStoryArrayList.toString());

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
        Log.i(FOOTPRINT_LOGGER,"eventDetailsArrayList" + eventDetailsArrayList.toString());
        mEventDetails = eventDetailsArrayList.get(0);
    }

    private void getStoriesInBackground(Integer eventID){
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.fetchStoryForEventDataInBackground(eventID.toString(),new GetStoriesForEventCallback() {
            @Override
            public void done(ArrayList<Story> storyArrayList) {

                if (storyArrayList == null) {
                    Log.i(FOOTPRINT_LOGGER, "storyArrayList is null");
                    showErrorMessage();
                } else{
                    setmStoryArrayList(storyArrayList);
                    displayStories();
                }
            }
        });
    }

    public static void setmStoryArrayList(ArrayList<Story> mStoryArrayList) {
        ViewEventActivity.mStoryArrayList = mStoryArrayList;
    }

    private void showErrorMessage(){
        Log.i(FOOTPRINT_LOGGER, "showErrorMessage()");
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setMessage("Could not fetch Event");
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();
    }
}
