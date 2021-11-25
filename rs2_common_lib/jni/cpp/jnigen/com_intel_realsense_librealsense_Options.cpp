#include <com_intel_realsense_librealsense_Options.h>

//@line:75

    #include <jni.h>
    #include <error.h>

    #include <librealsense2/rs.h>
     JNIEXPORT jboolean JNICALL Java_com_intel_realsense_librealsense_Options_nSupports(JNIEnv* env, jclass clazz, jlong handle, jint option) {


//@line:82

    rs2_error* e = NULL;
    auto rv = rs2_supports_option((const rs2_options *) handle, (rs2_option) option, &e);
    handle_error(env, e);
    return rv > 0;
    

}

JNIEXPORT jfloat JNICALL Java_com_intel_realsense_librealsense_Options_nGetValue(JNIEnv* env, jclass clazz, jlong handle, jint option) {


//@line:88

    rs2_error* e = NULL;
    float rv = rs2_get_option(reinterpret_cast<const rs2_options *>(handle),
                              static_cast<rs2_option>(option), &e);
    handle_error(env, e);
    return rv;
     

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Options_nSetValue(JNIEnv* env, jclass clazz, jlong handle, jint option, jfloat value) {


//@line:95

    rs2_error* e = NULL;
    rs2_set_option(reinterpret_cast<const rs2_options *>(handle), static_cast<rs2_option>(option), value, &e);
    handle_error(env, e);
     

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Options_nGetRange(JNIEnv* env, jclass clazz, jlong handle, jint option, jobject outParams) {


//@line:100

    float min = -1;
    float max = -1;
    float step = -1;
    float def = -1;
    rs2_error *e = NULL;
    jclass claxx = env->GetObjectClass(outParams);

    rs2_get_option_range(reinterpret_cast<const rs2_options *>(handle),
                         static_cast<rs2_option>(option), &min, &max, &step, &def, &e);
    handle_error(env, e);

    if(e)
        return;

    jfieldID minField = env->GetFieldID(claxx, "min", "F");
    jfieldID maxField = env->GetFieldID(claxx, "max", "F");
    jfieldID stepField = env->GetFieldID(claxx, "step", "F");
    jfieldID defField = env->GetFieldID(claxx, "def", "F");

    env->SetFloatField(outParams, minField, min);
    env->SetFloatField(outParams, maxField, max);
    env->SetFloatField(outParams, stepField, step);
    env->SetFloatField(outParams, defField, def);
     

}

JNIEXPORT jboolean JNICALL Java_com_intel_realsense_librealsense_Options_nIsReadOnly(JNIEnv* env, jclass clazz, jlong handle, jint option) {


//@line:125

    rs2_error* e = NULL;
    int rv = rs2_is_option_read_only(reinterpret_cast<const rs2_options *>(handle),
                                     static_cast<rs2_option>(option), &e);
    handle_error(env, e);
    return rv > 0;
     

}

JNIEXPORT jstring JNICALL Java_com_intel_realsense_librealsense_Options_nGetDescription(JNIEnv* env, jclass clazz, jlong handle, jint option) {


//@line:132

    rs2_error* e = NULL;
    const char *rv = rs2_get_option_description(reinterpret_cast<const rs2_options *>(handle),
                                                static_cast<rs2_option>(option), &e);
    handle_error(env, e);
    return env->NewStringUTF(rv);
     

}

JNIEXPORT jstring JNICALL Java_com_intel_realsense_librealsense_Options_nGetValueDescription(JNIEnv* env, jclass clazz, jlong handle, jint option, jfloat value) {


//@line:139

    rs2_error* e = NULL;
    const char *rv = rs2_get_option_value_description(reinterpret_cast<const rs2_options *>(handle),
                                                      static_cast<rs2_option>(option), value, &e);
    handle_error(env, e);
    return env->NewStringUTF(rv);
     

}

