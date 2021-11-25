#include <com_intel_realsense_librealsense_ProcessingBlock.h>

//@line:5

    #include <jni.h>
    #include <error.h>

    #include <librealsense2/rs.h>
     JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_ProcessingBlock_nDelete(JNIEnv* env, jclass clazz, jlong handle) {


//@line:12

    rs2_delete_processing_block(reinterpret_cast<rs2_processing_block *>(handle));
    

}

JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_ProcessingBlock_nInvoke(JNIEnv* env, jclass clazz, jlong handle, jlong frameHandle) {


//@line:31

    rs2_error *e = NULL;
    rs2_frame_add_ref(reinterpret_cast<rs2_frame *>(frameHandle), &e);
    handle_error(env, e);
    rs2_process_frame(reinterpret_cast<rs2_processing_block *>(handle),
                      reinterpret_cast<rs2_frame *>(frameHandle), &e);
    handle_error(env, e);
    

}

