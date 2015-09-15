package com.sacri.footprint_v3.entity;

import android.content.Intent;

/**
 * Created by Sagar Deswal on 11/09/15.
 *
 */
public class UserDetails {

    private Integer userID;
    private String username;
    private String fullname;
    private String mobile;
    private String email;
    private String paswordhashcode;

    public UserDetails() {
    }

    public UserDetails(String username, String paswordhashcode) {
        this.username = username;
        this.paswordhashcode = paswordhashcode;
    }

    public UserDetails(String username, String fullname, String mobile, String email) {
        this.email = email;
        this.fullname = fullname;
        this.mobile = mobile;
        this.username = username;
    }

    public String getPaswordhashcode() {
        return paswordhashcode;
    }

    public void setPaswordhashcode(String paswordhashcode) {
        this.paswordhashcode = paswordhashcode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "email='" + email + '\'' +
                ", userID=" + userID +
                ", username='" + username + '\'' +
                ", fullname='" + fullname + '\'' +
                ", mobile='" + mobile + '\'' +
                ", paswordhashcode='" + paswordhashcode + '\'' +
                '}';
    }
}
