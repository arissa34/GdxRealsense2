#include <com_intel_realsense_librealsense_YuyDecoder.h>

//@line:6

    #include <jni.h>
    #include <error.h>

    #include <librealsense2/rs.h>
     JNIEXPORT jlong JNICALL Java_com_intel_realsense_librealsense_YuyDecoder_nCreate(JNIEnv* env, jclass clazz, jlong queueHandle) {


//@line:18

    rs2_error *e = NULL;
    rs2_processing_block *rv = rs2_create_yuy_decoder(&e);
    handle_error(env, e);
    rs2_start_processing_queue(rv, reinterpret_cast<rs2_frame_queue *>(queueHandle), &e);
    handle_error(env, e);
    return reinterpret_cast<jlong>(rv);
    

}

