#include <com_intel_realsense_librealsense_VideoStreamProfile.h>

//@line:37

    #include <jni.h>
    #include <cstring>      //for memcpy
    #include <error.h>
    #include <librealsense2/rs.h>
     JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_VideoStreamProfile_nGetResolution(JNIEnv* env, jclass clazz, jlong handle, jobject params) {


//@line:44

    int width = -1;
    int height = -1;
    rs2_error *e = NULL;

    rs2_get_video_stream_resolution((const rs2_stream_profile *) handle, &width, &height, &e);
    handle_error(env, e);

    jclass claxx = env->GetObjectClass(params);

    jfieldID widthField = env->GetFieldID(claxx, "width", "I");
    jfieldID heightField = env->GetFieldID(claxx, "height", "I");

    env->SetIntField(params, widthField, width);
    env->SetIntField(params, heightField, height);
    

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_VideoStreamProfile_nGetIntrinsic(JNIEnv* env, jclass clazz, jlong handle, jobject intrinsic) {


//@line:60

    rs2_error* e = nullptr;
    rs2_intrinsics intr;
    rs2_get_video_stream_intrinsics(reinterpret_cast<const rs2_stream_profile *>(handle), &intr, &e);
    handle_error(env, e);

    if (e != nullptr)
    {
        return;
    }

    jclass claxx = env->GetObjectClass(intrinsic);

    //retrieving all the built-in types members
    jfieldID width_field = env->GetFieldID(claxx, "mWidth", "I");
    jfieldID height_field = env->GetFieldID(claxx, "mHeight", "I");
    jfieldID ppx_field = env->GetFieldID(claxx, "mPpx", "F");
    jfieldID ppy_field = env->GetFieldID(claxx, "mPpy", "F");
    jfieldID fx_field = env->GetFieldID(claxx, "mFx", "F");
    jfieldID fy_field = env->GetFieldID(claxx, "mFy", "F");
    jfieldID model_field = env->GetFieldID(claxx, "mModelValue", "I");


    env->SetIntField(intrinsic, width_field, intr.width);
    env->SetIntField(intrinsic, height_field, intr.height);
    env->SetFloatField(intrinsic, ppx_field, intr.ppx);
    env->SetFloatField(intrinsic, ppy_field, intr.ppy);
    env->SetFloatField(intrinsic, fx_field, intr.fx);
    env->SetFloatField(intrinsic, fy_field, intr.fy);
    env->SetIntField(intrinsic, model_field, intr.model);

    //retrieving the array member
    jfieldID coeff_field = env->GetFieldID(claxx, "mCoeffs", "[F");
    jfloatArray coeffsArray = env->NewFloatArray(5);
    if (coeffsArray != NULL)
    {
        env->SetFloatArrayRegion(coeffsArray, 0, 5, reinterpret_cast<jfloat*>(&intr.coeffs));
    }
    env->SetObjectField(intrinsic, coeff_field, coeffsArray);
     

}

