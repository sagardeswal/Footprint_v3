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
 * Created by bazinga on 13/09/15.
 */
public class ViewPlacesAdaptor extends ArrayAdapter<String> {
    public ViewPlacesAdaptor(Context context, String[] places) {
        super(context, R.layout.view_place_row, places);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater placeInflator = LayoutInflater.from(getContext());
        View viewPlaceRow = placeInflator.inflate(R.layout.view_place_row, parent, false);

        String place = getItem(position);
        TextView tvPlaceTitle = (TextView) viewPlaceRow.findViewById(R.id.tvPlaceTitle);
        ImageView ivPlaceThumbnail = (ImageView) viewPlaceRow.findViewById(R.id.ivPlaceThumbnail);

        tvPlaceTitle.setText(place);
        ivPlaceThumbnail.setImageResource(R.drawable.place_sample);

        return viewPlaceRow;
    }
}
