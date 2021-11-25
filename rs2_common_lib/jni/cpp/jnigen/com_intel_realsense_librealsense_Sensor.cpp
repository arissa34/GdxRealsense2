#include <com_intel_realsense_librealsense_Sensor.h>

//@line:105

    #include <jni.h>
    #include <memory>
    #include <vector>
    #include <error.h>

    #include <librealsense2/rs.h>

    #include <jni_logging.h>
    #include <frame_callback.h>

    static frame_callback_data sdata = {NULL, 0, JNI_FALSE, NULL, NULL};
     JNIEXPORT jlongArray JNICALL Java_com_intel_realsense_librealsense_Sensor_nGetStreamProfiles(JNIEnv* env, jclass clazz, jlong handle) {


//@line:119

    rs2_error* e = nullptr;
    std::shared_ptr<rs2_stream_profile_list> list(
            rs2_get_stream_profiles(reinterpret_cast<rs2_sensor *>(handle), &e),
            rs2_delete_stream_profiles_list);
    handle_error(env, e);

    auto size = rs2_get_stream_profiles_count(list.get(), &e);
    handle_error(env, e);

    std::vector<const rs2_stream_profile*> profiles;

    for (auto i = 0; i < size; i++)
    {
        auto sp = rs2_get_stream_profile(list.get(), i, &e);
        handle_error(env, e);
        profiles.push_back(sp);
    }

    // jlong is 64-bit, but pointer in profiles could be 32-bit, copy element by element
    jlongArray rv = env->NewLongArray(profiles.size());
    for (auto i = 0; i < size; i++)
    {
        env->SetLongArrayRegion(rv, i, 1, reinterpret_cast<const jlong *>(&profiles[i]));
    }
    return rv;
    

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Sensor_nRelease(JNIEnv* env, jclass clazz, jlong handle) {


//@line:146

    rs2_delete_sensor(reinterpret_cast<rs2_sensor *>(handle));
     

}

JNIEXPORT jboolean JNICALL Java_com_intel_realsense_librealsense_Sensor_nIsSensorExtendableTo(JNIEnv* env, jclass clazz, jlong handle, jint extension) {


//@line:149

    rs2_error *e = NULL;
    int rv = rs2_is_sensor_extendable_to(reinterpret_cast<const rs2_sensor *>(handle),
                                         static_cast<rs2_extension>(extension), &e);
    handle_error(env, e);
    return rv > 0;
     

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Sensor_nOpen(JNIEnv* env, jclass clazz, jlong handle, jlong sp) {


//@line:156

    rs2_error* e = nullptr;
    rs2_open(reinterpret_cast<rs2_sensor *>(handle), reinterpret_cast<rs2_stream_profile *>(sp), &e);
    handle_error(env, e);
     

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Sensor_nOpenMultiple(JNIEnv* env, jclass clazz, jlong device_handle, jlongArray obj_profiles, jint num_of_profiles) {
	long long* profiles = (long long*)env->GetPrimitiveArrayCritical(obj_profiles, 0);


//@line:161

    // building C++ profiles vector
    rs2_error* e = nullptr;
    std::vector<const rs2_stream_profile*> profs;
    profs.reserve(num_of_profiles);
    for (int i = 0; i < num_of_profiles; ++i)
    {
        auto p = reinterpret_cast<const rs2_stream_profile*>(profiles[i]);
        profs.push_back(p);
    }
    // API call
    rs2_open_multiple(reinterpret_cast<rs2_sensor *>(device_handle),
                      profs.data(), static_cast<int>(num_of_profiles), &e);
    handle_error(env, e);
     
	env->ReleasePrimitiveArrayCritical(obj_profiles, profiles, 0);

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Sensor_nStart(JNIEnv* env, jclass clazz, jlong handle, jobject jcb) {


//@line:176

    rs2_error* e = nullptr;

    if (rs_jni_callback_init(env, jcb, &sdata) != true) return;

    auto cb = [&](rs2::frame f) {
        rs_jni_cb(f, &sdata);
    };

    rs2_start_cpp(reinterpret_cast<rs2_sensor *>(handle), new rs2::frame_callback<decltype(cb)>(cb), &e);
    handle_error(env, e);
     

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Sensor_nStop(JNIEnv* env, jclass clazz, jlong handle) {


//@line:188

    rs2_error* e = nullptr;
    rs_jni_cleanup(env, &sdata);
    rs2_stop(reinterpret_cast<rs2_sensor *>(handle), &e);
    handle_error(env, e);
     

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Sensor_nClose(JNIEnv* env, jclass clazz, jlong handle) {


//@line:194

    rs2_error* e = nullptr;
    rs2_close(reinterpret_cast<rs2_sensor *>(handle), &e);
    handle_error(env, e);
     

}

