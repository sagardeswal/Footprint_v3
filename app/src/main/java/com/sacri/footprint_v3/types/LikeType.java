package com.sacri.footprint_v3.types;

import com.sacri.footprint_v3.entity.Like;

/**
 * Created by Sagar Deswal on 24/09/15.
 */
public interface LikeType {

    public void addLike(Like like);
    public void removeLike(Like like);
    public Integer getLikeCount();

}
