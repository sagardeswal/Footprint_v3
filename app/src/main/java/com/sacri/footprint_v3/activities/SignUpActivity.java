package com.sacri.footprint_v3.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sacri.footprint_v3.R;
import com.sacri.footprint_v3.entity.UserDetails;

public class SignUpActivity extends AppCompatActivity {

    private EditText etFullName;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etReEnterPassword;
    private EditText etEmail;
    private EditText etMobile;
    private Button bnSignUp;

    private UserDetails newUser;

    private static final String NULL_STRING = "";
    private static final String FOOTPRINT_LOGGER = "com.sacri.footprint_v3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Get reference to widgets
        etFullName = (EditText) findViewById(R.id.etFullname);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etReEnterPassword = (EditText) findViewById(R.id.etReEnterPassword);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etMobile = (EditText) findViewById(R.id.etMobile);
        bnSignUp = (Button) findViewById(R.id.bnSignUp);

        //Create new instance of UserDetails
        newUser = new UserDetails();

        bnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Set values in newUser
                newUser.setFullname(etFullName.getText().toString());
                newUser.setUsername(etUsername.getText().toString());
                newUser.setEmail(etEmail.getText().toString());
                newUser.setMobile(etMobile.getText().toString());

                //Check if Password fields match
                if(etPassword.getText().toString().equals(etReEnterPassword.getText().toString())){
                    newUser.setPaswordhashcode(etPassword.getText().toString());
                    storeUserDetials();
                    Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(SignUpActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                    etReEnterPassword.setText(NULL_STRING);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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

    private void storeUserDetials(){

        Log.i(FOOTPRINT_LOGGER,"UserDetails: FullName: " + newUser.getFullname());
        Log.i(FOOTPRINT_LOGGER,"UserDetails: Username: " + newUser.getUsername());
        Log.i(FOOTPRINT_LOGGER,"UserDetails: Email: " + newUser.getEmail());
        Log.i(FOOTPRINT_LOGGER,"UserDetails: Mobile: " + newUser.getMobile());

    }
}
