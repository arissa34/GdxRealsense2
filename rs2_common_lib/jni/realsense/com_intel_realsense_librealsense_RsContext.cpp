#include <com_intel_realsense_librealsense_RsContext.h>

//@line:46

    #include "include/error.h"
    #include "include/librealsense2/rs.h"
    #include "include/librealsense2/hpp/rs_context.hpp"
     JNIEXPORT jlong JNICALL Java_com_intel_realsense_librealsense_RsContext_nCreate(JNIEnv* env, jclass clazz) {


//@line:52

    rs2_error* e = NULL;
    rs2_context* handle = rs2_create_context(RS2_API_VERSION, &e);
    handle_error(env, e);
    return (jlong) handle;
    

}

JNIEXPORT jstring JNICALL Java_com_intel_realsense_librealsense_RsContext_nGetVersion(JNIEnv* env, jclass clazz) {


//@line:58

    return env->NewStringUTF(RS2_API_VERSION_STR);
    

}

JNIEXPORT jlong JNICALL Java_com_intel_realsense_librealsense_RsContext_nQueryDevices(JNIEnv* env, jclass clazz, jlong handle, jint mask) {


//@line:61

    rs2_error* e = NULL;
    rs2_device_list* device_list_handle = rs2_query_devices_ex((rs2_context *) handle, mask, &e);
    handle_error(env, e);
    return (jlong) device_list_handle;
    

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_RsContext_nDelete(JNIEnv* env, jclass clazz, jlong handle) {


//@line:67

    rs2_delete_context((rs2_context *) handle);
    

}

