#include <com_intel_realsense_librealsense_DeviceList.h>

//@line:62

    #include <error.h>
    #include <librealsense2/rs.h>
     JNIEXPORT jint JNICALL Java_com_intel_realsense_librealsense_DeviceList_nGetDeviceCount(JNIEnv* env, jclass clazz, jlong handle) {


//@line:67

    rs2_error *e = NULL;
    auto rv = rs2_get_device_count(reinterpret_cast<const rs2_device_list *>(handle), &e);
    handle_error(env, e);
    return rv;
    

}

JNIEXPORT jlong JNICALL Java_com_intel_realsense_librealsense_DeviceList_nCreateDevice(JNIEnv* env, jclass clazz, jlong handle, jint index) {


//@line:74

    rs2_error *e = NULL;
    rs2_device* rv = rs2_create_device(reinterpret_cast<const rs2_device_list *>(handle), index, &e);
    handle_error(env, e);
    return (jlong)rv;
    

}

JNIEXPORT jboolean JNICALL Java_com_intel_realsense_librealsense_DeviceList_nContainsDevice(JNIEnv* env, jclass clazz, jlong handle, jlong deviceHandle) {


//@line:81

    rs2_error *e = NULL;
    auto rv = rs2_device_list_contains(reinterpret_cast<const rs2_device_list *>(handle),
                                       reinterpret_cast<const rs2_device *>(deviceHandle), &e);
    handle_error(env, e);
    return rv > 0;
    

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_DeviceList_nRelease(JNIEnv* env, jclass clazz, jlong handle) {


//@line:89

    rs2_delete_device_list((rs2_device_list *) handle);
    

}

