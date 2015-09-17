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
import com.sacri.footprint_v3.entity.EventDetails;

import java.util.ArrayList;

/**
 * Created by Sagar Deswal on 17/09/15.
 */
public class FeedEventAdaptor extends ArrayAdapter<EventDetails> {

    private static final String FOOTPRINT_LOGGER = "com.sacri.footprint_v3";


    public FeedEventAdaptor(Context context, ArrayList<EventDetails> events) {
        super(context, R.layout.row_event_feed, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater eventInflater = LayoutInflater.from(getContext());
        View viewEventRow = eventInflater.inflate(R.layout.row_event_feed, parent, false);

        EventDetails event = getItem(position);
        Log.i(FOOTPRINT_LOGGER, "event title: " + event.getEventTitle());
        TextView tvEventTitle = (TextView) viewEventRow.findViewById(R.id.tvEventTitle);
        ImageView ivEventThumbnail = (ImageView) viewEventRow.findViewById(R.id.ivEventThumbnail);

        tvEventTitle.setText(event.getEventTitle());
        ivEventThumbnail.setImageResource(R.drawable.place_sample);

        return viewEventRow;
    }


}
