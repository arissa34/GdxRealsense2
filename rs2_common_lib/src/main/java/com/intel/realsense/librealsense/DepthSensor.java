package com.intel.realsense.librealsense;

public class DepthSensor extends Sensor {

    /*JNI
    #include <jni.h>
    #include "include/error.h"

    #include "include/librealsense2/rs.h"
     */

    DepthSensor(long handle) {
        super(handle);
        mOwner = false;
    }

    public float getDepthScale()  { return nGetDepthScale(mHandle); }


    private static native float nGetDepthScale(long handle);/*
    rs2_error* e = nullptr;
    float depthScale = rs2_get_depth_scale(reinterpret_cast<rs2_sensor *>(handle), &e);
    handle_error(env, e);
    return depthScale;
    */
}

