package com.sacri.footprint_v3.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Sagar Deswal on 24/09/15.
 */
public class Story {

    private Integer storyID;
    private Integer userID;
    private Integer placeID;
    private Integer eventID;
    private Integer locID;
    private String text;
    private HashMap<Integer,Comment> comments;
    private HashMap<Integer, Like> likes;
    private HashMap<Integer, Photo> photos;

    public Story() {
    }

    public Story(Integer locID, Integer placeID, Integer storyID, String text, Integer userID) {
        this.locID = locID;
        this.placeID = placeID;
        this.storyID = storyID;
        this.text = text;
        this.userID = userID;
    }

    public HashMap<Integer, Comment> getComments() {
        return comments;
    }

    public void setComments(HashMap<Integer, Comment> comments) {
        this.comments = comments;
    }

    public HashMap<Integer, Like> getLikes() {
        return likes;
    }

    public void setLikes(HashMap<Integer, Like> likes) {
        this.likes = likes;
    }

    public HashMap<Integer, Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(HashMap<Integer, Photo> photos) {
        this.photos = photos;
    }

    public Integer getEventID() {
        return eventID;
    }

    public void setEventID(Integer eventID) {
        this.eventID = eventID;
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

    public Integer getStoryID() {
        return storyID;
    }

    public void setStoryID(Integer storyID) {
        this.storyID = storyID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "Story{" +
                "comments=" + comments +
                ", storyID=" + storyID +
                ", userID=" + userID +
                ", placeID=" + placeID +
                ", eventID=" + eventID +
                ", locID=" + locID +
                ", text='" + text + '\'' +
                ", likes=" + likes +
                ", photos=" + photos +
                '}';
    }
}
