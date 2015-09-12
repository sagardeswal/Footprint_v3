package com.sacri.footprint_v3.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.sacri.footprint_v3.R;
import com.sacri.footprint_v3.activities.AddPlaceActivity;
import com.sacri.footprint_v3.activities.AddPostActivity;


public class MainMenuFragment extends Fragment {

    private ImageButton ibnHome;
    private ImageButton ibnPost;
    private ImageButton ibnPlace;
    private ImageButton ibnSettings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main_menu, container, false);

        ibnHome = (ImageButton) v.findViewById(R.id.ibnHome);
        ibnPost = (ImageButton) v.findViewById(R.id.ibnPost);
        ibnPlace = (ImageButton) v.findViewById(R.id.ibnPlace);
        ibnSettings = (ImageButton) v.findViewById(R.id.ibnSettings);

        ibnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddPlaceActivity.class);
                startActivity(intent);
            }
        });

        ibnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddPostActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

}
