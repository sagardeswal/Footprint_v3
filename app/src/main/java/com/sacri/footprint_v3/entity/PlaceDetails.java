package com.sacri.footprint_v3.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

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



    public PlaceDetails() {
    }

    public PlaceDetails(Integer locID, Integer placeID) {
        this.locID = locID;
        this.placeID = placeID;
    }

    public PlaceDetails(String title, String description, Integer locID, String category, Double longitude, Double latitude) {
        this.title = title;
        this.description = description;
        this.locID = locID;
        this.category = category;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public PlaceDetails(Integer placeID, String title, String description, Integer locID, String category, Double longitude, Double latitude) {
        this.placeID = placeID;
        this.title = title;
        this.description = description;
        this.locID = locID;
        this.category = category;
        this.longitude = longitude;
        this.latitude = latitude;
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
                "category='" + category + '\'' +
                ", placeID=" + placeID +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", locID=" + locID +
                ", isActive=" + isActive +
                ", photoFile=" + photoFile +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
