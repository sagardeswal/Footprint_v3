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
import com.sacri.footprint_v3.callback.RegisterUserCallback;
import com.sacri.footprint_v3.dbaccess.ServerRequests;
import com.sacri.footprint_v3.entity.UserDetails;

public class SignUpActivity extends AppCompatActivity {

    private EditText etFullName;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etReEnterPassword;
    private EditText etEmail;
    private EditText etMobile;

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
        Button bnSignUp = (Button) findViewById(R.id.bnSignUp);

        //Create new instance of UserDetails
        newUser = new UserDetails();

        bnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int missing[] = {0, 0, 0, 0, 0};
                int passwordsMatch = 0;
                //Set values in newUser
                if (etFullName.getText() != null)
                    newUser.setFullname(etFullName.getText().toString());
                else {
                    missing[0] = 1;
                    Toast.makeText(SignUpActivity.this, "Full name cannot be empty.", Toast.LENGTH_SHORT).show();
                }

                if (etUsername.getText() != null)
                    newUser.setUsername(etUsername.getText().toString());
                else {
                    missing[1] = 1;
                    Toast.makeText(SignUpActivity.this, "Username cannot be empty.", Toast.LENGTH_SHORT).show();
                }
                if (etEmail.getText() != null)
                    newUser.setEmail(etEmail.getText().toString());
                else {
                    missing[2] = 1;
                    Toast.makeText(SignUpActivity.this, "Email cannot be empty.", Toast.LENGTH_SHORT).show();
                }
                if (etMobile.getText() != null)
                    newUser.setMobile(etMobile.getText().toString());
                else {
                    missing[3] = 1;
                    Toast.makeText(SignUpActivity.this, "Mobile cannot be empty.", Toast.LENGTH_SHORT).show();
                }
                if (etPassword.getText() != null && etReEnterPassword.getText() != null) {
                    //Check if Password fields match
                    if (etPassword.getText().toString().equals(etReEnterPassword.getText().toString())) {
                        newUser.setPaswordhashcode(etPassword.getText().toString());
                        passwordsMatch = 1;
                    } else {
                        Toast.makeText(SignUpActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                        etPassword.setText(NULL_STRING);
                        etReEnterPassword.setText(NULL_STRING);
                    }
                } else {
                    missing[4] = 1;
                    Toast.makeText(SignUpActivity.this, "Please choose a password.", Toast.LENGTH_SHORT).show();
                }
                if (missing[0] != 1 && missing[1] != 1 && missing[2] != 1 && missing[3] != 1 && missing[4] != 1 && passwordsMatch == 1) {
                    storeUserDetails();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void storeUserDetails(){

        Log.i(FOOTPRINT_LOGGER, "userDetails : " + newUser.toString());
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.registerUserInBackground(newUser, new RegisterUserCallback() {
            @Override
            public void done(String response) {
                if(!response.contains(newUser.getUsername())) {
                    Toast.makeText(SignUpActivity.this, "Signed up successfully", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(SignUpActivity.this, "Username/Email already exits", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
