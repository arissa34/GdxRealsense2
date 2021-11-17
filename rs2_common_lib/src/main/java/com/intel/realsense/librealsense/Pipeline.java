package com.intel.realsense.librealsense;

public class Pipeline extends LrsClass{
    public Pipeline(){
        try(RsContext ctx = new RsContext()) {
            mHandle = nCreate(ctx.getHandle());
        }
    }

    public Pipeline(RsContext ctx){
        mHandle = nCreate(ctx.getHandle());
    }

    public PipelineProfile start() throws Exception{
        return new PipelineProfile(nStart(mHandle));
    }

    public PipelineProfile start(FrameCallback cb) throws Exception{
        long h = nStartWithCallback(mHandle, cb);
        return new PipelineProfile(h);
    }

    public PipelineProfile start(Config config) throws Exception {
        long h = nStartWithConfig(mHandle, config.getHandle());
        return new PipelineProfile(h);
    }

    public PipelineProfile start(Config config, FrameCallback cb) throws Exception {
        long h = nStartWithConfigAndCallback(mHandle, config.getHandle(), cb);
        return new PipelineProfile(h);
    }

    public void stop() {
        nStop(mHandle);
    }

    public FrameSet waitForFrames() throws Exception {
        return waitForFrames(5000); //defining default timeout
    }

    public FrameSet waitForFrames (int timeoutMilliseconds) throws Exception{
        long frameHandle = nWaitForFrames(mHandle, timeoutMilliseconds);
        return new FrameSet(frameHandle);
    }

    @Override
    public void close(){
        nDelete(mHandle);
    }

    /*JNI
    #include <jni.h>
    #include "include/error.h"
    #include "include/librealsense2/rs.h"
    #include "include/librealsense2/h/rs_pipeline.h"
    #include "include/frame_callback.h"

    static frame_callback_data pdata = {NULL, 0, JNI_FALSE, NULL, NULL};
     */

    private static native long nCreate(long context);/*
    rs2_error* e = NULL;
    rs2_pipeline* rv = rs2_create_pipeline(reinterpret_cast<rs2_context *>(context), &e);
    handle_error(env, e);
    return reinterpret_cast<jlong>(rv);
    */
    private static native void nDelete(long handle);/*
    rs2_delete_pipeline(reinterpret_cast<rs2_pipeline *>(handle));
     */
    private static native long nStart(long handle);/*
    rs2_error* e = NULL;
    auto rv = rs2_pipeline_start(reinterpret_cast<rs2_pipeline *>(handle), &e);
    handle_error(env, e);
    return reinterpret_cast<jlong>(rv);
     */
    private static native long nStartWithCallback(long handle, FrameCallback jcb);/*
    rs2_error* e = NULL;

    if (rs_jni_callback_init(env, jcb, &pdata) != true) return NULL;

    auto cb = [&](rs2::frame f) {
        rs_jni_cb(f, &pdata);
    };

    auto rv = rs2_pipeline_start_with_callback_cpp(reinterpret_cast<rs2_pipeline *>(handle), new rs2::frame_callback<decltype(cb)>(cb), &e);
    handle_error(env, e);
    return reinterpret_cast<jlong>(rv);
     */
    private static native long nStartWithConfig(long handle, long configHandle);/*
    rs2_error *e = NULL;
    auto rv = rs2_pipeline_start_with_config(reinterpret_cast<rs2_pipeline *>(handle),
                                             reinterpret_cast<rs2_config *>(configHandle), &e);
    handle_error(env, e);
    return reinterpret_cast<jlong>(rv);
     */
    private static native long nStartWithConfigAndCallback(long handle, long configHandle, FrameCallback jcb);/*
    rs2_error *e = NULL;

    if (rs_jni_callback_init(env, jcb, &pdata) != true) return NULL;

    auto cb = [&](rs2::frame f) {
        rs_jni_cb(f, &pdata);
    };

    auto rv = rs2_pipeline_start_with_config_and_callback_cpp(reinterpret_cast<rs2_pipeline *>(handle),
                                             reinterpret_cast<rs2_config *>(configHandle), new rs2::frame_callback<decltype(cb)>(cb), &e);
    handle_error(env, e);
    return reinterpret_cast<jlong>(rv);
     */
    private static native void nStop(long handle);/*
    rs2_error* e = NULL;
    rs_jni_cleanup(env, &pdata);
    rs2_pipeline_stop(reinterpret_cast<rs2_pipeline *>(handle), &e);
    handle_error(env, e);
     */
    private static native long nWaitForFrames(long handle, int timeout);/*
    rs2_error* e = NULL;
    rs2_frame *rv = rs2_pipeline_wait_for_frames(reinterpret_cast<rs2_pipeline *>(handle), timeout, &e);
    handle_error(env, e);
    return reinterpret_cast<jlong>(rv);
     */
}
