package com.sacri.footprint_v3.callback;

import com.sacri.footprint_v3.entity.Story;

import java.util.ArrayList;

/**
 * Created by Sagar Deswal on 24/09/15.
 */
public interface GetStoryCallback {
    void done(ArrayList<Story> storyArrayList);
}
