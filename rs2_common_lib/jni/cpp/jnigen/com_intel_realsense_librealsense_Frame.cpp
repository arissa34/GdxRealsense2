#include <com_intel_realsense_librealsense_FrameSet.h>
#include <com_intel_realsense_librealsense_FrameQueue.h>
#include <com_intel_realsense_librealsense_Frame.h>

//@line:75

    #include <jni.h>
    #include <error.h>
    #include <librealsense2/rs.h>
     JNIEXPORT jboolean JNICALL Java_com_intel_realsense_librealsense_Frame_nIsFrameExtendableTo(JNIEnv* env, jclass clazz, jlong handle, jint extension) {


//@line:81

    rs2_error *e = NULL;
    int rv = rs2_is_frame_extendable_to(reinterpret_cast<const rs2_frame *>(handle),
                                        static_cast<rs2_extension>(extension), &e);
    handle_error(env, e);
    return rv;

    

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Frame_nAddRef(JNIEnv* env, jclass clazz, jlong handle) {


//@line:89

    rs2_error *e = NULL;
    rs2_frame_add_ref((rs2_frame *) handle, &e);
    handle_error(env, e);
     

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Frame_nRelease(JNIEnv* env, jclass clazz, jlong handle) {


//@line:94

    if (handle)
        rs2_release_frame((rs2_frame *) handle);
     

}

JNIEXPORT jlong JNICALL Java_com_intel_realsense_librealsense_Frame_nGetStreamProfile(JNIEnv* env, jclass clazz, jlong handle) {


//@line:98

    rs2_error *e = NULL;
    const rs2_stream_profile *rv = rs2_get_frame_stream_profile(
            reinterpret_cast<const rs2_frame *>(handle), &e);
    handle_error(env, e);
    return (jlong) rv;
     

}

JNIEXPORT jint JNICALL Java_com_intel_realsense_librealsense_Frame_nGetDataSize(JNIEnv* env, jclass clazz, jlong handle) {


//@line:105

    rs2_error *e = NULL;
    auto rv = rs2_get_frame_data_size(reinterpret_cast<const rs2_frame *>(handle), &e);
    handle_error(env, e);
    return (jint)rv;
     

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Frame_nGetData(JNIEnv* env, jclass clazz, jlong handle, jbyteArray obj_data) {
	char* data = (char*)env->GetPrimitiveArrayCritical(obj_data, 0);


//@line:111

    jsize length = env->GetArrayLength(obj_data);
    rs2_error *e = NULL;
    env->SetByteArrayRegion(obj_data, 0, length, static_cast<const jbyte *>(rs2_get_frame_data(reinterpret_cast<const rs2_frame *>(handle), &e)));
    handle_error(env, e);
     
	env->ReleasePrimitiveArrayCritical(obj_data, data, 0);

}

JNIEXPORT jint JNICALL Java_com_intel_realsense_librealsense_Frame_nGetNumber(JNIEnv* env, jclass clazz, jlong handle) {


//@line:117

    rs2_error *e = NULL;
    unsigned long long rv = rs2_get_frame_number(reinterpret_cast<const rs2_frame *>(handle), &e);
    handle_error(env, e);
    return rv;
     

}

JNIEXPORT jdouble JNICALL Java_com_intel_realsense_librealsense_Frame_nGetTimestamp(JNIEnv* env, jclass clazz, jlong handle) {


//@line:123

    rs2_error *e = NULL;
    double rv = rs2_get_frame_timestamp(reinterpret_cast<const rs2_frame *>(handle), &e);
    handle_error(env, e);
    return rv;
     

}

JNIEXPORT jint JNICALL Java_com_intel_realsense_librealsense_Frame_nGetTimestampDomain(JNIEnv* env, jclass clazz, jlong handle) {


//@line:129

    rs2_error *e = NULL;
    int rv = rs2_get_frame_timestamp_domain(reinterpret_cast<const rs2_frame *>(handle), &e);
    handle_error(env, e);
    return rv;
     

}

JNIEXPORT jlong JNICALL Java_com_intel_realsense_librealsense_Frame_nGetMetadata(JNIEnv* env, jclass clazz, jlong handle, jint metadata_type) {


//@line:135

    rs2_error *e = NULL;
    long rv = rs2_get_frame_metadata(reinterpret_cast<const rs2_frame *>(handle),
                                     static_cast<rs2_frame_metadata_value>(metadata_type), &e);
    handle_error(env, e);
    return rv;
     

}

JNIEXPORT jboolean JNICALL Java_com_intel_realsense_librealsense_Frame_nSupportsMetadata(JNIEnv* env, jclass clazz, jlong handle, jint metadata_type) {


//@line:142

    rs2_error *e = NULL;
    int rv = rs2_supports_frame_metadata(reinterpret_cast<const rs2_frame *>(handle),
                                     static_cast<rs2_frame_metadata_value>(metadata_type), &e);
    handle_error(env, e);
    return rv > 0;
     

}

