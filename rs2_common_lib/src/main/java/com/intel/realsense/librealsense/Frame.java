package com.intel.realsense.librealsense;

public class Frame extends LrsClass implements Cloneable{

    Frame(long handle){
        mHandle = handle;
    }

    public boolean is(Extension extension) {
        return nIsFrameExtendableTo(mHandle, extension.value());
    }

    public <T extends Frame> T as(Extension extension) {
        switch (extension){
            case VIDEO_FRAME: return (T) new VideoFrame(mHandle);
            case DEPTH_FRAME: return (T) new DepthFrame(mHandle);
            case MOTION_FRAME: return (T) new MotionFrame(mHandle);
            case POINTS: return (T) new Points(mHandle);
            case FRAMESET: return (T) new FrameSet(mHandle);
        }
        throw new RuntimeException("this profile is not extendable to " + extension.name());
    }

    public StreamProfile getProfile() {
        return new StreamProfile(nGetStreamProfile(mHandle));
    }

    public int getDataSize() {
        return nGetDataSize(mHandle);
    }

    public void getData(byte[] data) {
        nGetData(mHandle, data);
    }

    public int getNumber(){
        return nGetNumber(mHandle);
    }

    public double getTimestamp(){
        return nGetTimestamp(mHandle);
    }

    public TimestampDomain getTimestampDomain() {
        int rv = nGetTimestampDomain(mHandle);
        return TimestampDomain.values()[rv];
    }

    public boolean supportsMetadata(FrameMetadata type) { return nSupportsMetadata(mHandle, type.value());}

    public long getMetadata(FrameMetadata type) { return nGetMetadata(mHandle, type.value());}

    public Frame applyFilter(FilterInterface filter) {
        return filter.process(this);
    }

    public Frame releaseWith(FrameReleaser frameReleaser){
        frameReleaser.addFrame(this);
        return this;
    }

    @Override
    public void close() {
        if(mOwner)
            nRelease(mHandle);
    }

    @Override
    public Frame clone() {
        Frame rv = new Frame(mHandle);
        nAddRef(mHandle);
        return rv;
    }

    /*JNI
    #include <jni.h>
    #include <error.h>
    #include <librealsense2/rs.h>
     */

    private static native boolean nIsFrameExtendableTo(long handle, int extension);/*
    rs2_error *e = NULL;
    int rv = rs2_is_frame_extendable_to(reinterpret_cast<const rs2_frame *>(handle),
                                        static_cast<rs2_extension>(extension), &e);
    handle_error(env, e);
    return rv;

    */
    private static native void nAddRef(long handle);/*
    rs2_error *e = NULL;
    rs2_frame_add_ref((rs2_frame *) handle, &e);
    handle_error(env, e);
     */
    private static native void nRelease(long handle);/*
    if (handle)
        rs2_release_frame((rs2_frame *) handle);
     */
    protected static native long nGetStreamProfile(long handle);/*
    rs2_error *e = NULL;
    const rs2_stream_profile *rv = rs2_get_frame_stream_profile(
            reinterpret_cast<const rs2_frame *>(handle), &e);
    handle_error(env, e);
    return (jlong) rv;
     */
    private static native int nGetDataSize(long handle);/*
    rs2_error *e = NULL;
    auto rv = rs2_get_frame_data_size(reinterpret_cast<const rs2_frame *>(handle), &e);
    handle_error(env, e);
    return (jint)rv;
     */
    private static native void nGetData(long handle, byte[] data);/*
    jsize length = env->GetArrayLength(obj_data);
    rs2_error *e = NULL;
    env->SetByteArrayRegion(obj_data, 0, length, static_cast<const jbyte *>(rs2_get_frame_data(reinterpret_cast<const rs2_frame *>(handle), &e)));
    handle_error(env, e);
     */
    private static native int nGetNumber(long handle);/*
    rs2_error *e = NULL;
    unsigned long long rv = rs2_get_frame_number(reinterpret_cast<const rs2_frame *>(handle), &e);
    handle_error(env, e);
    return rv;
     */
    private static native double nGetTimestamp(long handle);/*
    rs2_error *e = NULL;
    double rv = rs2_get_frame_timestamp(reinterpret_cast<const rs2_frame *>(handle), &e);
    handle_error(env, e);
    return rv;
     */
    private static native int nGetTimestampDomain(long handle);/*
    rs2_error *e = NULL;
    int rv = rs2_get_frame_timestamp_domain(reinterpret_cast<const rs2_frame *>(handle), &e);
    handle_error(env, e);
    return rv;
     */
    private static native long nGetMetadata(long handle, int metadata_type);/*
    rs2_error *e = NULL;
    long rv = rs2_get_frame_metadata(reinterpret_cast<const rs2_frame *>(handle),
                                     static_cast<rs2_frame_metadata_value>(metadata_type), &e);
    handle_error(env, e);
    return rv;
     */
    private static native boolean nSupportsMetadata(long handle, int metadata_type);/*
    rs2_error *e = NULL;
    int rv = rs2_supports_frame_metadata(reinterpret_cast<const rs2_frame *>(handle),
                                     static_cast<rs2_frame_metadata_value>(metadata_type), &e);
    handle_error(env, e);
    return rv > 0;
     */
}
