package com.intel.realsense.librealsense;

public class DisparityTransformFilter extends Filter {

    /*JNI
    #include <jni.h>
    #include "include/error.h"

    #include "include/librealsense2/rs.h"
     */

    public DisparityTransformFilter(boolean transformToDisparity){
        mHandle = nCreate(mQueue.getHandle(), transformToDisparity);
    }

    private static native long nCreate(long queueHandle, boolean transformToDisparity);/*
    rs2_error *e = NULL;
    rs2_processing_block *rv = rs2_create_disparity_transform_block(transformToDisparity, &e);
    handle_error(env, e);
    rs2_start_processing_queue(rv, reinterpret_cast<rs2_frame_queue *>(queueHandle), &e);
    handle_error(env, e);
    return reinterpret_cast<jlong>(rv);
    */
}