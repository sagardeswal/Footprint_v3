package com.sacri.footprint_v3.callback;

import com.sacri.footprint_v3.entity.Story;

import java.util.ArrayList;

/**
 * Created by Sagar Deswal on 29/09/15.
 */
public interface GetStoriesForEventCallback {
    void done(ArrayList<Story> storyArrayList);
}
