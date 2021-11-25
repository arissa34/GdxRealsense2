#include <com_intel_realsense_librealsense_FwLogParsedMsg.h>

//@line:22

    #include <jni.h>
    #include <error.h>
    #include <librealsense2/h/rs_internal.h>
     JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_FwLogParsedMsg_nRelease(JNIEnv* env, jclass clazz, jlong handle) {


//@line:28

    rs2_delete_fw_log_parsed_message(reinterpret_cast<rs2_firmware_log_parsed_message*>(handle));
    

}

JNIEXPORT jstring JNICALL Java_com_intel_realsense_librealsense_FwLogParsedMsg_nGetMessage(JNIEnv* env, jclass clazz, jlong handle) {


//@line:32

    rs2_error* e = NULL;
    const char* message = rs2_get_fw_log_parsed_message(reinterpret_cast<rs2_firmware_log_parsed_message*>(handle), &e);
    handle_error(env, e);

    return env->NewStringUTF(message);
     

}

JNIEXPORT jstring JNICALL Java_com_intel_realsense_librealsense_FwLogParsedMsg_nGetFileName(JNIEnv* env, jclass clazz, jlong handle) {


//@line:40

    rs2_error* e = NULL;
    const char* file_name = rs2_get_fw_log_parsed_file_name(reinterpret_cast<rs2_firmware_log_parsed_message*>(handle), &e);
    handle_error(env, e);

    return env->NewStringUTF(file_name);
     

}

JNIEXPORT jstring JNICALL Java_com_intel_realsense_librealsense_FwLogParsedMsg_nGetThreadName(JNIEnv* env, jclass clazz, jlong handle) {


//@line:48

    rs2_error* e = NULL;
    const char* thread_name = rs2_get_fw_log_parsed_thread_name(reinterpret_cast<rs2_firmware_log_parsed_message*>(handle), &e);
    handle_error(env, e);

    return env->NewStringUTF(thread_name);
     

}

JNIEXPORT jstring JNICALL Java_com_intel_realsense_librealsense_FwLogParsedMsg_nGetSeverity(JNIEnv* env, jclass clazz, jlong handle) {


//@line:56

    rs2_error* e = NULL;
    rs2_log_severity severity = rs2_get_fw_log_parsed_severity(reinterpret_cast<rs2_firmware_log_parsed_message*>(handle), &e);
    handle_error(env, e);

    const char* severity_string = rs2_log_severity_to_string(severity);
    return env->NewStringUTF(severity_string);
     

}

JNIEXPORT jint JNICALL Java_com_intel_realsense_librealsense_FwLogParsedMsg_nGetLine(JNIEnv* env, jclass clazz, jlong handle) {


//@line:65

    rs2_error* e = NULL;
    int line = rs2_get_fw_log_parsed_line(reinterpret_cast<rs2_firmware_log_parsed_message*>(handle), &e);
    handle_error(env, e);
    return (jint)line;
     

}

JNIEXPORT jlong JNICALL Java_com_intel_realsense_librealsense_FwLogParsedMsg_nGetTimestamp(JNIEnv* env, jclass clazz, jlong handle) {


//@line:72

    rs2_error* e = NULL;
    unsigned int timestamp = rs2_get_fw_log_parsed_timestamp(reinterpret_cast<rs2_firmware_log_parsed_message*>(handle), &e);
    handle_error(env, e);
    return (jlong)(unsigned long long)timestamp;
     

}

JNIEXPORT jint JNICALL Java_com_intel_realsense_librealsense_FwLogParsedMsg_nGetSequenceId(JNIEnv* env, jclass clazz, jlong handle) {


//@line:79

    rs2_error* e = NULL;
    unsigned int sequence = rs2_get_fw_log_parsed_sequence_id(reinterpret_cast<rs2_firmware_log_parsed_message*>(handle), &e);
    handle_error(env, e);
    return (jint)sequence;
     

}

