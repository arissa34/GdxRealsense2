#include <com_intel_realsense_librealsense_SequenceIdFilter.h>

//@line:5

    #include <jni.h>
    #include "include/error.h"

    #include "include/librealsense2/rs.h"
     JNIEXPORT jlong JNICALL Java_com_intel_realsense_librealsense_SequenceIdFilter_nCreate(JNIEnv* env, jclass clazz, jlong queueHandle) {


//@line:21

    rs2_error *e = NULL;
    rs2_processing_block *rv = rs2_create_sequence_id_filter(&e);
    handle_error(env, e);
    rs2_start_processing_queue(rv, reinterpret_cast<rs2_frame_queue *>(queueHandle), &e);
    handle_error(env, e);
    return reinterpret_cast<jlong>(rv);
    

}

