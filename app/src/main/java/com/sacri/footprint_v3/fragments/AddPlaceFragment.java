package com.sacri.footprint_v3.fragments;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.sacri.footprint_v3.R;
import com.sacri.footprint_v3.activities.AddPlaceActivity;
import com.sacri.footprint_v3.entity.PlaceDetails;

import java.io.File;


public class AddPlaceFragment extends Fragment {

    private static final String FOOTPRINT_LOGGER = "com.sacri.footprint_v3";
    private EditText etTitle;
    private EditText etDescription;
    private EditText etLocation;
    private Spinner spCategory;
    private ImageButton ibnTakePic;
    private Button bnSave;
    private Button bnCancel;
    private ImageView ivPreview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle SavedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_place, parent, false);

        etTitle = (EditText) v.findViewById(R.id.etTitle);
        etDescription = (EditText) v.findViewById(R.id.etDescription);
        etLocation = (EditText) v.findViewById(R.id.etLocation);
        spCategory = (Spinner) v.findViewById(R.id.spCategory);
        ibnTakePic = (ImageButton) v.findViewById(R.id.ibnTakePic);
        bnSave = (Button) v.findViewById(R.id.bnSave);
        bnCancel = (Button) v.findViewById(R.id.bnCancel);
        ivPreview = (ImageView) v.findViewById(R.id.ivPreview);

        //Disable Button if user has no camera
        if(!hasCamera(getActivity())){
            ibnTakePic.setEnabled(false);
        }

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.category_array,
                        android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(spinnerAdapter);

        ibnTakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(FOOTPRINT_LOGGER,"ibnTakePic clicked");
                InputMethodManager imm = (InputMethodManager) getActivity()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etTitle.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(etDescription.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(etLocation.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(spCategory.getWindowToken(), 0);
                startCamera();
            }
        });

        bnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(FOOTPRINT_LOGGER,"bnSave clicked");
                PlaceDetails placeDetails = ((AddPlaceActivity)getActivity()).getCurrentPlaceDetails();
                placeDetails.setTitle(etTitle.getText().toString());
                placeDetails.setDescription(etDescription.getText().toString());
                placeDetails.setCategory(spCategory.getSelectedItem().toString());
                placeDetails.setLocation(etLocation.getText().toString());

                Toast.makeText(getActivity(),placeDetails.getTitle() +  " Added", Toast.LENGTH_SHORT).show();
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        });

        bnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i(FOOTPRINT_LOGGER,"bnCancel clicked");
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        });
        return v;
    }

    private boolean hasCamera(Context context){
        Log.i(FOOTPRINT_LOGGER,"hasCamera()");
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    public void startCamera() {
        Log.i(FOOTPRINT_LOGGER,"startCamera()");
        Fragment cameraFragment = new CameraFragment();
        FragmentTransaction transaction = getActivity().getFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.fragmentContainer, cameraFragment);
        transaction.addToBackStack("AddPlaceFragment");
        transaction.commit();
    }


    @Override
    public void onResume() {
        super.onResume();
        File photoFile = ((AddPlaceActivity)getActivity()).getCurrentPlaceDetails().getPhotoFile();
        if(photoFile!=null) {
            Log.i(FOOTPRINT_LOGGER,"onResume photoFile not null");
            String filePath = photoFile.getPath();
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            ivPreview.setVisibility(View.VISIBLE);
            ivPreview.setImageBitmap(bitmap);
        }
    }
}
