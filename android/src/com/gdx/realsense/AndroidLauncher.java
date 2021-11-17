package com.gdx.realsense;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.intel.realsense.librealsense.DeviceListener;
import com.intel.realsense.librealsense.DeviceWatcherAndroid;
import com.intel.realsense.librealsense.RsContext;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AndroidLauncher extends AndroidApplication {

    MyGdxRs2 myGdxRs2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.numSamples = 4;
        config.hideStatusBar = true;
        config.useImmersiveMode = true;

        initialize(myGdxRs2 = new MyGdxRs2(), config);

        Dexter.withContext(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        RsContext.init(new DeviceWatcherAndroid(getApplicationContext()));
                        myGdxRs2.getRsContext().setDevicesChangedCallback(myGdxRs2.getRsManager());
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();
    }

    @Override
    protected void onStop() {
        myGdxRs2.onExit();
        super.onStop();
    }
}
