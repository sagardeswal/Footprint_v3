package com.sacri.footprint_v3.entity;

/**
 * Created by Sagar Deswal on 14/09/15.
 */
public class ActivityDetails {

    private Integer activityID;
    private String title;
    private String description;

    public Integer getActivityID() {
        return activityID;
    }

    public void setActivityID(Integer activityID) {
        this.activityID = activityID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
