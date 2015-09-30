package com.sacri.footprint_v3.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sacri.footprint_v3.R;
import com.sacri.footprint_v3.activities.ViewPlaceProfileActivity;
import com.sacri.footprint_v3.entity.PlaceDetails;

import java.util.ArrayList;

/*
    Created by Sagar Deswal on 27/09/15.
 */
public class FeedPlaceRecyclerAdaptor extends RecyclerView.Adapter<FeedPlaceRecyclerAdaptor.ViewHolder> {

    private static final String FOOTPRINT_LOGGER = "com.sacri.footprint_v3";
    private int mBackground;
    private ArrayList<PlaceDetails> mValues;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public String mBoundString;

        public final View mView;
        public final ImageView ivThumbnail;
        public final TextView tvTitle;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ivThumbnail = (ImageView) view.findViewById(R.id.ivThumbnail);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvTitle.getText();
        }
    }

//    public String getValueAt(int position) {
//        return mValues.get(position).getTitle();
//    }

    public FeedPlaceRecyclerAdaptor(Context context, ArrayList<PlaceDetails> placeDetailsArrayList) {
        TypedValue mTypedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        mValues = placeDetailsArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_place_feed, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mBoundString = mValues.get(position).getTitle();
        holder.tvTitle.setText(mValues.get(position).getTitle());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaceDetails selectedPlace = mValues.get(position);
                Context context = v.getContext();
                Intent viewPlaceIntent = new Intent(context, ViewPlaceProfileActivity.class);
                Bundle placeData = new Bundle();
                Log.i(FOOTPRINT_LOGGER, "placeID=" + selectedPlace.getPlaceID());
                placeData.putInt("placeID", selectedPlace.getPlaceID());
                Log.i(FOOTPRINT_LOGGER, "placeTitle=" + selectedPlace.getTitle());
                placeData.putString("placeTitle", selectedPlace.getTitle());
                Log.i(FOOTPRINT_LOGGER, "placeLocID=" + selectedPlace.getLocID());
                placeData.putInt("placeLocID", selectedPlace.getLocID());
                Log.i(FOOTPRINT_LOGGER, "placeDescription=" + selectedPlace.getDescription());
                placeData.putString("placeDescription", selectedPlace.getDescription());
                Log.i(FOOTPRINT_LOGGER, "placeCategory=" + selectedPlace.getCategory());
                placeData.putString("placeCategory", selectedPlace.getCategory());
                viewPlaceIntent.putExtra("placeData", placeData);
                context.startActivity(viewPlaceIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
