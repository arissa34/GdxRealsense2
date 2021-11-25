#include <com_intel_realsense_librealsense_DepthSensor.h>

//@line:5

    #include <jni.h>
    #include <error.h>

    #include <librealsense2/rs.h>
     JNIEXPORT jfloat JNICALL Java_com_intel_realsense_librealsense_DepthSensor_nGetDepthScale(JNIEnv* env, jclass clazz, jlong handle) {


//@line:20

    rs2_error* e = nullptr;
    float depthScale = rs2_get_depth_scale(reinterpret_cast<rs2_sensor *>(handle), &e);
    handle_error(env, e);
    return depthScale;
    

}

