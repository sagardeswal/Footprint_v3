package com.sacri.footprint_v3.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.common.api.GoogleApiClient;
import com.sacri.footprint_v3.entity.UserDetails;

/**
 * Created by Sagar Deswal on 14/09/15.
 */
public class UserLocalStore {

    public static final String SP_USER_DETAILS = "userDetails";

    SharedPreferences userDetailsLocalDatabase;

    public UserLocalStore(Context context){
        userDetailsLocalDatabase = context.getSharedPreferences(SP_USER_DETAILS, 0);
    }

    public void storeUserData(UserDetails userDetails){
        SharedPreferences.Editor spEditor = userDetailsLocalDatabase.edit();
        spEditor.putString("fullname", userDetails.getFullname());
        spEditor.putString("username", userDetails.getUsername());
        spEditor.putString("email", userDetails.getEmail());
        spEditor.putString("mobile", userDetails.getMobile());
        spEditor.commit();
    }

    public UserDetails getLoggedInUser(){
        String fullname = userDetailsLocalDatabase.getString("fullname", "");
        String username = userDetailsLocalDatabase.getString("username", "");
        String email = userDetailsLocalDatabase.getString("mobile", "");
        String mobile = userDetailsLocalDatabase.getString("mobile", "");
        UserDetails userDetails = new UserDetails(username,fullname,email,mobile);
        return userDetails;
    }

    public void setUserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor spEditor = userDetailsLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }

    public boolean getUserLoggedIn(){
        if(userDetailsLocalDatabase.getBoolean("loggedIn", false)==true){
            return true;
        } else {
            return false;
        }
    }

    public void clearUserData(){
        SharedPreferences.Editor spEditor = userDetailsLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }
}
