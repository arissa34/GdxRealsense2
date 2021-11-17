#include <com_intel_realsense_librealsense_Config.h>

//@line:55

    #include <jni.h>
    #include "include/error.h"

    #include "include/librealsense2/rs.h"
    #include "include/librealsense2/h/rs_config.h"
     JNIEXPORT jlong JNICALL Java_com_intel_realsense_librealsense_Config_nCreate(JNIEnv* env, jclass clazz) {


//@line:68

    rs2_error *e = NULL;
    rs2_config *rv = rs2_create_config(&e);
    handle_error(env, e);
    return (jlong) rv;
    

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Config_nDelete(JNIEnv* env, jclass clazz, jlong handle) {


//@line:74

    rs2_delete_config((rs2_config *) handle);
     

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Config_nEnableStream(JNIEnv* env, jclass clazz, jlong handle, jint type, jint index, jint width, jint height, jint format, jint framerate) {


//@line:77

    rs2_error *e = NULL;
    rs2_config_enable_stream(reinterpret_cast<rs2_config *>(handle), static_cast<rs2_stream>(type), index, width, height,
                             static_cast<rs2_format>(format), framerate, &e);
    handle_error(env, e);
     

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Config_nDisableStream(JNIEnv* env, jclass clazz, jlong handle, jint streamType) {


//@line:83

    rs2_error *e = NULL;
    rs2_config_disable_stream(reinterpret_cast<rs2_config *>(handle),
                              static_cast<rs2_stream>(streamType), &e);
    handle_error(env, e);
     

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Config_nEnableAllStreams(JNIEnv* env, jclass clazz, jlong handle) {


//@line:89

    rs2_error *e = NULL;
    rs2_config_enable_all_stream(reinterpret_cast<rs2_config *>(handle), &e);
    handle_error(env, e);
     

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Config_nDisableAllStreams(JNIEnv* env, jclass clazz, jlong handle) {


//@line:94

    rs2_error *e = NULL;
    rs2_config_disable_all_streams(reinterpret_cast<rs2_config *>(handle), &e);
    handle_error(env, e);
     

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Config_nEnableDeviceFromFile(JNIEnv* env, jclass clazz, jlong handle, jstring obj_filePath) {
	char* filePath = (char*)env->GetStringUTFChars(obj_filePath, 0);


//@line:99

    rs2_error *e = NULL;
    rs2_config_enable_device_from_file(reinterpret_cast<rs2_config *>(handle), filePath, &e);
    handle_error(env, e);
     
	env->ReleaseStringUTFChars(obj_filePath, filePath);

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Config_nEnableDevice(JNIEnv* env, jclass clazz, jlong handle, jstring obj_serial) {
	char* serial = (char*)env->GetStringUTFChars(obj_serial, 0);


//@line:104

    rs2_error *e = NULL;
    rs2_config_enable_device(reinterpret_cast<rs2_config *>(handle), serial, &e);
    handle_error(env, e);
     
	env->ReleaseStringUTFChars(obj_serial, serial);

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Config_nEnableRecordToFile(JNIEnv* env, jclass clazz, jlong handle, jstring obj_filePath) {
	char* filePath = (char*)env->GetStringUTFChars(obj_filePath, 0);


//@line:109

    rs2_error *e = NULL;
    rs2_config_enable_record_to_file(reinterpret_cast<rs2_config *>(handle), filePath, &e);
    handle_error(env, e);
     
	env->ReleaseStringUTFChars(obj_filePath, filePath);

}

JNIEXPORT jboolean JNICALL Java_com_intel_realsense_librealsense_Config_nCanResolve(JNIEnv* env, jclass clazz, jlong handle, jlong pipelineHandle) {


//@line:114

    rs2_error *e = NULL;
    auto rv = rs2_config_can_resolve(reinterpret_cast<rs2_config *>(handle),
                                     reinterpret_cast<rs2_pipeline *>(pipelineHandle), &e);
    handle_error(env, e);
    return rv > 0;
     

}

JNIEXPORT jlong JNICALL Java_com_intel_realsense_librealsense_Config_nResolve(JNIEnv* env, jclass clazz, jlong handle, jlong pipelineHandle) {


//@line:121

    rs2_error *e = NULL;
    rs2_pipeline_profile* rv = rs2_config_resolve(reinterpret_cast<rs2_config *>(handle),
                                                  reinterpret_cast<rs2_pipeline *>(pipelineHandle), &e);
    handle_error(env, e);
    return reinterpret_cast<jlong>(rv);
     

}

