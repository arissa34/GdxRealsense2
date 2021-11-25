package com.intel.realsense.librealsense;

public abstract class ProcessingBlock extends Options implements ProcessingBlockInterface {

    /*JNI
    #include <jni.h>
    #include <error.h>

    #include <librealsense2/rs.h>
     */

    private static native void nDelete(long handle);/*
    rs2_delete_processing_block(reinterpret_cast<rs2_processing_block *>(handle));
    */

    @Override
    public void invoke(Frame original) {
        nInvoke(mHandle, original.getHandle());
    }

    @Override
    public void invoke(FrameSet original) {
        nInvoke(mHandle, original.getHandle());
    }

    @Override
    public void close() {
        nDelete(mHandle);
    }

    protected static native void nInvoke(long handle, long frameHandle);/*
    rs2_error *e = NULL;
    rs2_frame_add_ref(reinterpret_cast<rs2_frame *>(frameHandle), &e);
    handle_error(env, e);
    rs2_process_frame(reinterpret_cast<rs2_processing_block *>(handle),
                      reinterpret_cast<rs2_frame *>(frameHandle), &e);
    handle_error(env, e);
    */
}
