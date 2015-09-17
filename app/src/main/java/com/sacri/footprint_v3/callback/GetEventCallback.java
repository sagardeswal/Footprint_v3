package com.sacri.footprint_v3.callback;

import com.sacri.footprint_v3.entity.EventDetails;

import java.util.ArrayList;

/**
 * Created by Sagar Deswal on 18/09/15.
 */
public interface GetEventCallback {
    void done(ArrayList<EventDetails> eventDetailsArrayList);
}

