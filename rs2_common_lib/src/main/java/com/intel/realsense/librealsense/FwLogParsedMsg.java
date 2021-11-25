package com.intel.realsense.librealsense;

public class FwLogParsedMsg extends LrsClass {

    FwLogParsedMsg(long handle){
        mHandle = handle;
    }

    @Override
    public void close() {
        nRelease(mHandle);
    }

    public String getMessage() { return nGetMessage(mHandle); }
    public String getFileName() {return nGetFileName(mHandle);}
    public String getThreadName() {return nGetThreadName(mHandle);}
    public String getSeverity() {return nGetSeverity(mHandle);}
    public int getLine() {return nGetLine(mHandle);}
    public long getTimestamp(){return nGetTimestamp(mHandle);}
    public int getSequenceId() {return nGetSequenceId(mHandle);}

    /*JNI
    #include <jni.h>
    #include <error.h>
    #include <librealsense2/h/rs_internal.h>
     */

    private native static void nRelease(long handle);/*
    rs2_delete_fw_log_parsed_message(reinterpret_cast<rs2_firmware_log_parsed_message*>(handle));
    */

    private native static String nGetMessage(long handle);/*
    rs2_error* e = NULL;
    const char* message = rs2_get_fw_log_parsed_message(reinterpret_cast<rs2_firmware_log_parsed_message*>(handle), &e);
    handle_error(env, e);

    return env->NewStringUTF(message);
     */

    private native static String nGetFileName(long handle);/*
    rs2_error* e = NULL;
    const char* file_name = rs2_get_fw_log_parsed_file_name(reinterpret_cast<rs2_firmware_log_parsed_message*>(handle), &e);
    handle_error(env, e);

    return env->NewStringUTF(file_name);
     */

    private native static String nGetThreadName(long handle);/*
    rs2_error* e = NULL;
    const char* thread_name = rs2_get_fw_log_parsed_thread_name(reinterpret_cast<rs2_firmware_log_parsed_message*>(handle), &e);
    handle_error(env, e);

    return env->NewStringUTF(thread_name);
     */

    private native static String nGetSeverity(long handle);/*
    rs2_error* e = NULL;
    rs2_log_severity severity = rs2_get_fw_log_parsed_severity(reinterpret_cast<rs2_firmware_log_parsed_message*>(handle), &e);
    handle_error(env, e);

    const char* severity_string = rs2_log_severity_to_string(severity);
    return env->NewStringUTF(severity_string);
     */

    private native static int nGetLine(long handle);/*
    rs2_error* e = NULL;
    int line = rs2_get_fw_log_parsed_line(reinterpret_cast<rs2_firmware_log_parsed_message*>(handle), &e);
    handle_error(env, e);
    return (jint)line;
     */

    private native static long nGetTimestamp(long handle);/*
    rs2_error* e = NULL;
    unsigned int timestamp = rs2_get_fw_log_parsed_timestamp(reinterpret_cast<rs2_firmware_log_parsed_message*>(handle), &e);
    handle_error(env, e);
    return (jlong)(unsigned long long)timestamp;
     */

    private native static int nGetSequenceId(long handle);/*
    rs2_error* e = NULL;
    unsigned int sequence = rs2_get_fw_log_parsed_sequence_id(reinterpret_cast<rs2_firmware_log_parsed_message*>(handle), &e);
    handle_error(env, e);
    return (jint)sequence;
     */

}
