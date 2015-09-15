package com.sacri.footprint_v3.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by bazinga on 12/09/15.
 */
public class PlaceDetails {

    private Integer placeID;
    private String title;
    private String description;
    private String location;
    private String category;
    private Character isActive;
    private File photoFile;

    public PlaceDetails() {
    }

    public PlaceDetails(String title, String description, String location, String category) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.category = category;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
                ", location='" + location + '\'' +
                ", isActive=" + isActive +
                ", photoFile=" + photoFile +
                '}';
    }
}
