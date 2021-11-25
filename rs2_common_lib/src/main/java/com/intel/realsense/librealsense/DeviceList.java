package com.intel.realsense.librealsense;

public class DeviceList extends LrsClass {
    private static final String TAG = "librs api";

    public DeviceList(long handle) {
        this.mHandle = handle;
    }

    public int getDeviceCount() {
        return nGetDeviceCount(this.mHandle);
    }

    public Device createDevice(int index) {
        try {
            long deviceHandle = nCreateDevice(this.mHandle, index);
            return new Device(deviceHandle);
        } catch (Exception var4) {
            System.out.println("librs api : "+ var4.getMessage());
            return null;
        }
    }

    public boolean containesDevice(Device device) {
        return nContainsDevice(this.mHandle, device.getHandle());
    }

    public void foreach(DeviceCallback callback) {
        int size = this.getDeviceCount();

        for(int i = 0; i < size; ++i) {
            Device d = this.createDevice(i);
            Throwable var5 = null;

            try {
                callback.onDevice(d);
            } catch (Throwable var14) {
                var5 = var14;
                throw var14;
            } finally {
                if (d != null) {
                    if (var5 != null) {
                        try {
                            d.close();
                        } catch (Throwable var13) {
                            var5.addSuppressed(var13);
                        }
                    } else {
                        d.close();
                    }
                }

            }
        }

    }

    public void close() {
        nRelease(this.mHandle);
    }

    /*JNI
    #include <error.h>
    #include <librealsense2/rs.h>
     */

    private static native int nGetDeviceCount(long handle);/*
    rs2_error *e = NULL;
    auto rv = rs2_get_device_count(reinterpret_cast<const rs2_device_list *>(handle), &e);
    handle_error(env, e);
    return rv;
    */

    private static native long nCreateDevice(long handle, int index);/*
    rs2_error *e = NULL;
    rs2_device* rv = rs2_create_device(reinterpret_cast<const rs2_device_list *>(handle), index, &e);
    handle_error(env, e);
    return (jlong)rv;
    */

    private static native boolean nContainsDevice(long handle, long deviceHandle);/*
    rs2_error *e = NULL;
    auto rv = rs2_device_list_contains(reinterpret_cast<const rs2_device_list *>(handle),
                                       reinterpret_cast<const rs2_device *>(deviceHandle), &e);
    handle_error(env, e);
    return rv > 0;
    */

    private static native void nRelease(long handle);/*
    rs2_delete_device_list((rs2_device_list *) handle);
    */
}
