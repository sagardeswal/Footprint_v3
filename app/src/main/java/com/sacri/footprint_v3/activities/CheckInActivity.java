package com.sacri.footprint_v3.activities;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import com.sacri.footprint_v3.R;
import com.sacri.footprint_v3.callback.CameraActionCallback;
import com.sacri.footprint_v3.entity.PlaceDetails;
import com.sacri.footprint_v3.fragments.AddPlaceFragment;
import com.sacri.footprint_v3.fragments.CameraFragment;
import com.sacri.footprint_v3.fragments.CheckinFragment;

import java.io.File;

public class CheckInActivity extends AppCompatActivity implements CameraActionCallback{


    private File photoFile;
    private PlaceDetails placeDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);
        Bundle placeData = getIntent().getBundleExtra("placeData");
        placeDetails = new PlaceDetails(placeData.getInt("placeLocID"),placeData.getInt("placeID"));
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
                fragment = new CheckinFragment();
                manager.beginTransaction().add(R.id.fragmentContainer, fragment)
                        .commit();

            }
        }

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        FragmentManager manager = getFragmentManager();
        manager.popBackStack();
        if(manager.getBackStackEntryCount()==0){
            super.onBackPressed();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_check_in, menu);
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



    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public File getPhotoFile() {
        return photoFile;
    }

    public PlaceDetails getPlaceDetails() {
        return placeDetails;
    }

    @Override
    public void onPhotoCaptured(File photo) {
        this.photoFile = photo;
    }
}
