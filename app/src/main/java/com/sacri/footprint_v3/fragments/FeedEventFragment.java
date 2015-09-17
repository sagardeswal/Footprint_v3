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
import com.sacri.footprint_v3.entity.EventDetails;
import com.sacri.footprint_v3.utils.FeedEventAdaptor;

import java.util.ArrayList;


public class FeedEventFragment extends Fragment {
    private static final String FOOTPRINT_LOGGER = "com.sacri.footprint_v3";
    private ListAdapter feedEventAdaptor;
    private View v;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_feed_event, container, false);

        ArrayList<EventDetails> eventDetailsArrayList =((FeedActivity) getActivity()).getMEventDetailsArrayList();

        if (eventDetailsArrayList == null) {
            Log.i(FOOTPRINT_LOGGER, "eventDetailsArrayList is null");
            showErrorMessage();

        } else {
            Log.i(FOOTPRINT_LOGGER, "eventDetailsArrayList: " + eventDetailsArrayList.get(0).getEventTitle());
            setListAdaptor(eventDetailsArrayList);
        }


        return v;
    }


    private void setListAdaptor(ArrayList<EventDetails> eventDetailsArrayList ){
        feedEventAdaptor = new FeedEventAdaptor(getActivity(), eventDetailsArrayList);
        ListView contentList = (ListView) v.findViewById(R.id.lvEvents);
        contentList.setAdapter(feedEventAdaptor);

        contentList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String eventTitle = String.valueOf(parent.getItemAtPosition(position));
                        Toast.makeText(getActivity(), eventTitle, Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    private void showErrorMessage(){
        Log.i(FOOTPRINT_LOGGER, "showErrorMessage()");
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setMessage("No Events Found");
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();
    }
}
