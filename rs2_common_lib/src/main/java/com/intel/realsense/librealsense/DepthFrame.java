package com.intel.realsense.librealsense;

public class DepthFrame extends VideoFrame {
    protected DepthFrame(long handle) {
        super(handle);
        mOwner = false;
    }

    public float getDistance(int x, int y) {
        return nGetDistance(mHandle, x, y);
    }

    public float getUnits() {
        return nGetUnits(mHandle);
    }

    /*JNI
    #include <jni.h>
    #include <error.h>
    #include <librealsense2/rs.h>
     */

    private static native float nGetDistance(long handle, int x, int y);/*
    rs2_error *e = NULL;
    float rv = rs2_depth_frame_get_distance(reinterpret_cast<const rs2_frame *>(handle), x, y, &e);
    handle_error(env, e);
    return rv;
    */
    private static native float nGetUnits(long handle);/*
    rs2_error *e = NULL;
    float rv = rs2_depth_frame_get_units( reinterpret_cast<const rs2_frame *>(handle), &e );
    handle_error( env, e );
    return rv;
     */
}
