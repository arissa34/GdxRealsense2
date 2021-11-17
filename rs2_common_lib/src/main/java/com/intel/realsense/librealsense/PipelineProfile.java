package com.intel.realsense.librealsense;

public class PipelineProfile extends LrsClass {

    PipelineProfile(long handle){
        mHandle = handle;
    }

    public Device getDevice() {
        return new Device(nGetDevice(mHandle));
    }

    @Override
    public void close() {
        nDelete(mHandle);
    }

    /*JNI
    #include <jni.h>
    #include "include/error.h"
    #include "include/librealsense2/rs.h"
    #include "include/librealsense2/h/rs_pipeline.h"
    #include "include/jni_logging.h"
    #include "include/frame_callback.h"
     */

    private static native void nDelete(long handle);/*
    rs2_delete_pipeline_profile(reinterpret_cast<rs2_pipeline_profile *>(handle));
    */
    private static native long nGetDevice(long handle);/*
    rs2_error* e = NULL;
    rs2_device *rv = rs2_pipeline_profile_get_device(reinterpret_cast<rs2_pipeline_profile *>(handle), &e);
    handle_error(env, e);
    return reinterpret_cast<jlong>(rv);
     */
}
