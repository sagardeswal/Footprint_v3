package com.sacri.footprint_v3.entity;

/**
 * Created by bazinga on 11/09/15.
 */
public class UserDetails {

    private String username;
    private String fullname;
    private String mobile;
    private String email;
    private String paswordhashcode;

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
}
