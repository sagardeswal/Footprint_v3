package com.sacri.footprint_v3.callback;

import com.sacri.footprint_v3.entity.PlaceDetails;

import java.util.ArrayList;

/**
 * Created by Sagar Deswal on 14/09/15.
 */
public interface GetPlaceCallback {
    void done(ArrayList<PlaceDetails> placeDetailsArrayList);
}
