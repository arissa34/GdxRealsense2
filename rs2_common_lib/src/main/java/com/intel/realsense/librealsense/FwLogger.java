package com.intel.realsense.librealsense;

import java.io.File;
import java.util.Scanner;


public class FwLogger extends Device {

    private boolean mIsParserAvailable = false;
    private boolean mFwLogPullingStatus = false;

    FwLogger(long handle){
        super(handle);
        mOwner = false;
    }

    public boolean initParser(String xml_path) {
        // checking the input file path for parsing logs
        if (!xml_path.contentEquals("") ) {
            try{
                try (Scanner scanner = new Scanner( new File(xml_path), "UTF-8" )) {
                    String xml_content_raw = scanner.useDelimiter("\\A").next();
                    String xml_content = xml_content_raw.replaceAll("\r\n", "\n" );
                    if (nInitParser(mHandle, xml_content)) {
                        mIsParserAvailable = true;
                    }
                }
            }
            catch(Exception e){
                throw new RuntimeException("path to fw logs xml did not succeed: " + e.getMessage());
            }
        }
        return mIsParserAvailable;
    }

    public FwLogMsg getFwLog(){
        mFwLogPullingStatus = false;
        return new FwLogMsg(nGetFwLog(mHandle));
    }

    public FwLogMsg getFwLogsFromFlash() {
        mFwLogPullingStatus = false;
        return new FwLogMsg(nGetFlashLog(mHandle));
    }

    public long getNumberOfUnreadFWLogs() {
        return nGetNumberOfFwLogs(mHandle);
    }

    public boolean getFwLogPullingStatus() { return mFwLogPullingStatus; }

    public FwLogParsedMsg parseFwLog(FwLogMsg msg) {
        return new FwLogParsedMsg(nParseFwLog(mHandle, msg.getHandle()));
    }

    /*JNI
    #include <jni.h>
    #include "include/error.h"
    #include "include/librealsense2/h/rs_internal.h"
     */

    private native long nGetFwLog(long fw_logger_handle);/*
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
    */

    private native long nGetFlashLog(long fw_logger_handle);/*
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
     */

    private native long nGetNumberOfFwLogs(long fw_logger_handle);/*
    rs2_error* e = NULL;
    unsigned int numOfFwLogsPolledFromDevice = rs2_get_number_of_fw_logs(reinterpret_cast<rs2_device*>(fw_logger_handle), &e);
    handle_error(env, e);

    return (jlong)(unsigned long long)numOfFwLogsPolledFromDevice;
     */

    private static native boolean nInitParser(long fw_logger_handle, String xml_content_arr);/*
    rs2_error* e = NULL;
    int result = rs2_init_fw_log_parser(reinterpret_cast<rs2_device*>(fw_logger_handle), xml_content_arr, &e);
    handle_error(env, e);

    bool resultBool = (result != 0);
    return (jboolean)resultBool;
     */

    private static native long nParseFwLog(long fw_logger_handle, long fw_log_msg_handle);/*
    rs2_error* e = NULL;
    rs2_firmware_log_parsed_message* parsed_msg = rs2_create_fw_log_parsed_message(reinterpret_cast<rs2_device*>(fw_logger_handle), &e);
    handle_error(env, e);

    int result = rs2_parse_firmware_log(reinterpret_cast<rs2_device*>(fw_logger_handle),
                                   reinterpret_cast<rs2_firmware_log_message*>(fw_log_msg_handle),
                                        parsed_msg, &e);
    handle_error(env, e);

    return (jlong)parsed_msg;
     */

}
