package com.sacri.footprint_v3.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.api.GoogleApiClient;
import com.sacri.footprint_v3.R;
import com.sacri.footprint_v3.utils.UserLocalStore;

public class MainActivity extends AppCompatActivity {

    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;
    private UserLocalStore userLocalStore;

    private Button bnAddPlace;
    private Button bnLogout;
    private Button bnFeed;
    private Button bnMap;
    private Button bnAddEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userLocalStore = new UserLocalStore(this);

        bnFeed = (Button) findViewById(R.id.bnFeed);
        bnFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FeedActivity.class);
                startActivity(intent);
            }
        });

        bnMap = (Button) findViewById(R.id.bnMap);
        bnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SpotPlaceActivity.class);
                startActivity(intent);
            }
        });

        bnAddEvent = (Button) findViewById(R.id.bnAddEvent);
        bnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddEventActivity.class);
                startActivity(intent);
            }
        });


        bnAddPlace = (Button) findViewById(R.id.bnAddPlace);
        //hide app place button
        bnAddPlace.setVisibility(View.INVISIBLE);
        bnLogout = (Button) findViewById(R.id.bnLogout);
        bnAddPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddPlaceActivity.class);
                startActivity(intent);
            }
        });

        bnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (mGoogleApiClient.isConnected()) {
//                    Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
//                    mGoogleApiClient.disconnect();
//                }
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);
                finish();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
