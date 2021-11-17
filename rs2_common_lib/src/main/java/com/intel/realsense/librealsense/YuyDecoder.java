package com.intel.realsense.librealsense;

// convert YUYV from color stream into RGB8 format
public class YuyDecoder extends Filter {

    /*JNI
    #include <jni.h>
    #include "include/error.h"

    #include "include/librealsense2/rs.h"
     */

    public YuyDecoder(){
        mHandle = nCreate(mQueue.getHandle());
        setValue(Option.STREAM_FILTER, StreamType.COLOR.value());
    }

    private static native long nCreate(long queueHandle);/*
    rs2_error *e = NULL;
    rs2_processing_block *rv = rs2_create_yuy_decoder(&e);
    handle_error(env, e);
    rs2_start_processing_queue(rv, reinterpret_cast<rs2_frame_queue *>(queueHandle), &e);
    handle_error(env, e);
    return reinterpret_cast<jlong>(rv);
    */
}
