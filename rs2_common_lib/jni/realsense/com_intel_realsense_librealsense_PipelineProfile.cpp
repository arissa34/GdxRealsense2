#include <com_intel_realsense_librealsense_PipelineProfile.h>

//@line:18

    #include <jni.h>
    #include "include/error.h"
    #include "include/librealsense2/rs.h"
    #include "include/librealsense2/h/rs_pipeline.h"
    #include "include/jni_logging.h"
    #include "include/frame_callback.h"
     JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_PipelineProfile_nDelete(JNIEnv* env, jclass clazz, jlong handle) {


//@line:27

    rs2_delete_pipeline_profile(reinterpret_cast<rs2_pipeline_profile *>(handle));
    

}

JNIEXPORT jlong JNICALL Java_com_intel_realsense_librealsense_PipelineProfile_nGetDevice(JNIEnv* env, jclass clazz, jlong handle) {


//@line:30

    rs2_error* e = NULL;
    rs2_device *rv = rs2_pipeline_profile_get_device(reinterpret_cast<rs2_pipeline_profile *>(handle), &e);
    handle_error(env, e);
    return reinterpret_cast<jlong>(rv);
     

}

