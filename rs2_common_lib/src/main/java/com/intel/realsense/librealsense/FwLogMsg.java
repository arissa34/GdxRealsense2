package com.intel.realsense.librealsense;

public class FwLogMsg extends LrsClass{

    FwLogMsg(long handle){
        mHandle = handle;
    }

    public enum LogSeverity {
        DEBUG(0),
        INFO(1),
        WARN(2),
        ERROR(4),
        FATAL(5);

        private final int mValue;

        private LogSeverity(int value) { mValue = value; }
        public int value() { return mValue; }
    }

    @Override
    public void close() {
        nRelease(mHandle);
    }

    public LogSeverity getSeverity() { return LogSeverity.values()[nGetSeverity(mHandle)]; }
    public String getSeverityStr(){ return nGetSeverityStr(mHandle); }
    public long getTimestamp(){return nGetTimestamp(mHandle);}
    public byte[] getData(byte[] buffer){ return nGetData(mHandle, buffer);}
    public int getSize() { return nGetSize(mHandle);}

    /*JNI
    #include <jni.h>
    #include <error.h>
    #include <librealsense2/h/rs_internal.h>
     */

    private native static void nRelease(long handle);/*
    rs2_delete_fw_log_message(reinterpret_cast<rs2_firmware_log_message*>(handle));
    */

    private native static int nGetSeverity(long handle);/*
    rs2_error* e = NULL;
    rs2_log_severity severity = rs2_fw_log_message_severity(reinterpret_cast<rs2_firmware_log_message*>(handle), &e);
    handle_error(env, e);
    return (jint)severity;
     */

    private native static String nGetSeverityStr(long handle);/*
    rs2_error* e = NULL;
    rs2_log_severity severity = rs2_fw_log_message_severity(reinterpret_cast<rs2_firmware_log_message*>(handle), &e);
    handle_error(env, e);

    const char* severity_string = rs2_log_severity_to_string(severity);
    return env->NewStringUTF(severity_string);
     */

    private native static long nGetTimestamp(long handle);/*
    rs2_error* e = NULL;
    unsigned int timestamp = rs2_fw_log_message_timestamp(reinterpret_cast<rs2_firmware_log_message*>(handle), &e);
    handle_error(env, e);
    return (jlong)(unsigned long long)timestamp;
     */

    private native static byte[] nGetData(long handle, byte[] input_buffer);/*
    rs2_error* e = NULL;
    int size = rs2_fw_log_message_size(reinterpret_cast<rs2_firmware_log_message*>(handle), &e);
    handle_error(env, e);

    const unsigned char* message_data = rs2_fw_log_message_data(reinterpret_cast<rs2_firmware_log_message*>(handle), &e);
    handle_error(env, e);

    jbyteArray rv = env->NewByteArray(size);
    env->SetByteArrayRegion (rv, 0, size, reinterpret_cast<const jbyte *>(message_data));
    return rv;
     */

    private native static int nGetSize(long handle);/*
    rs2_error* e = NULL;
    int size = rs2_fw_log_message_size(reinterpret_cast<rs2_firmware_log_message*>(handle), &e);
    handle_error(env, e);
    return (jint)size;
     */
}
