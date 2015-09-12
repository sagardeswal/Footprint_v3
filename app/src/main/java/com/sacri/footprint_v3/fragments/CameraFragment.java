package com.sacri.footprint_v3.fragments;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.Fragment;
import android.app.FragmentManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.sacri.footprint_v3.R;
import com.sacri.footprint_v3.activities.AddPlaceActivity;


public class CameraFragment extends Fragment {

    private static final String FOOTPRINT_LOGGER = "com.sacri.footprint_v3";
	private Camera camera;
	private SurfaceView surfaceView;
	private File photoFile;
	private ImageButton photoButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_camera, parent, false);

		photoButton = (ImageButton) v.findViewById(R.id.camera_photo_button);

		if (camera == null) {
			try {
				camera = Camera.open();
				photoButton.setEnabled(true);
			} catch (Exception e) {
				Log.e(FOOTPRINT_LOGGER, "No camera with exception: " + e.getMessage());
				photoButton.setEnabled(false);
				Toast.makeText(getActivity(), "No camera detected",
						Toast.LENGTH_LONG).show();
			}
		}

		surfaceView = (SurfaceView) v.findViewById(R.id.camera_surface_view);
		SurfaceHolder holder = surfaceView.getHolder();
		holder.addCallback(new Callback() {

			public void surfaceCreated(SurfaceHolder holder) {
                Log.i(FOOTPRINT_LOGGER,"surfaceCreated()");
				try {
					if (camera != null) {
						camera.setDisplayOrientation(90);
						camera.setPreviewDisplay(holder);
						camera.startPreview();
					}
				} catch (IOException e) {
					Log.e(FOOTPRINT_LOGGER, "Error setting up preview", e);
				}
			}

			public void surfaceChanged(SurfaceHolder holder, int format,
									   int width, int height) {
                Log.i(FOOTPRINT_LOGGER,"surfaceChanged()");
				// nothing to do here
			}

			public void surfaceDestroyed(SurfaceHolder holder) {
                Log.i(FOOTPRINT_LOGGER,"surfaceDestroyed()");
				// nothing here
			}

		});

		photoButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
                Log.i(FOOTPRINT_LOGGER, "photoButton clicked");
				if (camera == null){
                    Log.i(FOOTPRINT_LOGGER,"camera is null");
                    return;
                }

				camera.takePicture(new Camera.ShutterCallback() {

                    @Override
                    public void onShutter() {
                        // nothing to do
                        Log.i(FOOTPRINT_LOGGER, "onShutter()");
                    }

                }, null, new Camera.PictureCallback() {

                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {
                        Log.i(FOOTPRINT_LOGGER, "onPictureTaken()");

                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                        photoFile = new File((getActivity()).getFilesDir(), "IMG_" + timeStamp + ".jpg");
                        if (photoFile == null) {
                            Log.i(FOOTPRINT_LOGGER, "Error creating media file, check storage permissions: ");
                            return;
                        }

                        try {
                            FileOutputStream fos = new FileOutputStream(photoFile);
                            fos.write(data);
                            fos.close();
                            addPhotoFileToPlaceDetailsAndReturn();
                        } catch (FileNotFoundException e) {
                            Log.i(FOOTPRINT_LOGGER, "File not found: " + e.getMessage());
                        } catch (IOException e) {
                            Log.i(FOOTPRINT_LOGGER, "Error accessing file: " + e.getMessage());
						}
					}

				});
			}
		});

		return v;
	}

	private void addPhotoFileToPlaceDetailsAndReturn(){
        Log.i(FOOTPRINT_LOGGER,"addPhotoFileToPlaceDetailsAndReturn()");
		((AddPlaceActivity)getActivity()).getCurrentPlaceDetails().setPhotoFile(photoFile);
		FragmentManager fm = getActivity().getFragmentManager();
		fm.popBackStack("AddPlaceFragment",
				FragmentManager.POP_BACK_STACK_INCLUSIVE);
	}

    @Override
    public void onResume() {
        Log.i(FOOTPRINT_LOGGER,"onResume()");
        super.onResume();
        if (camera == null) {
            try {
                Log.i(FOOTPRINT_LOGGER,"onResume camera is null");
                camera = Camera.open();
                photoButton.setEnabled(true);
            } catch (Exception e) {
                Log.i(FOOTPRINT_LOGGER, "No camera: " + e.getMessage());
                photoButton.setEnabled(false);
                Toast.makeText(getActivity(), "No camera detected",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onPause() {
        if (camera != null) {
            Log.i(FOOTPRINT_LOGGER,"onPause camera is not null");
            camera.stopPreview();
            camera.release();
        }
        super.onPause();
    }

}
