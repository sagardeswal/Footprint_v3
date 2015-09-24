package com.sacri.footprint_v3.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.sacri.footprint_v3.R;
import com.sacri.footprint_v3.entity.PlaceDetails;

public class ViewPlaceProfileActivity extends AppCompatActivity {

    private final int REQUEST_CHECKIN_CODE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_place_profile);

        Button bnCheckIn = (Button) findViewById(R.id.bnCheckIn);
        bnCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent checkinIntent = new Intent(ViewPlaceProfileActivity.this,CheckInActivity.class);
                Bundle placeData = getIntent().getBundleExtra("placeData");
                checkinIntent.putExtra("placeData",placeData);
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
