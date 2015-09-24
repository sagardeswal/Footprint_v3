package com.sacri.footprint_v3.entity;

import com.sacri.footprint_v3.types.LikeType;

/**
 * Created by Sagar Deswal on 24/09/15.
 */
public abstract class Like implements LikeType {

    private Integer likeID;

    public Integer getLikeID() {
        return likeID;
    }

    public void setLikeID(Integer likeID) {
        this.likeID = likeID;
    }
}
