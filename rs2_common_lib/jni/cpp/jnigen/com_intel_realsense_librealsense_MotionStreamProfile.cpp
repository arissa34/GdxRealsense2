#include <com_intel_realsense_librealsense_MotionStreamProfile.h>

//@line:21

    #include <jni.h>
    #include <cstring>      //for memcpy
    #include <error.h>
    #include <librealsense2/rs.h>
     JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_MotionStreamProfile_nGetIntrinsic(JNIEnv* env, jclass clazz, jlong handle, jobject intrinsic) {


//@line:28

    rs2_error* e = nullptr;
    rs2_motion_device_intrinsic intr;
    rs2_get_motion_intrinsics(reinterpret_cast<const rs2_stream_profile *>(handle), &intr, &e);
    handle_error(env, e);

    if (e != nullptr)
    {
        return;
    }

    jclass claxx = env->GetObjectClass(intrinsic);

    //retrieving the array members
    //mData
    jfieldID data_field = env->GetFieldID(claxx, "mData", "[F");
    jfloatArray dataArray = env->NewFloatArray(12);
    if (dataArray != NULL)
    {
        env->SetFloatArrayRegion(dataArray, 0, 12, reinterpret_cast<jfloat*>(&intr.data));
    }
    env->SetObjectField(intrinsic, data_field, dataArray);

    //mNoiseVariances
    jfieldID noise_field = env->GetFieldID(claxx, "mNoiseVariances", "[F");
    jfloatArray noiseArray = env->NewFloatArray(3);
    if (noiseArray != NULL)
    {
        env->SetFloatArrayRegion(noiseArray, 0, 3, reinterpret_cast<jfloat*>(&intr.noise_variances));
    }
    env->SetObjectField(intrinsic, noise_field, noiseArray);

    //mBiasVariances
    jfieldID bias_field = env->GetFieldID(claxx, "mBiasVariances", "[F");
    jfloatArray biasArray = env->NewFloatArray(3);
    if (biasArray != NULL)
    {
        env->SetFloatArrayRegion(biasArray, 0, 3, reinterpret_cast<jfloat*>(&intr.bias_variances));
    }
    env->SetObjectField(intrinsic, bias_field, biasArray);
    

}

