#include <com_intel_realsense_librealsense_FwLogger.h>

//@line:56

    #include <jni.h>
    #include "include/error.h"
    #include "include/librealsense2/h/rs_internal.h"
     JNIEXPORT jlong JNICALL Java_com_intel_realsense_librealsense_FwLogger_nGetFwLog(JNIEnv* env, jobject object, jlong fw_logger_handle) {


//@line:62

    rs2_error* e = NULL;
    rs2_firmware_log_message* log_msg = rs2_create_fw_log_message(reinterpret_cast<rs2_device*>(fw_logger_handle), &e);
    handle_error(env, e);

    int result = rs2_get_fw_log(reinterpret_cast<rs2_device*>(fw_logger_handle), log_msg, &e);
    handle_error(env, e);

    bool result_bool = (result != 0);
    //jclass claxx = env->GetObjectClass(instance);
    //jfieldID resultField = env->GetFieldID(claxx, "mFwLogPullingStatus", "Z");
    //env->SetBooleanField(instance, resultField, result_bool);

    return (jlong)log_msg;
    

}

JNIEXPORT jlong JNICALL Java_com_intel_realsense_librealsense_FwLogger_nGetFlashLog(JNIEnv* env, jobject object, jlong fw_logger_handle) {


//@line:78

    rs2_error* e = NULL;
    rs2_firmware_log_message* log_msg = rs2_create_fw_log_message(reinterpret_cast<rs2_device*>(fw_logger_handle), &e);
    handle_error(env, e);

    int result = rs2_get_flash_log(reinterpret_cast<rs2_device*>(fw_logger_handle), log_msg, &e);
    handle_error(env, e);

    bool result_bool = (result != 0);
    //jclass claxx = env->GetObjectClass(instance);
    //jfieldID resultField = env->GetFieldID(claxx, "mFwLogPullingStatus", "Z");
    //env->SetBooleanField(instance, resultField, result_bool);

    return (jlong)log_msg;
     

}

JNIEXPORT jlong JNICALL Java_com_intel_realsense_librealsense_FwLogger_nGetNumberOfFwLogs(JNIEnv* env, jobject object, jlong fw_logger_handle) {


//@line:94

    rs2_error* e = NULL;
    unsigned int numOfFwLogsPolledFromDevice = rs2_get_number_of_fw_logs(reinterpret_cast<rs2_device*>(fw_logger_handle), &e);
    handle_error(env, e);

    return (jlong)(unsigned long long)numOfFwLogsPolledFromDevice;
     

}

static inline jboolean wrapped_Java_com_intel_realsense_librealsense_FwLogger_nInitParser
(JNIEnv* env, jclass clazz, jlong fw_logger_handle, jstring obj_xml_content_arr, char* xml_content_arr) {

//@line:102

    rs2_error* e = NULL;
    int result = rs2_init_fw_log_parser(reinterpret_cast<rs2_device*>(fw_logger_handle), xml_content_arr, &e);
    handle_error(env, e);

    bool resultBool = (result != 0);
    return (jboolean)resultBool;
     
}

JNIEXPORT jboolean JNICALL Java_com_intel_realsense_librealsense_FwLogger_nInitParser(JNIEnv* env, jclass clazz, jlong fw_logger_handle, jstring obj_xml_content_arr) {
	char* xml_content_arr = (char*)env->GetStringUTFChars(obj_xml_content_arr, 0);

	jboolean JNI_returnValue = wrapped_Java_com_intel_realsense_librealsense_FwLogger_nInitParser(env, clazz, fw_logger_handle, obj_xml_content_arr, xml_content_arr);

	env->ReleaseStringUTFChars(obj_xml_content_arr, xml_content_arr);

	return JNI_returnValue;
}

JNIEXPORT jlong JNICALL Java_com_intel_realsense_librealsense_FwLogger_nParseFwLog(JNIEnv* env, jclass clazz, jlong fw_logger_handle, jlong fw_log_msg_handle) {


//@line:111

    rs2_error* e = NULL;
    rs2_firmware_log_parsed_message* parsed_msg = rs2_create_fw_log_parsed_message(reinterpret_cast<rs2_device*>(fw_logger_handle), &e);
    handle_error(env, e);

    int result = rs2_parse_firmware_log(reinterpret_cast<rs2_device*>(fw_logger_handle),
                                   reinterpret_cast<rs2_firmware_log_message*>(fw_log_msg_handle),
                                        parsed_msg, &e);
    handle_error(env, e);

    return (jlong)parsed_msg;
     

}

