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
import android.widget.TextView;
import android.widget.Toast;
import com.sacri.footprint_v3.R;
import com.sacri.footprint_v3.callback.RegisterUserCallback;
import com.sacri.footprint_v3.dbaccess.ServerRequests;
import com.sacri.footprint_v3.entity.UserDetails;
import com.sacri.footprint_v3.utils.UserLocalStore;

public class SignUpActivity extends AppCompatActivity {

    private EditText etFullName;
    private EditText etPassword;
    private EditText etEmail;
    private EditText etMobile;
    private TextView tvLoginLink;
    private UserDetails newUser;
    private Button bnSignUp;
    private UserLocalStore userLocalStore;
    private static final String FOOTPRINT_LOGGER = "com.sacri.footprint_v3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        userLocalStore = new UserLocalStore(this);
        //Get reference to widgets
        etFullName = (EditText) findViewById(R.id.etFullname);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etMobile = (EditText) findViewById(R.id.etMobile);
        bnSignUp = (Button) findViewById(R.id.bnSignUp);
        tvLoginLink = (TextView) findViewById(R.id.tvLoginLink);

        //Create new instance of UserDetails
        newUser = new UserDetails();
        bnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signup();
            }
        });
        tvLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void signup() {
        Log.d(FOOTPRINT_LOGGER, "Signup");

        if (!validate()) {
            onSignupFailed("Please fill the details correctly");
            return;
        }
        bnSignUp.setEnabled(false);
        storeUserDetails();
    }

    public void onSignupSuccess() {
        bnSignUp.setEnabled(true);
        setResult(RESULT_OK, null);
//        logUserIn(newUser);
        Toast.makeText(SignUpActivity.this, "Signed up successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void onSignupFailed(String message) {
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();

        bnSignUp.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;


        String name = etFullName.getText().toString();
        String email = etEmail.getText().toString();
        String mobile = etMobile.getText().toString();
        String password = etPassword.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            etFullName.setError("at least 3 characters");
            valid = false;
        } else {
            newUser.setFullname(name);
            etFullName.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("enter a valid email address");
            valid = false;
        } else {
            newUser.setEmail(email);
            etEmail.setError(null);
        }

        if (mobile.isEmpty() || mobile.length() < 10) {
            etMobile.setError("not valid mobile number");
            valid = false;
        } else {
            newUser.setMobile(mobile);
            etMobile.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            etPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            newUser.setPaswordhashcode(password);
            etPassword.setError(null);
        }
        return valid;
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
                if (!response.contains(newUser.getEmail())) {
                    onSignupSuccess();
                } else {
                    onSignupFailed("Username/Email already exits");
                }
            }
        });
    }

    private void logUserIn(UserDetails userDetails){
        userLocalStore.storeUserData(userDetails);
        userLocalStore.setUserLoggedIn(true);
    }
}
