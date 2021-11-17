package com.intel.realsense.librealsense;

public class Points extends Frame {
    private float[] mData;
    private float[] mTextureCoordinates;

    protected Points(long handle) {
        super(handle);
        mOwner = false;
    }

    public float[] getVertices(){
        if(mData == null){
            mData = new float[getCount() * 3];
            getData(mData);
        }
        return mData;
    }

    public float[] getTextureCoordinates(){
        if(mTextureCoordinates == null){
            mTextureCoordinates = new float[getCount() * 2];
            getTextureCoordinates(mTextureCoordinates);
        }
        return mTextureCoordinates;
    }

    public void getTextureCoordinates(float[] data) {
        nGetTextureCoordinates(mHandle, data);
    }

    public void getData(float[] data) {
        nGetData(mHandle, data);
    }

    public int getCount(){
        return nGetCount(mHandle);
    }

    public void exportToPly(String filePath, VideoFrame texture) {
        nExportToPly(mHandle, filePath, texture.getHandle());
    }

    /*JNI
    #include <jni.h>
    #include "include/error.h"
    #include "include/librealsense2/rs.h"
     */

    private static native int nGetCount(long handle);/*
    rs2_error *e = NULL;
    int rv = rs2_get_frame_points_count(reinterpret_cast<const rs2_frame *>(handle), &e);
    handle_error(env, e);
    return rv;
    */

    private static native void nGetData(long handle, float[] data_);/*
    jsize length = (jsize)env->GetArrayLength(obj_data_);
    rs2_error *e = NULL;
    env->SetFloatArrayRegion(obj_data_, 0, length, static_cast<const jfloat *>(rs2_get_frame_data(
            reinterpret_cast<const rs2_frame *>(handle), &e)));
    handle_error(env, e);
     */

    private static native void nGetTextureCoordinates(long handle, float[] data_);/*
    jsize length = (jsize)env->GetArrayLength(obj_data_);
    rs2_error *e = NULL;
    env->SetFloatArrayRegion(obj_data_, 0, length, reinterpret_cast<const jfloat *>(rs2_get_frame_texture_coordinates(
            reinterpret_cast<const rs2_frame *>(handle), &e)));
    handle_error(env, e);
     */

    private static native void nExportToPly(long handle, String filePath, long textureHandle);/*
    rs2_error *e = NULL;
    rs2_export_to_ply(reinterpret_cast<const rs2_frame *>(handle), filePath,
                      reinterpret_cast<rs2_frame *>(textureHandle), &e);
    handle_error(env, e);
     */
}
