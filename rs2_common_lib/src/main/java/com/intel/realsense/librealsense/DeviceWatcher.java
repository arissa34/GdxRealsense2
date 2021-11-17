package com.intel.realsense.librealsense;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class DeviceWatcher extends LrsClass implements AutoCloseable, DeviceListener{
    private static final String TAG = "librs DeviceWatcher";

    private final List<DeviceListener> mAppDeviceListener;

    DeviceWatcher(){
        mAppDeviceListener = new ArrayList<>();
    }

    public synchronized void addListener(DeviceListener deviceListener){
        if(!mAppDeviceListener.contains(deviceListener)) {
            mAppDeviceListener.add(deviceListener);
        }
    }

    public synchronized void removeListener(DeviceListener deviceListener){
        mAppDeviceListener.remove(deviceListener);
    }

    protected HashMap<String, UsbDesc> mDescriptors = new LinkedHashMap<>();
    public int getDeviceCount() {
        return mDescriptors.size();
    }

    @Override
    public void onDeviceAttach() {
        invalidateDevices();
    }

    @Override
    public void onDeviceDetach() {
        invalidateDevices();
    }

    protected abstract void invalidateDevices();

    protected enum UpdateListenersType {
        ATTACH,
        DETACH
    }

    protected void updateListeners(UpdateListenersType type){
        for(DeviceListener listener : mAppDeviceListener) {
            try {
                if (type == UpdateListenersType.ATTACH)
                    listener.onDeviceAttach();
                else
                    listener.onDeviceDetach();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    protected abstract void removeDevice(UsbDesc desc);

    protected static native void nAddUsbDevice(String deviceName, int fileDescriptor);

    protected static native void nRemoveUsbDevice(int fileDescriptor);

    @Override
    public void close() {
        Iterator<Map.Entry<String, UsbDesc>> iter = mDescriptors.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, UsbDesc> entry = iter.next();
            removeDevice(entry.getValue());
            iter.remove();
        }
    }
}
