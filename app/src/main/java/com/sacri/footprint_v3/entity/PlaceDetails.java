package com.sacri.footprint_v3.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sagar Deswal on 12/09/15.
 */
public class PlaceDetails {

    private Integer placeID;
    private String title;
    private String description;
    private Integer locID;
    private String category;
    private Character isActive;
    private File photoFile;
    private Double longitude;
    private Double latitude;
    private Integer adminID;

    public PlaceDetails() {
    }

    public PlaceDetails(Integer locID, Integer placeID, Integer adminID) {
        this.locID = locID;
        this.placeID = placeID;
        this.adminID = adminID;
    }

    public PlaceDetails(Integer placeID, String title, String description, Integer locID, String category, Double longitude, Double latitude, Integer adminID) {
        this.placeID = placeID;
        this.title = title;
        this.description = description;
        this.locID = locID;
        this.category = category;
        this.longitude = longitude;
        this.latitude = latitude;
        this.adminID = adminID;
    }

    public Integer getAdminID() {
        return adminID;
    }

    public void setAdminID(Integer adminID) {
        this.adminID = adminID;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Character getIsActive() {
        return isActive;
    }

    public void setIsActive(Character isActive) {
        this.isActive = isActive;
    }

    public Integer getPlaceID() {
        return placeID;
    }

    public void setPlaceID(Integer placeID) {
        this.placeID = placeID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLocID() {
        return locID;
    }

    public void setLocID(Integer locID) {
        this.locID = locID;
    }

    public File getPhotoFile() {
        return photoFile;
    }

    public void setPhotoFile(File photoFile) {
        this.photoFile = photoFile;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "PlaceDetails{" +
                "adminID=" + adminID +
                ", placeID=" + placeID +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", locID=" + locID +
                ", category='" + category + '\'' +
                ", isActive=" + isActive +
                ", photoFile=" + photoFile +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
