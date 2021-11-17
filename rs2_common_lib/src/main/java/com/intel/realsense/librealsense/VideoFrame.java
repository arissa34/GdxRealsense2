package com.intel.realsense.librealsense;

public class VideoFrame extends Frame {
    private int mWidth = -1;
    private int mHeight = -1;
    private int mStride = -1;
    private int mBitsPerPixel = -1;

    public int getWidth(){
        if(mWidth == -1)
            mWidth = nGetWidth(mHandle);
        return mWidth;
    }

    public int getHeight(){
        if(mHeight == -1)
            mHeight = nGetHeight(mHandle);
        return mHeight;
    }

    public int getStride(){
        if(mStride == -1)
            mStride = nGetStride(mHandle);
        return mStride;
    }

    public int getBitsPerPixel(){
        if(mBitsPerPixel == -1)
            mBitsPerPixel = nGetBitsPerPixel(mHandle);
        return mBitsPerPixel;
    }

    public VideoStreamProfile getProfile() {
        return new VideoStreamProfile(nGetStreamProfile(mHandle));
    }

    protected VideoFrame(long handle) {
        super(handle);
        mOwner = false;
    }

    /*JNI
    #include <jni.h>
    #include "include/error.h"
    #include "include/librealsense2/rs.h"
     */

    private static native int nGetWidth(long handle);/*
    rs2_error *e = NULL;
    int rv = rs2_get_frame_width(reinterpret_cast<const rs2_frame *>(handle), &e);
    handle_error(env, e);
    return rv;
    */
    private static native int nGetHeight(long handle);/*
    rs2_error *e = NULL;
    int rv = rs2_get_frame_height(reinterpret_cast<const rs2_frame *>(handle), &e);
    handle_error(env, e);
    return rv;
     */
    private static native int nGetStride(long handle);/*
    rs2_error *e = NULL;
    int rv = rs2_get_frame_stride_in_bytes(reinterpret_cast<const rs2_frame *>(handle), &e);
    handle_error(env, e);
    return rv;
     */
    private static native int nGetBitsPerPixel(long handle);/*
    rs2_error *e = NULL;
    int rv = rs2_get_frame_bits_per_pixel(reinterpret_cast<const rs2_frame *>(handle), &e);
    handle_error(env, e);
    return rv;
     */
}
