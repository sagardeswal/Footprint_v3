package com.sacri.footprint_v3.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.sacri.footprint_v3.R;
import com.sacri.footprint_v3.utils.FeedPostAdaptor;

public class FeedPostFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_post_feed, container, false);

        String[] postTexts = {"Great place! Must visit in summers.", "Chilling with college friends. Great weather.",
                "Love the chat at this place.", "On a shopping spree", "Save it for later.", "Awesome", "Bhaak! The worst place ever."};

        ListAdapter feedPostAdaptor = new FeedPostAdaptor(getActivity(),postTexts);


        ListView contentList = (ListView) v.findViewById(R.id.lvPosts);
        contentList.setAdapter(feedPostAdaptor);

        contentList.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String postText = String.valueOf(parent.getItemAtPosition(position));
                        Toast.makeText(getActivity(), postText, Toast.LENGTH_SHORT).show();
                    }
                }
        );


        return v;
    }

}
