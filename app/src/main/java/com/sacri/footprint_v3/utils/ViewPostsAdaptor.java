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
public class ViewPostsAdaptor extends ArrayAdapter<String> {
    public ViewPostsAdaptor(Context context, String[] posts) {
        super(context, R.layout.view_post_row, posts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater postInflator = LayoutInflater.from(getContext());
        View viewPostRow = postInflator.inflate(R.layout.view_post_row, parent, false);

        String postText = getItem(position);
        TextView tvPostText = (TextView) viewPostRow.findViewById(R.id.tvPostText);
        ImageView ivPostThumbnail = (ImageView) viewPostRow.findViewById(R.id.ivPostThumbnail);

        tvPostText.setText(postText);
        ivPostThumbnail.setImageResource(R.drawable.post_sample);

        return viewPostRow;
    }
}

