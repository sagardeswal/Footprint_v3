package com.sacri.footprint_v3.fragments;


import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.sacri.footprint_v3.R;
import com.sacri.footprint_v3.activities.CheckInActivity;
import com.sacri.footprint_v3.callback.AddStoryCallback;
import com.sacri.footprint_v3.dbaccess.ServerRequests;
import com.sacri.footprint_v3.entity.Photo;
import com.sacri.footprint_v3.entity.Story;
import com.sacri.footprint_v3.entity.UserDetails;
import com.sacri.footprint_v3.adaptor.UserLocalStore;

import java.io.File;

public class CheckinFragment extends Fragment {

    private static final String FOOTPRINT_LOGGER = "com.sacri.footprint_v3";
    private EditText etText;
    private ImageView ivPreview;
    private UserLocalStore userLocalStore;
    private UserDetails loggedUser;
    public CheckinFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_checkin, container, false);
        userLocalStore = new UserLocalStore(getActivity());
        loggedUser = userLocalStore.getLoggedInUser();
        etText = (EditText) v.findViewById(R.id.etText);
        ImageButton ibnTakePic = (ImageButton) v.findViewById(R.id.ibnTakePic);
        Button bnSave = (Button) v.findViewById(R.id.bnSave);
        ivPreview = (ImageView) v.findViewById(R.id.ivPreview);
        ivPreview.setVisibility(View.GONE);
        //Disable Button if user has no camera
        if(!hasCamera(getActivity())){
            ibnTakePic.setEnabled(false);
        }

        ibnTakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(FOOTPRINT_LOGGER, "ibnTakePic clicked");
                startCamera();
            }
        });

        bnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Story newStory = new Story();
                if(((CheckInActivity)getActivity()).getPhotoFile()!=null) {
                    Photo storyPhoto = new Photo(((CheckInActivity) getActivity()).getPhotoFile());
                    newStory.getPhotos().put(loggedUser.getUserID(),storyPhoto);
                }
                if(etText!=null && etText.getText()!=null && !etText.getText().toString().isEmpty()) {
                    newStory.setText(etText.getText().toString());
                    newStory.setUserID(loggedUser.getUserID());
                    newStory.setPlaceID(((CheckInActivity) getActivity()).getPlaceID());
                    newStory.setLocID(((CheckInActivity) getActivity()).getLocID());
                    newStory.setEventID(((CheckInActivity)getActivity()).getEventID());
                    storeCheckIn(newStory);
                }
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        File photoFile = ((CheckInActivity)getActivity()).getPhotoFile();
        if(photoFile!=null) {
            Log.i(FOOTPRINT_LOGGER,"onResume photoFile not null");
            String filePath = photoFile.getPath();
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            ivPreview.setVisibility(View.VISIBLE);
            ivPreview.setImageBitmap(bitmap);
        }
    }


    /////////////////////////////////CAMERA METHODS START///////////////////////////////////////////

    private boolean hasCamera(Context context){
        Log.i(FOOTPRINT_LOGGER, "hasCamera()");
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    public void startCamera() {
        Log.i(FOOTPRINT_LOGGER, "startCamera()");
        Fragment cameraFragment = new CameraFragment();
        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.fragmentContainer, cameraFragment);
        transaction.addToBackStack("CheckInActivity");
        transaction.commit();
    }

    /////////////////////////////////CAMERA METHODS END///////////////////////////////////////////

    private void storeCheckIn(Story newStory){
        Log.i(FOOTPRINT_LOGGER, "storeCheckIn()");

        ServerRequests serverRequests = new ServerRequests(getActivity());
        serverRequests.addStoryDataInBackground(newStory, new AddStoryCallback() {
            @Override
            public void done() {
                Toast.makeText(getActivity(), "Story Added Successfully", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });
    }

}
