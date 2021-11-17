#include <com_intel_realsense_librealsense_Pipeline.h>
#include <com_intel_realsense_librealsense_PipelineProfile.h>

//@line:51

    #include <jni.h>
    #include "include/error.h"
    #include "include/librealsense2/rs.h"
    #include "include/librealsense2/h/rs_pipeline.h"
    #include "include/frame_callback.h"

    static frame_callback_data pdata = {NULL, 0, JNI_FALSE, NULL, NULL};
     JNIEXPORT jlong JNICALL Java_com_intel_realsense_librealsense_Pipeline_nCreate(JNIEnv* env, jclass clazz, jlong context) {


//@line:61

    rs2_error* e = NULL;
    rs2_pipeline* rv = rs2_create_pipeline(reinterpret_cast<rs2_context *>(context), &e);
    handle_error(env, e);
    return reinterpret_cast<jlong>(rv);
    

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Pipeline_nDelete(JNIEnv* env, jclass clazz, jlong handle) {


//@line:67

    rs2_delete_pipeline(reinterpret_cast<rs2_pipeline *>(handle));
     

}

JNIEXPORT jlong JNICALL Java_com_intel_realsense_librealsense_Pipeline_nStart(JNIEnv* env, jclass clazz, jlong handle) {


//@line:70

    rs2_error* e = NULL;
    auto rv = rs2_pipeline_start(reinterpret_cast<rs2_pipeline *>(handle), &e);
    handle_error(env, e);
    return reinterpret_cast<jlong>(rv);
     

}

JNIEXPORT jlong JNICALL Java_com_intel_realsense_librealsense_Pipeline_nStartWithCallback(JNIEnv* env, jclass clazz, jlong handle, jobject jcb) {


//@line:76

    rs2_error* e = NULL;

    if (rs_jni_callback_init(env, jcb, &pdata) != true) return NULL;

    auto cb = [&](rs2::frame f) {
        rs_jni_cb(f, &pdata);
    };

    auto rv = rs2_pipeline_start_with_callback_cpp(reinterpret_cast<rs2_pipeline *>(handle), new rs2::frame_callback<decltype(cb)>(cb), &e);
    handle_error(env, e);
    return reinterpret_cast<jlong>(rv);
     

}

JNIEXPORT jlong JNICALL Java_com_intel_realsense_librealsense_Pipeline_nStartWithConfig(JNIEnv* env, jclass clazz, jlong handle, jlong configHandle) {


//@line:89

    rs2_error *e = NULL;
    auto rv = rs2_pipeline_start_with_config(reinterpret_cast<rs2_pipeline *>(handle),
                                             reinterpret_cast<rs2_config *>(configHandle), &e);
    handle_error(env, e);
    return reinterpret_cast<jlong>(rv);
     

}

JNIEXPORT jlong JNICALL Java_com_intel_realsense_librealsense_Pipeline_nStartWithConfigAndCallback(JNIEnv* env, jclass clazz, jlong handle, jlong configHandle, jobject jcb) {


//@line:96

    rs2_error *e = NULL;

    if (rs_jni_callback_init(env, jcb, &pdata) != true) return NULL;

    auto cb = [&](rs2::frame f) {
        rs_jni_cb(f, &pdata);
    };

    auto rv = rs2_pipeline_start_with_config_and_callback_cpp(reinterpret_cast<rs2_pipeline *>(handle),
                                             reinterpret_cast<rs2_config *>(configHandle), new rs2::frame_callback<decltype(cb)>(cb), &e);
    handle_error(env, e);
    return reinterpret_cast<jlong>(rv);
     

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Pipeline_nStop(JNIEnv* env, jclass clazz, jlong handle) {


//@line:110

    rs2_error* e = NULL;
    rs_jni_cleanup(env, &pdata);
    rs2_pipeline_stop(reinterpret_cast<rs2_pipeline *>(handle), &e);
    handle_error(env, e);
     

}

JNIEXPORT jlong JNICALL Java_com_intel_realsense_librealsense_Pipeline_nWaitForFrames(JNIEnv* env, jclass clazz, jlong handle, jint timeout) {


//@line:116

    rs2_error* e = NULL;
    rs2_frame *rv = rs2_pipeline_wait_for_frames(reinterpret_cast<rs2_pipeline *>(handle), timeout, &e);
    handle_error(env, e);
    return reinterpret_cast<jlong>(rv);
     

}

