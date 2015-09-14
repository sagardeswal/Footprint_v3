package com.sacri.footprint_v3.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sacri.footprint_v3.R;

public class ViewUserProfileActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etEmail;
    private EditText etMobile;
    private EditText etUsername;
    private Button bnChangePassword;
    private Button bnSave;
    private Button bnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_profile);

        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etMobile = (EditText) findViewById(R.id.etMobile);
        etUsername = (EditText) findViewById(R.id.etUsername);
        bnChangePassword = (Button) findViewById(R.id.bnChangePassword);
        bnSave = (Button) findViewById(R.id.bnSave);
//        bnSave.setVisibility(View.INVISIBLE);
//        bnSave.setActivated(false);
        bnCancel = (Button) findViewById(R.id.bnCancel);
//        bnCancel.setVisibility(View.INVISIBLE);
//        bnCancel.setActivated(false);

        bnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserDetails();
                Toast.makeText(ViewUserProfileActivity.this, "Profile updated!", Toast.LENGTH_SHORT).show();
            }
        });

        bnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void updateUserDetails(){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_user_profile, menu);
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
