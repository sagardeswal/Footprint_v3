package com.sacri.footprint_v3.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sacri.footprint_v3.R;
import com.sacri.footprint_v3.entity.PlaceDetails;

public class ViewPlaceProfileActivity extends AppCompatActivity {

    private final int REQUEST_CHECKIN_CODE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_place_profile);
        Bundle placeData = getIntent().getBundleExtra("placeData");
        final PlaceDetails placeDetails = new PlaceDetails();
        placeDetails.setPlaceID((Integer)placeData.get("placeID"));
        placeDetails.setLocID((Integer)placeData.get("placeLocID"));
        placeDetails.setTitle(placeData.get("placeTitle").toString());
        placeDetails.setDescription(placeData.get("placeDescription").toString());
        placeDetails.setCategory(placeData.get("placeCategory").toString());

        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(placeDetails.getTitle());
        TextView tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvDescription.setText(placeDetails.getDescription());
        TextView tvCategory = (TextView) findViewById(R.id.tvCategory);
        tvCategory.setText(placeDetails.getCategory());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent checkinIntent = new Intent(ViewPlaceProfileActivity.this,CheckInActivity.class);
                Bundle data = new Bundle();
                data.putInt("placeID", placeDetails.getPlaceID());
                data.putInt("eventID", -1);
                data.putInt("locID", placeDetails.getLocID());
                checkinIntent.putExtras(data);
//                Bundle placeData = getIntent().getBundleExtra("placeData");
//                checkinIntent.putExtra("placeData",placeData);
                startActivityForResult(checkinIntent, REQUEST_CHECKIN_CODE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_place_profile, menu);
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
