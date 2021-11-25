package com.intel.realsense.librealsense;

public class FrameSet extends Frame {
    private int mSize = 0;

    public FrameSet(long handle) {
        super(handle);
        mSize = nFrameCount(mHandle);
    }

    public Frame first(StreamType type) {
        return first(type, StreamFormat.ANY);
    }

    public Frame first(StreamType type, StreamFormat format) {
        for(int i = 0; i < mSize; i++) {
            Frame f = new Frame(nExtractFrame(mHandle, i));
            try(StreamProfile p = f.getProfile()){
                if(p.getType() == type && (p.getFormat() == format || format == StreamFormat.ANY))
                    return f;
            }
            f.close();
        }
        return null;
    }

    public void foreach(FrameCallback callback) {
        for(int i = 0; i < mSize; i++) {
            try(Frame f = new Frame(nExtractFrame(mHandle, i))){
                callback.onFrame(f);
            }
        }
    }

    public int getSize(){ return mSize; }

    public FrameSet applyFilter(FilterInterface filter) {
        return filter.process(this);
    }

    public FrameSet releaseWith(FrameReleaser frameReleaser){
        frameReleaser.addFrame(this);
        return this;
    }

    @Override
    public void close() {
        nRelease(mHandle);
    }

    @Override
    public FrameSet clone() {
        FrameSet rv = new FrameSet(mHandle);
        nAddRef(mHandle);
        return rv;
    }

    /*JNI
    #include <jni.h>
    #include <error.h>
    #include <librealsense2/rs.h>
     */

    private static native void nAddRef(long handle);/*
    rs2_error *e = NULL;
    rs2_frame_add_ref((rs2_frame *) handle, &e);
    handle_error(env, e);
    */
    private static native void nRelease(long handle);/*
    rs2_release_frame(reinterpret_cast<rs2_frame *>(handle));
     */
    private static native long nExtractFrame(long handle, int index);/*
    rs2_error *e = NULL;
    rs2_frame *rv = rs2_extract_frame(reinterpret_cast<rs2_frame *>(handle), index, &e);
    handle_error(env, e);
    return reinterpret_cast<jlong>(rv);
     */
    private static native int nFrameCount(long handle);/*
    rs2_error *e = NULL;
    int rv = rs2_embedded_frames_count(reinterpret_cast<rs2_frame *>(handle), &e);
    handle_error(env, e);
    return rv;
     */
}
