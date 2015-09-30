package com.sacri.footprint_v3.entity;

import java.util.Date;

/**
 * Created by Sagar Deswal on 17/09/15.
 */
public class EventDetails {

    private Integer eventID;
    private Integer placeID;
    private Integer locID;
    private String eventTitle;
    private String eventDescription;
    private Boolean repeatedWeekly;
    private Date startDate;
    private Date endDate;
    private Integer startTimeHour;
    private Integer startTimeMinutes;
    private Integer endTimeHour;
    private Integer endTimeMinutes;
    private Double longitude;
    private Double latitude;
    private String address;

    public EventDetails() {
    }

    public EventDetails(Integer eventID, Integer placeID, Integer locID, String eventTitle, String eventDescription, Boolean repeatedWeekly, Date startDate, Date endDate, Integer startTimeHour, Integer startTimeMinutes, Integer endTimeHour, Integer endTimeMinutes, Double longitude, Double latitude, String address) {
        this.eventID = eventID;
        this.placeID = placeID;
        this.locID = locID;
        this.address = address;
        this.endDate = endDate;
        this.endTimeHour = endTimeHour;
        this.endTimeMinutes = endTimeMinutes;
        this.eventDescription = eventDescription;
        this.eventTitle = eventTitle;
        this.latitude = latitude;
        this.longitude = longitude;
        this.repeatedWeekly = repeatedWeekly;
        this.startDate = startDate;
        this.startTimeHour = startTimeHour;
        this.startTimeMinutes = startTimeMinutes;
    }

    public Integer getLocID() {
        return locID;
    }

    public void setLocID(Integer locID) {
        this.locID = locID;
    }

    public Integer getPlaceID() {
        return placeID;
    }

    public void setPlaceID(Integer placeID) {
        this.placeID = placeID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getEndTimeHour() {
        return endTimeHour;
    }

    public void setEndTimeHour(Integer endTimeHour) {
        this.endTimeHour = endTimeHour;
    }

    public Integer getEndTimeMinutes() {
        return endTimeMinutes;
    }

    public void setEndTimeMinutes(Integer endTimeMinutes) {
        this.endTimeMinutes = endTimeMinutes;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public Integer getEventID() {
        return eventID;
    }

    public void setEventID(Integer eventID) {
        this.eventID = eventID;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public Boolean getRepeatedWeekly() {
        return repeatedWeekly;
    }

    public void setRepeatedWeekly(Boolean repeatedWeekly) {
        this.repeatedWeekly = repeatedWeekly;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getStartTimeHour() {
        return startTimeHour;
    }

    public void setStartTimeHour(Integer startTimeHour) {
        this.startTimeHour = startTimeHour;
    }

    public Integer getStartTimeMinutes() {
        return startTimeMinutes;
    }

    public void setStartTimeMinutes(Integer startTimeMinutes) {
        this.startTimeMinutes = startTimeMinutes;
    }

    @Override
    public String toString() {
        return "EventDetails{" +
                "address='" + address + '\'' +
                ", eventID=" + eventID +
                ", placeID=" + placeID +
                ", locID=" + locID +
                ", eventTitle='" + eventTitle + '\'' +
                ", eventDescription='" + eventDescription + '\'' +
                ", repeatedWeekly=" + repeatedWeekly +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", startTimeHour=" + startTimeHour +
                ", startTimeMinutes=" + startTimeMinutes +
                ", endTimeHour=" + endTimeHour +
                ", endTimeMinutes=" + endTimeMinutes +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
