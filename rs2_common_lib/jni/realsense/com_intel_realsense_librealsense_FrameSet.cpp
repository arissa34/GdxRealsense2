#include <com_intel_realsense_librealsense_FrameSet.h>

//@line:58

    #include <jni.h>
    #include "include/error.h"
    #include "include/librealsense2/rs.h"
     JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_FrameSet_nAddRef(JNIEnv* env, jclass clazz, jlong handle) {


//@line:64

    rs2_error *e = NULL;
    rs2_frame_add_ref((rs2_frame *) handle, &e);
    handle_error(env, e);
    

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_FrameSet_nRelease(JNIEnv* env, jclass clazz, jlong handle) {


//@line:69

    rs2_release_frame(reinterpret_cast<rs2_frame *>(handle));
     

}

JNIEXPORT jlong JNICALL Java_com_intel_realsense_librealsense_FrameSet_nExtractFrame(JNIEnv* env, jclass clazz, jlong handle, jint index) {


//@line:72

    rs2_error *e = NULL;
    rs2_frame *rv = rs2_extract_frame(reinterpret_cast<rs2_frame *>(handle), index, &e);
    handle_error(env, e);
    return reinterpret_cast<jlong>(rv);
     

}

JNIEXPORT jint JNICALL Java_com_intel_realsense_librealsense_FrameSet_nFrameCount(JNIEnv* env, jclass clazz, jlong handle) {


//@line:78

    rs2_error *e = NULL;
    int rv = rs2_embedded_frames_count(reinterpret_cast<rs2_frame *>(handle), &e);
    handle_error(env, e);
    return rv;
     

}

