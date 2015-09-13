package com.sacri.footprint_v3.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sacri.footprint_v3.R;

/**
 * Created by Sagar Deswal on 13/09/15.
 */
public class FeedPlaceAdaptor extends ArrayAdapter<String> {
    public FeedPlaceAdaptor(Context context, String[] places) {
        super(context, R.layout.row_place_feed, places);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater placeInflator = LayoutInflater.from(getContext());
        View viewPlaceRow = placeInflator.inflate(R.layout.row_place_feed, parent, false);

        String place = getItem(position);
        TextView tvPlaceTitle = (TextView) viewPlaceRow.findViewById(R.id.tvPlaceTitle);
        ImageView ivPlaceThumbnail = (ImageView) viewPlaceRow.findViewById(R.id.ivPlaceThumbnail);

        tvPlaceTitle.setText(place);
        ivPlaceThumbnail.setImageResource(R.drawable.place_sample);

        return viewPlaceRow;
    }
}
