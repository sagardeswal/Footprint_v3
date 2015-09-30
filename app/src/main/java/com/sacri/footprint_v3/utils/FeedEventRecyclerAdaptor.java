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
import com.sacri.footprint_v3.activities.ViewEventActivity;
import com.sacri.footprint_v3.entity.EventDetails;

import java.util.ArrayList;

/**
 * Created by Sagar Deswal on 29/09/2015
 */
public class FeedEventRecyclerAdaptor extends RecyclerView.Adapter<FeedEventRecyclerAdaptor.ViewHolder> {

    private static final String FOOTPRINT_LOGGER = "com.sacri.footprint_v3";
    private int mBackground;
    private ArrayList<EventDetails> mValues;

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

    public FeedEventRecyclerAdaptor(Context context, ArrayList<EventDetails> eventDetailsArrayList) {
        Log.i(FOOTPRINT_LOGGER,"FeedEventRecyclerAdaptor()");
        TypedValue mTypedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        mValues = eventDetailsArrayList;
        Log.i(FOOTPRINT_LOGGER,"mValues=" + eventDetailsArrayList.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_event_feed, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mBoundString = mValues.get(position).getEventTitle();
        holder.tvTitle.setText(mValues.get(position).getEventTitle());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(FOOTPRINT_LOGGER,"holder.mView.setOnClickListener()");
                EventDetails selectedEvent = mValues.get(position);
                Context context = v.getContext();
                Intent viewEventIntent = new Intent(context, ViewEventActivity.class);
                Bundle eventData = new Bundle();
                Log.i(FOOTPRINT_LOGGER, "eventID=" + selectedEvent.getEventID());
                eventData.putInt("eventID", selectedEvent.getEventID());
                Log.i(FOOTPRINT_LOGGER, "eventTitle=" + selectedEvent.getEventTitle());
                eventData.putString("eventTitle", selectedEvent.getEventTitle());
                Log.i(FOOTPRINT_LOGGER, "eventDescription=" + selectedEvent.getEventDescription());
                eventData.putString("eventDescription", selectedEvent.getEventDescription());
                viewEventIntent.putExtra("eventData", eventData);
                context.startActivity(viewEventIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}