#include <com_intel_realsense_librealsense_StreamProfile.h>

//@line:75

    #include <jni.h>
    #include <cstring>      //for memcpy
    #include <error.h>
    #include <librealsense2/rs.h>
     JNIEXPORT jboolean JNICALL Java_com_intel_realsense_librealsense_StreamProfile_nIsProfileExtendableTo(JNIEnv* env, jclass clazz, jlong handle, jint extension) {


//@line:82

    rs2_error *e = NULL;
    int rv = rs2_stream_profile_is(reinterpret_cast<const rs2_stream_profile *>(handle),
                                   static_cast<rs2_extension>(extension), &e);
    handle_error(env, e);
    return rv;
    

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_StreamProfile_nGetProfile(JNIEnv* env, jclass clazz, jlong handle, jobject params) {


//@line:90

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
     

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_StreamProfile_nDelete(JNIEnv* env, jclass clazz, jlong handle) {


//@line:116

    rs2_delete_stream_profile((rs2_stream_profile *) handle);
     

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_StreamProfile_nGetExtrinsicTo(JNIEnv* env, jclass clazz, jlong handle, jlong otherHandle, jobject extrinsic) {


//@line:120

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
     

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_StreamProfile_nRegisterExtrinsic(JNIEnv* env, jclass clazz, jlong fromHandle, jlong toHandle, jobject extrinsic) {


//@line:154

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
     

}

