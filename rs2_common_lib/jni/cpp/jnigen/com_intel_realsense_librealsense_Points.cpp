#include <com_intel_realsense_librealsense_Points.h>

//@line:44

    #include <jni.h>
    #include <error.h>
    #include <librealsense2/rs.h>
     JNIEXPORT jint JNICALL Java_com_intel_realsense_librealsense_Points_nGetCount(JNIEnv* env, jclass clazz, jlong handle) {


//@line:50

    rs2_error *e = NULL;
    int rv = rs2_get_frame_points_count(reinterpret_cast<const rs2_frame *>(handle), &e);
    handle_error(env, e);
    return rv;
    

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Points_nGetData(JNIEnv* env, jclass clazz, jlong handle, jfloatArray obj_data_) {
	float* data_ = (float*)env->GetPrimitiveArrayCritical(obj_data_, 0);


//@line:57

    jsize length = (jsize)env->GetArrayLength(obj_data_);
    rs2_error *e = NULL;
    env->SetFloatArrayRegion(obj_data_, 0, length, static_cast<const jfloat *>(rs2_get_frame_data(
            reinterpret_cast<const rs2_frame *>(handle), &e)));
    handle_error(env, e);
     
	env->ReleasePrimitiveArrayCritical(obj_data_, data_, 0);

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Points_nGetTextureCoordinates(JNIEnv* env, jclass clazz, jlong handle, jfloatArray obj_data_) {
	float* data_ = (float*)env->GetPrimitiveArrayCritical(obj_data_, 0);


//@line:65

    jsize length = (jsize)env->GetArrayLength(obj_data_);
    rs2_error *e = NULL;
    env->SetFloatArrayRegion(obj_data_, 0, length, reinterpret_cast<const jfloat *>(rs2_get_frame_texture_coordinates(
            reinterpret_cast<const rs2_frame *>(handle), &e)));
    handle_error(env, e);
     
	env->ReleasePrimitiveArrayCritical(obj_data_, data_, 0);

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Points_nExportToPly(JNIEnv* env, jclass clazz, jlong handle, jstring obj_filePath, jlong textureHandle) {
	char* filePath = (char*)env->GetStringUTFChars(obj_filePath, 0);


//@line:73

    rs2_error *e = NULL;
    rs2_export_to_ply(reinterpret_cast<const rs2_frame *>(handle), filePath,
                      reinterpret_cast<rs2_frame *>(textureHandle), &e);
    handle_error(env, e);
     
	env->ReleaseStringUTFChars(obj_filePath, filePath);

}

