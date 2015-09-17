package com.sacri.footprint_v3.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class FetchPlacesService extends Service {
    public FetchPlacesService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
