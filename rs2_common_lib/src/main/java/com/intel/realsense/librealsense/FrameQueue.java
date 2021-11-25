package com.intel.realsense.librealsense;

public class FrameQueue extends LrsClass {

    public FrameQueue(){
        mHandle = nCreate(1);
    }

    public FrameQueue(int capacity){
        mHandle = nCreate(capacity);
    }

    public Frame pollForFrame() {
        long rv = nPollForFrame(mHandle);
        if(rv != 0) {
            return new Frame(rv);
        }
        return null;
    }

    public FrameSet pollForFrames() {
        long rv = nPollForFrame(mHandle);
        if(rv != 0) {
            return new FrameSet(rv);
        }
        return null;
    }

    public Frame waitForFrame() {
        return waitForFrame(5000);
    }

    public Frame waitForFrame(int timeout) {
        long rv = nWaitForFrames(mHandle, timeout);
        return new Frame(rv);
    }

    public FrameSet waitForFrames() {
        return waitForFrames();
    }

    public FrameSet waitForFrames(int timeout) {
        long rv = nWaitForFrames(mHandle, timeout);
        return new FrameSet(rv);
    }

    @Override
    public void close() throws Exception {
        nDelete(mHandle);
    }

    public void Enqueue(Frame f)
    {
        nEnqueue(mHandle, f.getHandle());
    }

    /*JNI
    #include <jni.h>
    #include <error.h>
    #include <librealsense2/rs.h>
     */

    private static native long nCreate(int capacity);/*
    rs2_error *e = NULL;
    rs2_frame_queue *rv = rs2_create_frame_queue(capacity, &e);
    handle_error(env, e);
    return (jlong) rv;
    */
    private static native void nDelete(long handle);/*
    rs2_delete_frame_queue((rs2_frame_queue *) handle);
     */
    private static native long nPollForFrame(long handle);/*
    rs2_frame *output_frame = NULL;
    rs2_error *e = NULL;
    int rv = rs2_poll_for_frame((rs2_frame_queue *) handle, &output_frame, &e);
    handle_error(env, e);
    return (jlong) (rv ? output_frame : 0);
     */
    private static native long nWaitForFrames(long handle, int timeout);/*
    rs2_error *e = NULL;
    rs2_frame *rv = rs2_wait_for_frame((rs2_frame_queue *) handle, (unsigned int) timeout, &e);
    handle_error(env, e);
    return (jlong) rv;
     */
    private static native void nEnqueue(long handle, long frameHandle);/*
    rs2_error *e = NULL;
    rs2_frame_add_ref((rs2_frame *) frameHandle, &e);
    handle_error(env, e);
    rs2_enqueue_frame((rs2_frame *) frameHandle, (void *) handle);
     */
}
