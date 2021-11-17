package com.intel.realsense.librealsense;


public class StreamProfile extends LrsClass {
    private StreamType mType;
    private StreamFormat mFormat;

    private ProfileParams mPp;

    private class ProfileParams {
        public int type;
        public int format;
        public int index;
        public int uniqueId;
        public int frameRate;
    }

    StreamProfile(long handle){
        mHandle = handle;
        mPp = new ProfileParams();
        nGetProfile(mHandle, mPp);
        mType = StreamType.values()[mPp.type];
        mFormat = StreamFormat.values()[mPp.format];
    }

    public StreamType getType() {
        return mType;
    }

    public StreamFormat getFormat() {
        return mFormat;
    }

    public int getIndex() {
        return mPp.index;
    }

    public int getUniqueId() {
        return mPp.uniqueId;
    }

    public int getFrameRate() {
        return mPp.frameRate;
    }

    public Extrinsic getExtrinsicTo(StreamProfile other) throws Exception {
        Extrinsic extrinsic = new Extrinsic();
        nGetExtrinsicTo(mHandle, other.mHandle, extrinsic);
        return extrinsic;
    }

    public void registerExtrinsic(StreamProfile other, Extrinsic extrinsic)
    {
        nRegisterExtrinsic(mHandle, other.mHandle, extrinsic);
    }

    public boolean is(Extension extension) {
        return nIsProfileExtendableTo(mHandle, extension.value());
    }

    public <T extends StreamProfile> T as(Extension extension) {
        switch (extension){
            case VIDEO_PROFILE: return (T) new VideoStreamProfile(mHandle);
            case MOTION_PROFILE: return (T) new MotionStreamProfile(mHandle);
        }
        throw new RuntimeException("this profile is not extendable to " + extension.name());
    }

    @Override
    public void close() {
//        if(mOwner)
//            nDelete(mHandle);
    }

    /*JNI
    #include <jni.h>
    #include <cstring>      //for memcpy
    #include "include/error.h"
    #include "include/librealsense2/rs.h"
     */

    private static native boolean nIsProfileExtendableTo(long handle, int extension);/*
    rs2_error *e = NULL;
    int rv = rs2_stream_profile_is(reinterpret_cast<const rs2_stream_profile *>(handle),
                                   static_cast<rs2_extension>(extension), &e);
    handle_error(env, e);
    return rv;
    */

    private static native void nGetProfile(long handle, ProfileParams params);/*
    rs2_stream stream_type = RS2_STREAM_ANY;
    rs2_format format = RS2_FORMAT_ANY;
    int index = -1;
    int uniqueId = -1;
    int frameRate = -1;
    rs2_error *e = NULL;

    rs2_get_stream_profile_data((const rs2_stream_profile *) handle, &stream_type, &format, &index, &uniqueId, &frameRate, &e);
    handle_error(env, e);

    jclass claxx = env->GetObjectClass(params);

    jfieldID typeField = env->GetFieldID(claxx, "type", "I");
    jfieldID formatField = env->GetFieldID(claxx, "format", "I");
    jfieldID indexField = env->GetFieldID(claxx, "index", "I");
    jfieldID uniqueIdField = env->GetFieldID(claxx, "uniqueId", "I");
    jfieldID frameRateField = env->GetFieldID(claxx, "frameRate", "I");

    env->SetIntField(params, typeField, stream_type);
    env->SetIntField(params, formatField, format);
    env->SetIntField(params, indexField, index);
    env->SetIntField(params, uniqueIdField, uniqueId);
    env->SetIntField(params, frameRateField, frameRate);
     */

    private static native void nDelete(long handle);/*
    rs2_delete_stream_profile((rs2_stream_profile *) handle);
     */

    private static native void nGetExtrinsicTo(long handle, long otherHandle, Extrinsic extrinsic);/*
    rs2_error* e = nullptr;
    rs2_extrinsics extr;
    rs2_get_extrinsics(reinterpret_cast<const rs2_stream_profile *>(handle),
            reinterpret_cast<const rs2_stream_profile *>(otherHandle),&extr, &e);
    handle_error(env, e);

    if (e != nullptr)
    {
        return;
    }

    jclass claxx = env->GetObjectClass(extrinsic);

    //retrieving the array members
    //mRotation
    jfieldID rotation_field = env->GetFieldID(claxx, "mRotation", "[F");
    jfloatArray rotationArray = env->NewFloatArray(9);
    if (rotationArray != NULL)
    {
        env->SetFloatArrayRegion(rotationArray, 0, 9, reinterpret_cast<jfloat*>(&extr.rotation));
    }
    env->SetObjectField(extrinsic, rotation_field, rotationArray);

    //mTranslation
    jfieldID translation_field = env->GetFieldID(claxx, "mTranslation", "[F");
    jfloatArray translationArray = env->NewFloatArray(3);
    if (translationArray != NULL)
    {
        env->SetFloatArrayRegion(translationArray, 0, 3, reinterpret_cast<jfloat*>(&extr.translation));
    }
    env->SetObjectField(extrinsic, translation_field, translationArray);
     */

    private static native void nRegisterExtrinsic(long fromHandle, long toHandle, Extrinsic extrinsic);/*
    rs2_error* e = nullptr;
    rs2_extrinsics extr;

    //fill c++ extrinsic from java extrinsic data
    jclass claxx = env->GetObjectClass(extrinsic);
    //fill rotation
    jfieldID rotation_field = env->GetFieldID(claxx, "mRotation", "[F");
    jobject rotationObject = env->GetObjectField(extrinsic, rotation_field);
    jfloatArray* rotationArray = reinterpret_cast<jfloatArray *>(&rotationObject);
    jfloat * rotation = env->GetFloatArrayElements(*rotationArray, NULL);
    memcpy(extr.rotation, rotation, 9 * sizeof(float));
    env->ReleaseFloatArrayElements(*rotationArray, rotation, 0);
    //fill translation
    jfieldID translation_field = env->GetFieldID(claxx, "mTranslation", "[F");
    jobject translationObject = env->GetObjectField(extrinsic, translation_field);
    jfloatArray* translationArray = reinterpret_cast<jfloatArray *>(&translationObject);
    jfloat * translation = env->GetFloatArrayElements(*translationArray, NULL);
    memcpy(extr.translation, translation, 3 * sizeof(float));
    env->ReleaseFloatArrayElements(*translationArray, translation, 0);

    //calling the api method
    rs2_register_extrinsics(reinterpret_cast<const rs2_stream_profile *>(fromHandle),
                       reinterpret_cast<const rs2_stream_profile *>(toHandle), extr, &e);
    handle_error(env, e);
     */
}
