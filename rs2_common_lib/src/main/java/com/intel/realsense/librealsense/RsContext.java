package com.intel.realsense.librealsense;

public class RsContext extends LrsClass{
    private static DeviceWatcher mDeviceWatcher;
    private DeviceListener mListener;

    public static void init(DeviceWatcher deviceWatcher){
        if(mDeviceWatcher == null)
            mDeviceWatcher = deviceWatcher;
    }

    public static String getVersion(){
        return nGetVersion();
    }

    public DeviceList queryDevices() {
        return queryDevices(ProductLine.ANY_INTEL);
    }

    public DeviceList queryDevices(ProductLine productLine) {
        return new DeviceList(nQueryDevices(mHandle, productLine.value()));
    }

    public synchronized void setDevicesChangedCallback(DeviceListener listener) {
        removeDevicesChangedCallback();
        mListener = listener;
        if(mDeviceWatcher != null)
            mDeviceWatcher.addListener(mListener);
    }

    public synchronized void removeDevicesChangedCallback() {
        if(mListener != null && mDeviceWatcher != null)
            mDeviceWatcher.removeListener(mListener);
    }

    public RsContext() {
        mHandle = nCreate();
    }

    @Override
    public void close() {
        removeDevicesChangedCallback();
        nDelete(mHandle);
    }

    /*JNI
    #include <error.h>
    #include <librealsense2/rs.h>
    #include <librealsense2/hpp/rs_context.hpp>
     */

    private static native long nCreate(); /*
    rs2_error* e = NULL;
    rs2_context* handle = rs2_create_context(RS2_API_VERSION, &e);
    handle_error(env, e);
    return (jlong) handle;
    */
    private static native String nGetVersion();/*
    return env->NewStringUTF(RS2_API_VERSION_STR);
    */
    private static native long nQueryDevices(long handle, int mask);/*
    rs2_error* e = NULL;
    rs2_device_list* device_list_handle = rs2_query_devices_ex((rs2_context *) handle, mask, &e);
    handle_error(env, e);
    return (jlong) device_list_handle;
    */
    private static native void nDelete(long handle);/*
    rs2_delete_context((rs2_context *) handle);
    */
}
