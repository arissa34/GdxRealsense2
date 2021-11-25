package com.intel.realsense.librealsense;

public class Config extends LrsClass {

    public Config(){
        mHandle = nCreate();
    }

    public void enableStream(StreamType type) {
        enableStream(type, -1, 0, 0, StreamFormat.ANY, 0);
    }

    public void enableStream(StreamType type, StreamFormat format){
        enableStream(type, -1, 0, 0, format, 0);
    }

    public void enableStream(StreamType type, int width, int height) {
        enableStream(type, -1, width, height, StreamFormat.ANY, 0);
    }

    public void enableStream(StreamType type, int width, int height, StreamFormat format){
        enableStream(type, -1, width, height, format, 0);
    }

    public void enableStream(StreamType type, int index, int width, int height, StreamFormat format, int framerate){
        nEnableStream(mHandle, type.value(), index, width, height, format.value(), framerate);
    }

    public void disableStream(StreamType type) { nDisableStream(mHandle, type.value()); }

    public void enableAllStreams() { nEnableAllStreams(mHandle); }

    public void disableAllStreams() { nDisableAllStreams(mHandle); }

    public void enableRecordToFile(String filePath) {
        nEnableRecordToFile(mHandle, filePath);
    }

    public void enableDeviceFromFile(String filePath) {
        nEnableDeviceFromFile(mHandle, filePath);
    }

    public void enableDevice(String serial) { nEnableDevice(mHandle, serial); }

    public boolean canResolve(Pipeline pipeline){
        return nCanResolve(mHandle, pipeline.mHandle);
    }

    public void resolve(Pipeline pipeline) {
        long pipeHandle = nResolve(mHandle, pipeline.mHandle);
        PipelineProfile rv = new PipelineProfile(pipeHandle);
        rv.close();//TODO: enable when PipelineProfile is implemented
    }

    /*JNI
    #include <jni.h>
    #include <error.h>

    #include <librealsense2/rs.h>
    #include <librealsense2/h/rs_config.h>
     */

    @Override
    public void close() {
        nDelete(mHandle);
    }

    private static native long nCreate();/*
    rs2_error *e = NULL;
    rs2_config *rv = rs2_create_config(&e);
    handle_error(env, e);
    return (jlong) rv;
    */
    private static native void nDelete(long handle);/*
    rs2_delete_config((rs2_config *) handle);
     */
    private static native void nEnableStream(long handle, int type, int index, int width, int height, int format, int framerate);/*
    rs2_error *e = NULL;
    rs2_config_enable_stream(reinterpret_cast<rs2_config *>(handle), static_cast<rs2_stream>(type), index, width, height,
                             static_cast<rs2_format>(format), framerate, &e);
    handle_error(env, e);
     */
    private static native void nDisableStream(long handle, int streamType);/*
    rs2_error *e = NULL;
    rs2_config_disable_stream(reinterpret_cast<rs2_config *>(handle),
                              static_cast<rs2_stream>(streamType), &e);
    handle_error(env, e);
     */
    private static native void nEnableAllStreams(long handle);/*
    rs2_error *e = NULL;
    rs2_config_enable_all_stream(reinterpret_cast<rs2_config *>(handle), &e);
    handle_error(env, e);
     */
    private static native void nDisableAllStreams(long handle);/*
    rs2_error *e = NULL;
    rs2_config_disable_all_streams(reinterpret_cast<rs2_config *>(handle), &e);
    handle_error(env, e);
     */
    private static native void nEnableDeviceFromFile(long handle, String filePath);/*
    rs2_error *e = NULL;
    rs2_config_enable_device_from_file(reinterpret_cast<rs2_config *>(handle), filePath, &e);
    handle_error(env, e);
     */
    private static native void nEnableDevice(long handle, String serial);/*
    rs2_error *e = NULL;
    rs2_config_enable_device(reinterpret_cast<rs2_config *>(handle), serial, &e);
    handle_error(env, e);
     */
    private static native void nEnableRecordToFile(long handle, String filePath);/*
    rs2_error *e = NULL;
    rs2_config_enable_record_to_file(reinterpret_cast<rs2_config *>(handle), filePath, &e);
    handle_error(env, e);
     */
    private static native boolean nCanResolve(long handle, long pipelineHandle);/*
    rs2_error *e = NULL;
    auto rv = rs2_config_can_resolve(reinterpret_cast<rs2_config *>(handle),
                                     reinterpret_cast<rs2_pipeline *>(pipelineHandle), &e);
    handle_error(env, e);
    return rv > 0;
     */
    private static native long nResolve(long handle, long pipelineHandle);/*
    rs2_error *e = NULL;
    rs2_pipeline_profile* rv = rs2_config_resolve(reinterpret_cast<rs2_config *>(handle),
                                                  reinterpret_cast<rs2_pipeline *>(pipelineHandle), &e);
    handle_error(env, e);
    return reinterpret_cast<jlong>(rv);
     */
}
