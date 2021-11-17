#include <com_intel_realsense_librealsense_FwLogMsg.h>

//@line:33

    #include <jni.h>
    #include "include/error.h"
    #include "include/librealsense2/h/rs_internal.h"
     JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_FwLogMsg_nRelease(JNIEnv* env, jclass clazz, jlong handle) {


//@line:39

    rs2_delete_fw_log_message(reinterpret_cast<rs2_firmware_log_message*>(handle));
    

}

JNIEXPORT jint JNICALL Java_com_intel_realsense_librealsense_FwLogMsg_nGetSeverity(JNIEnv* env, jclass clazz, jlong handle) {


//@line:43

    rs2_error* e = NULL;
    rs2_log_severity severity = rs2_fw_log_message_severity(reinterpret_cast<rs2_firmware_log_message*>(handle), &e);
    handle_error(env, e);
    return (jint)severity;
     

}

JNIEXPORT jstring JNICALL Java_com_intel_realsense_librealsense_FwLogMsg_nGetSeverityStr(JNIEnv* env, jclass clazz, jlong handle) {


//@line:50

    rs2_error* e = NULL;
    rs2_log_severity severity = rs2_fw_log_message_severity(reinterpret_cast<rs2_firmware_log_message*>(handle), &e);
    handle_error(env, e);

    const char* severity_string = rs2_log_severity_to_string(severity);
    return env->NewStringUTF(severity_string);
     

}

JNIEXPORT jlong JNICALL Java_com_intel_realsense_librealsense_FwLogMsg_nGetTimestamp(JNIEnv* env, jclass clazz, jlong handle) {


//@line:59

    rs2_error* e = NULL;
    unsigned int timestamp = rs2_fw_log_message_timestamp(reinterpret_cast<rs2_firmware_log_message*>(handle), &e);
    handle_error(env, e);
    return (jlong)(unsigned long long)timestamp;
     

}

static inline jbyteArray wrapped_Java_com_intel_realsense_librealsense_FwLogMsg_nGetData
(JNIEnv* env, jclass clazz, jlong handle, jbyteArray obj_input_buffer, char* input_buffer) {

//@line:66

    rs2_error* e = NULL;
    int size = rs2_fw_log_message_size(reinterpret_cast<rs2_firmware_log_message*>(handle), &e);
    handle_error(env, e);

    const unsigned char* message_data = rs2_fw_log_message_data(reinterpret_cast<rs2_firmware_log_message*>(handle), &e);
    handle_error(env, e);

    jbyteArray rv = env->NewByteArray(size);
    env->SetByteArrayRegion (rv, 0, size, reinterpret_cast<const jbyte *>(message_data));
    return rv;
     
}

JNIEXPORT jbyteArray JNICALL Java_com_intel_realsense_librealsense_FwLogMsg_nGetData(JNIEnv* env, jclass clazz, jlong handle, jbyteArray obj_input_buffer) {
	char* input_buffer = (char*)env->GetPrimitiveArrayCritical(obj_input_buffer, 0);

	jbyteArray JNI_returnValue = wrapped_Java_com_intel_realsense_librealsense_FwLogMsg_nGetData(env, clazz, handle, obj_input_buffer, input_buffer);

	env->ReleasePrimitiveArrayCritical(obj_input_buffer, input_buffer, 0);

	return JNI_returnValue;
}

JNIEXPORT jint JNICALL Java_com_intel_realsense_librealsense_FwLogMsg_nGetSize(JNIEnv* env, jclass clazz, jlong handle) {


//@line:79

    rs2_error* e = NULL;
    int size = rs2_fw_log_message_size(reinterpret_cast<rs2_firmware_log_message*>(handle), &e);
    handle_error(env, e);
    return (jint)size;
     

}

