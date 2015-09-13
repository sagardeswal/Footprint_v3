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
public class FeedPostAdaptor extends ArrayAdapter<String> {
    public FeedPostAdaptor(Context context, String[] posts) {
        super(context, R.layout.row_post_feed, posts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater postInflator = LayoutInflater.from(getContext());
        View viewPostRow = postInflator.inflate(R.layout.row_post_feed, parent, false);

        String postText = getItem(position);
        TextView tvPostText = (TextView) viewPostRow.findViewById(R.id.tvPostText);
        ImageView ivPostThumbnail = (ImageView) viewPostRow.findViewById(R.id.ivPostThumbnail);

        tvPostText.setText(postText);
        ivPostThumbnail.setImageResource(R.drawable.post_sample);

        return viewPostRow;
    }
}

