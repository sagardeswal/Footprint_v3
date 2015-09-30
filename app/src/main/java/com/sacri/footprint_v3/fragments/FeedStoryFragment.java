package com.sacri.footprint_v3.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.sacri.footprint_v3.R;
import com.sacri.footprint_v3.activities.FeedActivity;
import com.sacri.footprint_v3.entity.Story;
import com.sacri.footprint_v3.utils.FeedStoryAdaptor;

import java.util.ArrayList;

public class FeedStoryFragment extends Fragment {

    private static final String FOOTPRINT_LOGGER = "com.sacri.footprint_v3";
    private ListAdapter feedStoryAdaptor;
    private View v;
    private ArrayList<Story> storyArrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_story_feed, container, false);

        storyArrayList = ((FeedActivity) getActivity()).getmStoryArrayList();

        if (storyArrayList == null) {
            Log.i(FOOTPRINT_LOGGER, "storyArrayList is null");
        } else {
            Log.i(FOOTPRINT_LOGGER, "storyArrayList size: " + storyArrayList.size());
            setListAdaptor();
        }

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    private void setListAdaptor( ){
        feedStoryAdaptor = new FeedStoryAdaptor(getActivity(), storyArrayList);
        ListView contentList = (ListView) v.findViewById(R.id.lvStories);
        contentList.setAdapter(feedStoryAdaptor);

        contentList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Story selectedStory = storyArrayList.get(position);
                        Toast.makeText(getActivity(), selectedStory.getText(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }




    private void showErrorMessage(){
        Log.i(FOOTPRINT_LOGGER, "showErrorMessage()");
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setMessage("No Places Found");
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();
    }

}
