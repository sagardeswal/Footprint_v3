package com.sacri.footprint_v3.entity;

/**
 * Created by Sagar Deswal on 24/09/15.
 */
public class Comment {

    private Integer commentID;
    private Integer commentText;
    private Integer userID;
    private Integer photoID;
    private Integer storyID;

    public Integer getCommentID() {
        return commentID;
    }

    public void setCommentID(Integer commentID) {
        this.commentID = commentID;
    }

    public Integer getCommentText() {
        return commentText;
    }

    public void setCommentText(Integer commentText) {
        this.commentText = commentText;
    }

    public Integer getPhotoID() {
        return photoID;
    }

    public void setPhotoID(Integer photoID) {
        this.photoID = photoID;
    }

    public Integer getStoryID() {
        return storyID;
    }

    public void setStoryID(Integer storyID) {
        this.storyID = storyID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }
}
