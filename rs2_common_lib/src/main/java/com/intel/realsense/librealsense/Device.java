package com.intel.realsense.librealsense;

import java.util.ArrayList;
import java.util.List;

public class Device extends LrsClass {
    private List<Sensor> _sensors = new ArrayList<>();

    Device(long handle){
        mHandle = handle;
        long[] sensorsHandles = nQuerySensors(mHandle);
        for(long h : sensorsHandles){
            _sensors.add(new Sensor(h));
        }
    }

    public List<Sensor> querySensors(){
        return _sensors;
    }

    public boolean supportsInfo(CameraInfo info){
        return nSupportsInfo(mHandle, info.value());
    }

    public String getInfo(CameraInfo info){
        return nGetInfo(mHandle, info.value());
    }

    public void toggleAdvancedMode(boolean enable){
        nToggleAdvancedMode(mHandle, enable);
    }

    public boolean isInAdvancedMode(){
        return nIsInAdvancedMode(mHandle);
    }

    public void loadPresetFromJson(byte[] data){
        nLoadPresetFromJson(mHandle, data);
    }

    public byte[] serializePresetToJson(){
        return nSerializePresetToJson(mHandle);
    }

    public boolean is(Extension extension) {
        return nIsDeviceExtendableTo(mHandle, extension.value());
    }

    public <T extends Device> T as(Extension extension) {
        switch (extension){
            case UPDATABLE: return (T) new Updatable(mHandle);
            case UPDATE_DEVICE: return (T) new UpdateDevice(mHandle);
            case DEBUG: return (T) new DebugProtocol(mHandle);
            case FW_LOGGER: return (T) new FwLogger(mHandle);
        }
        throw new RuntimeException("this device is not extendable to " + extension.name());
    }

    public void hardwareReset(){
        nHardwareReset(mHandle);
    }

    @Override
    public void close() {
        for (Sensor s : _sensors)
            s.close();
        if(mOwner)
            nRelease(mHandle);
    }

    /*JNI
    #include <jni.h>
    #include <memory>
    #include <vector>
    #include <error.h>

    #include <librealsense2/rs.h>
    #include <librealsense2/hpp/rs_device.hpp>
    #include <librealsense2/rs_advanced_mode.h>
     */

    private static native boolean nSupportsInfo(long handle, int info);/*
    rs2_error *e = NULL;
    auto rv = rs2_supports_device_info(reinterpret_cast<const rs2_device *>(handle),
                                       static_cast<rs2_camera_info>(info), &e);
    handle_error(env, e);
    return rv > 0;
    */
    private static native String nGetInfo(long handle, int info);/*
    rs2_error *e = NULL;
    const char* rv = rs2_get_device_info(reinterpret_cast<const rs2_device *>(handle),
                                         static_cast<rs2_camera_info>(info), &e);
    handle_error(env, e);
    if (NULL == rv)
        rv = "";
    return env->NewStringUTF(rv);
     */
    private static native void nToggleAdvancedMode(long handle, boolean enable);/*
    rs2_error* e = NULL;
    rs2_toggle_advanced_mode(reinterpret_cast<rs2_device *>(handle), enable, &e);
    handle_error(env, e);
     */
    private static native boolean nIsInAdvancedMode(long handle);/*

     */
    private static native void nLoadPresetFromJson(long handle, byte[] data);/*
     */
    private static native byte[] nSerializePresetToJson(long handle);/*
     */
    private static native long[] nQuerySensors(long handle);/*
    rs2_error* e = nullptr;
    std::shared_ptr<rs2_sensor_list> list(
            rs2_query_sensors(reinterpret_cast<const rs2_device *>(handle), &e),
            rs2_delete_sensor_list);
    handle_error(env, e);

    auto size = rs2_get_sensors_count(list.get(), &e);
    handle_error(env, e);

    std::vector<rs2_sensor*> sensors;
    for (auto i = 0; i < size; i++)
    {
        auto s = rs2_create_sensor(list.get(), i, &e);
        handle_error(env, e);
        sensors.push_back(s);
    }
    jlongArray rv = env->NewLongArray(sensors.size());

    for (auto i = 0; i < sensors.size(); i++)
    {
        env->SetLongArrayRegion(rv, i, 1, reinterpret_cast<const jlong *>(&sensors[i]));
    }

    return rv;
     */
    private static native void nHardwareReset(long handle);/*
    rs2_error *e = NULL;
    rs2_hardware_reset(reinterpret_cast<const rs2_device *>(handle), &e);
    handle_error(env, e);
     */
    private static native boolean nIsDeviceExtendableTo(long handle, int extension);/*
     */
    private static native void nRelease(long handle);/*
    rs2_delete_device(reinterpret_cast<rs2_device *>(handle));
     */
}
