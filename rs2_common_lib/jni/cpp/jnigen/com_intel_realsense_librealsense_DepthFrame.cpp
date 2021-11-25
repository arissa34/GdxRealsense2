#include <com_intel_realsense_librealsense_DepthFrame.h>

//@line:17

    #include <jni.h>
    #include <error.h>
    #include <librealsense2/rs.h>
     JNIEXPORT jfloat JNICALL Java_com_intel_realsense_librealsense_DepthFrame_nGetDistance(JNIEnv* env, jclass clazz, jlong handle, jint x, jint y) {


//@line:23

    rs2_error *e = NULL;
    float rv = rs2_depth_frame_get_distance(reinterpret_cast<const rs2_frame *>(handle), x, y, &e);
    handle_error(env, e);
    return rv;
    

}

JNIEXPORT jfloat JNICALL Java_com_intel_realsense_librealsense_DepthFrame_nGetUnits(JNIEnv* env, jclass clazz, jlong handle) {


//@line:29

    rs2_error *e = NULL;
    float rv = rs2_depth_frame_get_units( reinterpret_cast<const rs2_frame *>(handle), &e );
    handle_error( env, e );
    return rv;
     

}

