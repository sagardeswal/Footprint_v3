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
import com.sacri.footprint_v3.utils.FeedPlaceAdaptor;

public class FeedPlaceFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String[] places = {"Qutab Minar", "India Gate", "Lotus Temple", "Connaught Place", "Chandni Chawk", "Hauz Khas Village", "Akshardham"};

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_place_feed, container, false);
        ListAdapter feedPlaceAdaptor = new FeedPlaceAdaptor(getActivity(),places);


        ListView contentList = (ListView) v.findViewById(R.id.lvPlaces);
        contentList.setAdapter(feedPlaceAdaptor);

        contentList.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String placeTitle = String.valueOf(parent.getItemAtPosition(position));
                        Toast.makeText(getActivity(), placeTitle, Toast.LENGTH_LONG).show();
                    }
                }
        );


        return v;
    }

}
