package com.intel.realsense.librealsense;


public class Pointcloud extends Filter {

    /*JNI
    #include <jni.h>
    #include "include/error.h"

    #include "include/librealsense2/rs.h"
     */

    public Pointcloud(){
        mHandle = nCreate(mQueue.getHandle());
    }

    public Pointcloud(StreamType texture){
        mHandle = nCreate(mQueue.getHandle());
        setValue(Option.STREAM_FILTER, texture.value());
    }

    public Pointcloud(StreamType texture, int index){
        mHandle = nCreate(mQueue.getHandle());
        setValue(Option.STREAM_FILTER, texture.value());
        setValue(Option.STREAM_INDEX_FILTER, index);
    }

    private static native long nCreate(long queueHandle);/*
    rs2_error *e = NULL;
    rs2_processing_block *rv = rs2_create_pointcloud(&e);
    handle_error(env, e);
    rs2_start_processing_queue(rv, reinterpret_cast<rs2_frame_queue *>(queueHandle), &e);
    handle_error(env, e);
    return reinterpret_cast<jlong>(rv);
    */
}
