package com.sacri.footprint_v3.activities;

import android.app.AlertDialog;

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
import com.sacri.footprint_v3.callback.LoginUserCallback;
import com.sacri.footprint_v3.dbaccess.ServerRequests;
import com.sacri.footprint_v3.entity.UserDetails;
import com.sacri.footprint_v3.utils.UserLocalStore;

public class LoginActivity extends AppCompatActivity
//        implements
//        GoogleApiClient.ConnectionCallbacks,
//        GoogleApiClient.OnConnectionFailedListener,View.OnClickListener{
{
    /* Request code used to invoke sign in user interactions. */
    private static final int REQUEST_G_SIGN_IN = 0;
    private static final int REQUEST_SIGN_IN = 1;
    private static final int REQUEST_SIGNUP = 2;
    /* Client used to interact with Google APIs. */
//    private GoogleApiClient mGoogleApiClient;
    /* Is there a ConnectionResult resolution in progress? */
//    private boolean mIsResolving = false;
    /* Should we automatically resolve ConnectionResults when possible? */
    private boolean mShouldResolve = false;
    private UserLocalStore userLocalStore;
    private EditText etEmail;
    private EditText etPassword;
    private static final String FOOTPRINT_LOGGER = "com.sacri.footprint_v3";
    private TextView tvSignupLink;
    private Button bnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userLocalStore = new UserLocalStore(this);


//        // Build GoogleApiClient with access to basic profile
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(Plus.API)
//                .addScope(new Scope(Scopes.PROFILE))
//                .build();

        //Get reference to widgets
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bnLogin = (Button) findViewById(R.id.bnLogin);
        tvSignupLink = (TextView) findViewById(R.id.tvSignupLink);
//        findViewById(R.id.sign_in_button).setOnClickListener(this);



        bnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = "";
                String password = "";
                if(etEmail.getText()!=null){
                    email= etEmail.getText().toString();
                    if(etPassword.getText()!=null){
                        password=etPassword.getText().toString();
                    }
                }
                loginUser(email,password);
            }
        });

        tvSignupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    public void loginUser(String email, String password){

        if (!validate()) {
            onLoginFailed();
            return;
        }
        bnLogin.setEnabled(false);
        UserDetails userDetails = new UserDetails(email,password);
        authenticate(userDetails);
    }

    public void onLoginSuccess() {
        bnLogin.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        bnLogin.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("enter a valid email address");
            valid = false;
        } else {
            etEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            etPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            etPassword.setError(null);
        }

        return valid;
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void authenticate(final UserDetails userDetails){
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.loginUserInBackground(userDetails, new LoginUserCallback() {
            @Override
            public void done(UserDetails returnedUserDetails) {

                if (returnedUserDetails == null) {
                    Log.i(FOOTPRINT_LOGGER, "returnedUserData is null");
                    showErrorMessage();
                    etPassword.setText("");
                    etEmail.setText("");
                    etEmail.bringToFront();
                    bnLogin.setEnabled(true);

                } else {
                    Log.i(FOOTPRINT_LOGGER, "returnedUserData: " + returnedUserDetails.toString());
                    logUserIn(returnedUserDetails);
                    onLoginSuccess();
                }
            }
        });
    }

//    @Override
//    public void onClick(View v) {
//        if (v.getId() == R.id.sign_in_button) {
//            onSignInClicked();
//        }
//
//        // ...
//    }
//
//    private void onSignInClicked() {
//        // User clicked the sign-in button, so begin the sign-in process and automatically
//        // attempt to resolve any errors that occur.
//        mShouldResolve = true;
//        mGoogleApiClient.connect();
//
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(FOOTPRINT_LOGGER, "onActivityResult:" + requestCode + ":" + resultCode + ":" + data);



//        if (requestCode == REQUEST_G_SIGN_IN) {
//            // If the error resolution was not successful we should not resolve further.
//            if (resultCode != RESULT_OK) {
//                mShouldResolve = false;
//            }
//
//            mIsResolving = false;
//            mGoogleApiClient.connect();
//        }
//        else
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // By default we just finish the Activity and log them in automatically

                if(userLocalStore.getUserLoggedIn()){
                    Intent intent = new Intent(this,FeedActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    finish();
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
//        mGoogleApiClient.connect();
        if(userLocalStore.getUserLoggedIn()){
            Intent intent = new Intent(this,FeedActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
//        if(mGoogleApiClient!=null)
//            mGoogleApiClient.disconnect();
    }


    //////////////////////////////GOOGLE PLAY SERVICES STARTS/////////////////////////////////

//    @Override
//    public void onConnected(Bundle bundle) {
//        // onConnected indicates that an account was selected on the device, that the selected
//        // account has granted any requested permissions to our app and that we were able to
//        // establish a service connection to Google Play services.
//        Log.d(FOOTPRINT_LOGGER, "onConnected:" + bundle);
//        mShouldResolve = false;
//
////        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
////            Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
////            String personName = currentPerson.getDisplayName();
//////            String personPhoto = currentPerson.getImage().getUrl();
//////            String personGooglePlusProfile = currentPerson.getUrl();
////            String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
////            UserDetails userDetails = new UserDetails(email,password);
////        }
//
//
//
//        // Show the signed-in UI
//        Intent intent = new Intent(LoginActivity.this, FeedActivity.class);
//        startActivity(intent);
//        finish();
//    }
//
//    @Override
//    public void onConnectionFailed(ConnectionResult connectionResult) {
//        // Could not connect to Google Play Services.  The user needs to select an account,
//        // grant permissions or resolve an error in order to sign in. Refer to the javadoc for
//        // ConnectionResult to see possible error codes.
//        Log.d(FOOTPRINT_LOGGER, "onConnectionFailed:" + connectionResult);
//
//        if (!mIsResolving && mShouldResolve) {
//            if (connectionResult.hasResolution()) {
//                try {
//                    connectionResult.startResolutionForResult(this, REQUEST_G_SIGN_IN);
//                    mIsResolving = true;
//                } catch (IntentSender.SendIntentException e) {
//                    Log.e(FOOTPRINT_LOGGER, "Could not resolve ConnectionResult.", e);
//                    mIsResolving = false;
//                    mGoogleApiClient.connect();
//                }
//            } else {
//                // Could not resolve the connection result, show the user an
//                // error dialog.
////                showErrorDialog(connectionResult);
//                Toast.makeText(LoginActivity.this, "Connection Failed", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            // Show the signed-out UI
//            Toast.makeText(LoginActivity.this, "Signed Out", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }


    //////////////////////////////GOOGLE PLAY SERVICES ENDS/////////////////////////////////

    private void showErrorMessage(){
        Log.i(FOOTPRINT_LOGGER, "showErrorMessage()");
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginActivity.this);
        dialogBuilder.setMessage("Incorrect User");
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();
    }

    private void logUserIn(UserDetails returnedUserDetails){
        userLocalStore.storeUserData(returnedUserDetails);
        userLocalStore.setUserLoggedIn(true);
        if(returnedUserDetails!=null) {
            Log.i(FOOTPRINT_LOGGER, "returnedUserDetails=" + returnedUserDetails.toString());
            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, FeedActivity.class);
            startActivity(intent);
        }
    }
}
