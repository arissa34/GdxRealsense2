#include <com_intel_realsense_librealsense_DeviceList.h>
#include <com_intel_realsense_librealsense_Device.h>

//@line:71

    #include <jni.h>
    #include <memory>
    #include <vector>
    #include <error.h>

    #include <librealsense2/rs.h>
    #include <librealsense2/hpp/rs_device.hpp>
    #include <librealsense2/rs_advanced_mode.h>
     JNIEXPORT jboolean JNICALL Java_com_intel_realsense_librealsense_Device_nSupportsInfo(JNIEnv* env, jclass clazz, jlong handle, jint info) {


//@line:82

    rs2_error *e = NULL;
    auto rv = rs2_supports_device_info(reinterpret_cast<const rs2_device *>(handle),
                                       static_cast<rs2_camera_info>(info), &e);
    handle_error(env, e);
    return rv > 0;
    

}

JNIEXPORT jstring JNICALL Java_com_intel_realsense_librealsense_Device_nGetInfo(JNIEnv* env, jclass clazz, jlong handle, jint info) {


//@line:89

    rs2_error *e = NULL;
    const char* rv = rs2_get_device_info(reinterpret_cast<const rs2_device *>(handle),
                                         static_cast<rs2_camera_info>(info), &e);
    handle_error(env, e);
    if (NULL == rv)
        rv = "";
    return env->NewStringUTF(rv);
     

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Device_nToggleAdvancedMode(JNIEnv* env, jclass clazz, jlong handle, jboolean enable) {


//@line:98

    rs2_error* e = NULL;
    rs2_toggle_advanced_mode(reinterpret_cast<rs2_device *>(handle), enable, &e);
    handle_error(env, e);
     

}

JNIEXPORT jboolean JNICALL Java_com_intel_realsense_librealsense_Device_nIsInAdvancedMode(JNIEnv* env, jclass clazz, jlong handle) {


//@line:103


     

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Device_nLoadPresetFromJson(JNIEnv* env, jclass clazz, jlong handle, jbyteArray obj_data) {
	char* data = (char*)env->GetPrimitiveArrayCritical(obj_data, 0);


//@line:106

     
	env->ReleasePrimitiveArrayCritical(obj_data, data, 0);

}

JNIEXPORT jbyteArray JNICALL Java_com_intel_realsense_librealsense_Device_nSerializePresetToJson(JNIEnv* env, jclass clazz, jlong handle) {


//@line:108

     

}

JNIEXPORT jlongArray JNICALL Java_com_intel_realsense_librealsense_Device_nQuerySensors(JNIEnv* env, jclass clazz, jlong handle) {


//@line:110

    rs2_error* e = nullptr;
    std::shared_ptr<rs2_sensor_list> list(
            rs2_query_sensors(reinterpret_cast<const rs2_device *>(handle), &e),
            rs2_delete_sensor_list);
    handle_error(env, e);

    auto size = rs2_get_sensors_count(list.get(), &e);
    handle_error(env, e);

    std::vector<rs2_sensor*> sensors;
    for (auto i = 0; i < size; i++)
    {
        auto s = rs2_create_sensor(list.get(), i, &e);
        handle_error(env, e);
        sensors.push_back(s);
    }
    jlongArray rv = env->NewLongArray(sensors.size());

    for (auto i = 0; i < sensors.size(); i++)
    {
        env->SetLongArrayRegion(rv, i, 1, reinterpret_cast<const jlong *>(&sensors[i]));
    }

    return rv;
     

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Device_nHardwareReset(JNIEnv* env, jclass clazz, jlong handle) {


//@line:136

    rs2_error *e = NULL;
    rs2_hardware_reset(reinterpret_cast<const rs2_device *>(handle), &e);
    handle_error(env, e);
     

}

JNIEXPORT jboolean JNICALL Java_com_intel_realsense_librealsense_Device_nIsDeviceExtendableTo(JNIEnv* env, jclass clazz, jlong handle, jint extension) {


//@line:141

     

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Device_nRelease(JNIEnv* env, jclass clazz, jlong handle) {


//@line:143

    rs2_delete_device(reinterpret_cast<rs2_device *>(handle));
     

}

