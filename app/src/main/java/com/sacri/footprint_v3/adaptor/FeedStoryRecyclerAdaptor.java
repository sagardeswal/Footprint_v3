package com.sacri.footprint_v3.adaptor;

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
import com.sacri.footprint_v3.activities.ViewStoryActivity;
import com.sacri.footprint_v3.entity.Story;

import java.util.ArrayList;

/**
 * Created by Sagar Deswal on 30/09/15.
 */
public class FeedStoryRecyclerAdaptor extends RecyclerView.Adapter<FeedStoryRecyclerAdaptor.ViewHolder> {

    private static final String FOOTPRINT_LOGGER = "com.sacri.footprint_v3";
    private int mBackground;
    private ArrayList<Story> mValues;

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

    public FeedStoryRecyclerAdaptor(Context context, ArrayList<Story> storyArrayList) {
        TypedValue mTypedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        mValues = storyArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_story_feed, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mBoundString = mValues.get(position).getText();
        holder.tvTitle.setText(mValues.get(position).getText());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Story selectedStory = mValues.get(position);
                Context context = v.getContext();
                Intent viewPlaceIntent = new Intent(context, ViewStoryActivity.class);
                Bundle storyData = new Bundle();
                Log.i(FOOTPRINT_LOGGER, "storyID=" + selectedStory.getStoryID());
                storyData.putInt("storyID", selectedStory.getStoryID());
                Log.i(FOOTPRINT_LOGGER, "storyText=" + selectedStory.getText());
                storyData.putString("storyText", selectedStory.getText());
                Log.i(FOOTPRINT_LOGGER, "storyLocID=" + selectedStory.getLocID());
                storyData.putInt("storyLocID", selectedStory.getLocID());
                Log.i(FOOTPRINT_LOGGER, "StoryPlaceID=" + selectedStory.getPlaceID());
                storyData.putInt("StoryPlaceID", selectedStory.getPlaceID());
                Log.i(FOOTPRINT_LOGGER, "StoryEventID=" + selectedStory.getEventID());
                storyData.putInt("StoryEventID", selectedStory.getEventID());
                viewPlaceIntent.putExtra("storyData", storyData);
                context.startActivity(viewPlaceIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setmValues(ArrayList<Story> mValues) {
        this.mValues = mValues;
    }
}

