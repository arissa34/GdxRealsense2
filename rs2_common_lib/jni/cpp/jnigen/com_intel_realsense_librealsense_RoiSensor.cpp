#include <com_intel_realsense_librealsense_RoiSensor.h>

//@line:24

    #include <jni.h>
    #include <error.h>

    #include <librealsense2/rs.h>
     JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_RoiSensor_nSetRegionOfInterest(JNIEnv* env, jclass clazz, jlong handle, jint minX, jint minY, jint maxX, jint maxY) {


//@line:31

    rs2_error* e = nullptr;
    rs2_set_region_of_interest(reinterpret_cast<rs2_sensor *>(handle), minX, minY, maxX, maxY, &e);
    handle_error(env, e);
    

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_RoiSensor_nGetRegionOfInterest(JNIEnv* env, jclass clazz, jlong handle, jobject roi) {


//@line:36

    int min_x, min_y, max_x, max_y;
    rs2_error *e = nullptr;
    rs2_get_region_of_interest(reinterpret_cast<rs2_sensor *>(handle), &min_x, &min_y, &max_x, &max_y, &e);
    handle_error(env, e);

    if(e)
        return;

    jclass claxx = env->GetObjectClass(roi);

    jfieldID min_x_field = env->GetFieldID(claxx, "minX", "I");
    jfieldID min_y_field = env->GetFieldID(claxx, "minY", "I");
    jfieldID max_x_field = env->GetFieldID(claxx, "maxX", "I");
    jfieldID max_y_field = env->GetFieldID(claxx, "maxY", "I");

    env->SetIntField(roi, min_x_field, min_x);
    env->SetIntField(roi, min_y_field, min_y);
    env->SetIntField(roi, max_x_field, max_x);
    env->SetIntField(roi, max_y_field, max_y);
    

}

