package com.sacri.footprint_v3.adaptor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sacri.footprint_v3.R;
import com.sacri.footprint_v3.entity.Story;

import java.util.ArrayList;

/**
 * Created by Sagar Deswal on 13/09/15.
 */
public class FeedStoryAdaptor extends ArrayAdapter<Story> {

    private static final String FOOTPRINT_LOGGER = "com.sacri.footprint_v3";

    public FeedStoryAdaptor(Context context, ArrayList<Story> stories) {
        super(context, R.layout.row_story_feed, stories);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater postInflator = LayoutInflater.from(getContext());
        View viewPostRow = postInflator.inflate(R.layout.row_story_feed, parent, false);

        String postText = getItem(position).getText();
        Log.i(FOOTPRINT_LOGGER, "Story text: " + postText);
        TextView tvPostText = (TextView) viewPostRow.findViewById(R.id.tvTitle);
        ImageView ivPostThumbnail = (ImageView) viewPostRow.findViewById(R.id.ivThumbnail);

        tvPostText.setText(postText);
        ivPostThumbnail.setImageResource(R.drawable.story_sample);

        return viewPostRow;
    }
}

