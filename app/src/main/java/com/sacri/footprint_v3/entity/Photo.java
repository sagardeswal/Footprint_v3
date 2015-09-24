package com.sacri.footprint_v3.entity;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Sagar Deswal on 24/09/15.
 */
public class Photo {

    private Integer photoID;
    private File photo;
    private HashMap<Integer,Comment> comments;
    private HashMap<Integer,Like> likes;

    public Photo(File photo) {
        this.photo = photo;
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

    public File getPhoto() {
        return photo;
    }

    public void setPhoto(File photo) {
        this.photo = photo;
    }

    public Integer getPhotoID() {
        return photoID;
    }

    public void setPhotoID(Integer photoID) {
        this.photoID = photoID;
    }
}
