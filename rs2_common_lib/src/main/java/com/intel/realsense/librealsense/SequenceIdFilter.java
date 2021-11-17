package com.intel.realsense.librealsense;

public class SequenceIdFilter extends Filter {

    /*JNI
    #include <jni.h>
    #include "include/error.h"

    #include "include/librealsense2/rs.h"
     */

    public SequenceIdFilter(){
        mHandle = nCreate(mQueue.getHandle());
    }

    public SequenceIdFilter(int selectedId){
        mHandle = nCreate(mQueue.getHandle());
        setValue(Option.SEQUENCE_ID, (float)selectedId);
    }

    private static native long nCreate(long queueHandle);/*
    rs2_error *e = NULL;
    rs2_processing_block *rv = rs2_create_sequence_id_filter(&e);
    handle_error(env, e);
    rs2_start_processing_queue(rv, reinterpret_cast<rs2_frame_queue *>(queueHandle), &e);
    handle_error(env, e);
    return reinterpret_cast<jlong>(rv);
    */
}
