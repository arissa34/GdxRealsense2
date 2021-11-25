package com.intel.realsense.librealsense;

public class Align extends Filter {

    /*JNI
    #include <jni.h>
    #include <error.h>

    #include <librealsense2/rs.h>
     */

    public Align(StreamType alignTo){
        mHandle = nCreate(mQueue.getHandle(), alignTo.value());
    }

    private static native long nCreate(long queueHandle, int alignTo);/*
    rs2_error *e = NULL;
    rs2_processing_block *rv = rs2_create_align(static_cast<rs2_stream>(alignTo), &e);
    handle_error(env, e);
    rs2_start_processing_queue(rv, reinterpret_cast<rs2_frame_queue *>(queueHandle), &e);
    handle_error(env, e);
    return reinterpret_cast<jlong>(rv);
    */
}
