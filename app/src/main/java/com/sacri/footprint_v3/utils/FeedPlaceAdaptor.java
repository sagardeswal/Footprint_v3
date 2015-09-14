package com.sacri.footprint_v3.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sacri.footprint_v3.R;
import com.sacri.footprint_v3.entity.PlaceDetails;

import java.util.ArrayList;

/**
 * Created by Sagar Deswal on 13/09/15.
 */
public class FeedPlaceAdaptor extends ArrayAdapter<PlaceDetails> {

    private static final String FOOTPRINT_LOGGER = "com.sacri.footprint_v3";


    public FeedPlaceAdaptor(Context context, ArrayList<PlaceDetails> places) {
        super(context, R.layout.row_place_feed, places);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater placeInflator = LayoutInflater.from(getContext());
        View viewPlaceRow = placeInflator.inflate(R.layout.row_place_feed, parent, false);

        PlaceDetails place = getItem(position);
        Log.i(FOOTPRINT_LOGGER,"place title: " + place.getTitle());
        TextView tvPlaceTitle = (TextView) viewPlaceRow.findViewById(R.id.tvPlaceTitle);
        ImageView ivPlaceThumbnail = (ImageView) viewPlaceRow.findViewById(R.id.ivPlaceThumbnail);

        tvPlaceTitle.setText(place.getTitle());
        ivPlaceThumbnail.setImageResource(R.drawable.place_sample);

        return viewPlaceRow;
    }
}
