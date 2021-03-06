#include <com_intel_realsense_librealsense_FrameQueue.h>

//@line:57

    #include <jni.h>
    #include <error.h>
    #include <librealsense2/rs.h>
     JNIEXPORT jlong JNICALL Java_com_intel_realsense_librealsense_FrameQueue_nCreate(JNIEnv* env, jclass clazz, jint capacity) {


//@line:63

    rs2_error *e = NULL;
    rs2_frame_queue *rv = rs2_create_frame_queue(capacity, &e);
    handle_error(env, e);
    return (jlong) rv;
    

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_FrameQueue_nDelete(JNIEnv* env, jclass clazz, jlong handle) {


//@line:69

    rs2_delete_frame_queue((rs2_frame_queue *) handle);
     

}

JNIEXPORT jlong JNICALL Java_com_intel_realsense_librealsense_FrameQueue_nPollForFrame(JNIEnv* env, jclass clazz, jlong handle) {


//@line:72

    rs2_frame *output_frame = NULL;
    rs2_error *e = NULL;
    int rv = rs2_poll_for_frame((rs2_frame_queue *) handle, &output_frame, &e);
    handle_error(env, e);
    return (jlong) (rv ? output_frame : 0);
     

}

JNIEXPORT jlong JNICALL Java_com_intel_realsense_librealsense_FrameQueue_nWaitForFrames(JNIEnv* env, jclass clazz, jlong handle, jint timeout) {


//@line:79

    rs2_error *e = NULL;
    rs2_frame *rv = rs2_wait_for_frame((rs2_frame_queue *) handle, (unsigned int) timeout, &e);
    handle_error(env, e);
    return (jlong) rv;
     

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_FrameQueue_nEnqueue(JNIEnv* env, jclass clazz, jlong handle, jlong frameHandle) {


//@line:85

    rs2_error *e = NULL;
    rs2_frame_add_ref((rs2_frame *) frameHandle, &e);
    handle_error(env, e);
    rs2_enqueue_frame((rs2_frame *) frameHandle, (void *) handle);
     

}

