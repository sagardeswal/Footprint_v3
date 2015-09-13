package com.sacri.footprint_v3.dbaccess;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.sacri.footprint_v3.entity.UserDetails;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import com.sacri.footprint_v3.callback.GetUserCallback;

/**
 * Created by Sagar Deswal on 13/09/15.
 */
public class ServerRequests {

    private static final String FOOTPRINT_LOGGER = "com.sacri.footprint_v3";
    ProgressDialog progressDialog;
    private static int RESULT_LOAD_IMG = 1;
    public static final int CONNECTION_TIMEOUT = 1000 * 15;
    public static final String SERVER_ADDRESS = "http://www.footprint.comuv.com/";

    public ServerRequests(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please wait..");
    }

    public void storeUserDataInBackground(UserDetails userDetails, GetUserCallback getUserCallback){
        progressDialog.show();
        new StoreUserDataAsyncTask(userDetails,getUserCallback).execute();
    }

    public void fetchUserDataInBackground(UserDetails userDetails, GetUserCallback getUserCallback){
        progressDialog.show();
        new FetchUserDataAsyncTask(userDetails,getUserCallback).execute();
    }

    public class StoreUserDataAsyncTask extends AsyncTask<Void, Void, Void> {

        UserDetails userDetails;
        GetUserCallback getUserCallback;

        StoreUserDataAsyncTask(UserDetails userDetails, GetUserCallback getUserCallback){
            this.userDetails = userDetails;
            this.getUserCallback = getUserCallback;
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.i(FOOTPRINT_LOGGER, "doInBackground begins");
            ArrayList<NameValuePair> dataToSend = new ArrayList<NameValuePair>();
            dataToSend.add(new BasicNameValuePair("usr_fullname", userDetails.getFullname()));
            dataToSend.add(new BasicNameValuePair("usr_username", userDetails.getUsername()));
            dataToSend.add(new BasicNameValuePair("usr_email", userDetails.getEmail()));
            dataToSend.add(new BasicNameValuePair("usr_mobile", userDetails.getMobile()));
            dataToSend.add(new BasicNameValuePair("usr_passwordhashcode", userDetails.getPaswordhashcode()));

            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpParams);
            HttpPost httpPost = new HttpPost(SERVER_ADDRESS + "register.php");
            try{
                httpPost.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(httpPost);
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            getUserCallback.done(null);
            super.onPostExecute(aVoid);
        }
    }

    public class FetchUserDataAsyncTask extends AsyncTask<Void, Void, UserDetails> {

        UserDetails userDetails;
        GetUserCallback getUserCallback;

        FetchUserDataAsyncTask(UserDetails userDetails, GetUserCallback getUserCallback) {
            this.userDetails = userDetails;
            this.getUserCallback = getUserCallback;
        }

        @Override
        protected UserDetails doInBackground(Void... params) {
            Log.i(FOOTPRINT_LOGGER, "doInBackground begins");
            ArrayList<NameValuePair> dataToSend = new ArrayList<NameValuePair>();
            dataToSend.add(new BasicNameValuePair("usr_username", userDetails.getUsername()));
            Log.i(FOOTPRINT_LOGGER, "username=" + userDetails.getUsername());
            dataToSend.add(new BasicNameValuePair("usr_password", userDetails.getPaswordhashcode()));
            Log.i(FOOTPRINT_LOGGER, "password=" + userDetails.getPaswordhashcode());
            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpParams);
            HttpPost httpPost = new HttpPost(SERVER_ADDRESS + "login.php");

            UserDetails returnedUserDetails = null;
            try{
                httpPost.setEntity(new UrlEncodedFormEntity(dataToSend));

                Log.i(FOOTPRINT_LOGGER, "httpPost Entity : " + httpPost.getEntity().toString());
                HttpResponse httpResponse = client.execute(httpPost);

                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);

                Log.i(FOOTPRINT_LOGGER, "Result : " + result);
                if(result.length()==154){
                    returnedUserDetails = null;
                    Log.i(FOOTPRINT_LOGGER, "JSON Object is Null : ");
                }else{
                    JSONObject jsonObject = new JSONObject(result);
                    String fullname = jsonObject.getString("usr_fullname");
                    String mobile = jsonObject.getString("usr_mobile");
                    String email = jsonObject.getString("usr_email");
                    String username = jsonObject.getString("usr_username");
                    returnedUserDetails = new UserDetails(username,fullname,mobile,email);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            if(returnedUserDetails==null){
                Log.i(FOOTPRINT_LOGGER, "returnedUserDetails is Null : ");
            }
            return returnedUserDetails;
        }

        @Override
        protected void onPostExecute(UserDetails returnedUserDetails) {
            progressDialog.dismiss();
            getUserCallback.done(returnedUserDetails);
            super.onPostExecute(returnedUserDetails);
        }
    }
}
