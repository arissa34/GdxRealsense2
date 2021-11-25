#include <com_intel_realsense_librealsense_VideoFrame.h>

//@line:42

    #include <jni.h>
    #include <error.h>
    #include <librealsense2/rs.h>
     JNIEXPORT jint JNICALL Java_com_intel_realsense_librealsense_VideoFrame_nGetWidth(JNIEnv* env, jclass clazz, jlong handle) {


//@line:48

    rs2_error *e = NULL;
    int rv = rs2_get_frame_width(reinterpret_cast<const rs2_frame *>(handle), &e);
    handle_error(env, e);
    return rv;
    

}

JNIEXPORT jint JNICALL Java_com_intel_realsense_librealsense_VideoFrame_nGetHeight(JNIEnv* env, jclass clazz, jlong handle) {


//@line:54

    rs2_error *e = NULL;
    int rv = rs2_get_frame_height(reinterpret_cast<const rs2_frame *>(handle), &e);
    handle_error(env, e);
    return rv;
     

}

JNIEXPORT jint JNICALL Java_com_intel_realsense_librealsense_VideoFrame_nGetStride(JNIEnv* env, jclass clazz, jlong handle) {


//@line:60

    rs2_error *e = NULL;
    int rv = rs2_get_frame_stride_in_bytes(reinterpret_cast<const rs2_frame *>(handle), &e);
    handle_error(env, e);
    return rv;
     

}

JNIEXPORT jint JNICALL Java_com_intel_realsense_librealsense_VideoFrame_nGetBitsPerPixel(JNIEnv* env, jclass clazz, jlong handle) {


//@line:66

    rs2_error *e = NULL;
    int rv = rs2_get_frame_bits_per_pixel(reinterpret_cast<const rs2_frame *>(handle), &e);
    handle_error(env, e);
    return rv;
     

}

