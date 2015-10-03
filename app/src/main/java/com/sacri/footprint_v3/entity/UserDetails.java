package com.sacri.footprint_v3.entity;

/**
 * Created by Sagar Deswal on 11/09/15.
 *
 */
public class UserDetails {

    private Integer userID;
    private String fullname;
    private String mobile;
    private String email;
    private String paswordhashcode;
    private Character canAddPlace;

    public UserDetails() {
    }

    public UserDetails(String email, String paswordhashcode) {
        this.email = email;
        this.paswordhashcode = paswordhashcode;
    }

    public UserDetails(Integer userID, String fullname, String mobile, String email, Character canAddPlace) {
        this.canAddPlace = canAddPlace;
        this.email = email;
        this.fullname = fullname;
        this.mobile = mobile;
        this.userID = userID;
    }

    public String getPaswordhashcode() {
        return paswordhashcode;
    }

    public void setPaswordhashcode(String paswordhashcode) {
        this.paswordhashcode = paswordhashcode;
    }

    public Character getCanAddPlace() {
        return canAddPlace;
    }

    public void setCanAddPlace(Character canAddPlace) {
        this.canAddPlace = canAddPlace;
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


    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "canAddPlace=" + canAddPlace +
                ", userID=" + userID +
                ", fullname='" + fullname + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", paswordhashcode='" + paswordhashcode + '\'' +
                '}';
    }
}
