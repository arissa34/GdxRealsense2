package com.intel.realsense.librealsense;

public class RoiSensor extends Sensor {

    RoiSensor(long handle) {
        super(handle);
        mOwner = false;
    }

    public void setRegionOfInterest(RegionOfInterest roi) throws Exception{
        nSetRegionOfInterest(mHandle, roi.minX, roi.minY, roi.maxX, roi.maxY);
    }

    public void setRegionOfInterest(int minX, int minY, int maxX, int maxY) throws Exception{
        nSetRegionOfInterest(mHandle, minX, minY, maxX, maxY);
    }

    public RegionOfInterest getRegionOfInterest() throws Exception {
        RegionOfInterest rv = new RegionOfInterest();
        nGetRegionOfInterest(mHandle, rv);
        return rv;
    }

    /*JNI
    #include <jni.h>
    #include "include/error.h"

    #include "include/librealsense2/rs.h"
     */

    private static native void nSetRegionOfInterest(long handle, int minX, int minY, int maxX, int maxY);/*
    rs2_error* e = nullptr;
    rs2_set_region_of_interest(reinterpret_cast<rs2_sensor *>(handle), minX, minY, maxX, maxY, &e);
    handle_error(env, e);
    */
    private static native void nGetRegionOfInterest(long handle, RegionOfInterest roi);/*
    int min_x, min_y, max_x, max_y;
    rs2_error *e = nullptr;
    rs2_get_region_of_interest(reinterpret_cast<rs2_sensor *>(handle), &min_x, &min_y, &max_x, &max_y, &e);
    handle_error(env, e);

    if(e)
        return;

    jclass claxx = env->GetObjectClass(roi);

    jfieldID min_x_field = env->GetFieldID(claxx, "minX", "I");
    jfieldID min_y_field = env->GetFieldID(claxx, "minY", "I");
    jfieldID max_x_field = env->GetFieldID(claxx, "maxX", "I");
    jfieldID max_y_field = env->GetFieldID(claxx, "maxY", "I");

    env->SetIntField(roi, min_x_field, min_x);
    env->SetIntField(roi, min_y_field, min_y);
    env->SetIntField(roi, max_x_field, max_x);
    env->SetIntField(roi, max_y_field, max_y);
    */
}
