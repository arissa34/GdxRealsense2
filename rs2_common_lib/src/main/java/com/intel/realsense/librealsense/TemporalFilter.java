package com.intel.realsense.librealsense;

public class TemporalFilter extends Filter {

    /*JNI
    #include <jni.h>
    #include <error.h>

    #include <librealsense2/rs.h>
     */

    public TemporalFilter(){
        mHandle = nCreate(mQueue.getHandle());
    }

    private static native long nCreate(long queueHandle);/*
    rs2_error *e = NULL;
    rs2_processing_block *rv = rs2_create_temporal_filter_block(&e);
    handle_error(env, e);
    rs2_start_processing_queue(rv, reinterpret_cast<rs2_frame_queue *>(queueHandle), &e);
    handle_error(env, e);
    return reinterpret_cast<jlong>(rv);
    */
}