package com.intel.realsense.librealsense;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;

import com.badlogic.gdx.utils.SharedLibraryLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DeviceWatcherAndroid extends DeviceWatcher {
    private static final String TAG = "librs DeviceWatcher";

    private final Context mContext;
    private final Enumerator mEnumerator;

    private HashMap<String, UsbDescAndroid> mDescriptors = new LinkedHashMap<>();


    public DeviceWatcherAndroid(Context context){
        super();
        mContext = context;
        mEnumerator = new Enumerator(mContext, this);
    }

    protected synchronized void invalidateDevices() {
        UsbManager usbManager = (UsbManager) mContext.getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> devicesMap = usbManager.getDeviceList();
        List<String> intelDevices = new ArrayList<String>();
        for (Map.Entry<String, UsbDevice> entry : devicesMap.entrySet()) {
            UsbDevice usbDevice = entry.getValue();
            if (UsbUtilities.isIntel(usbDevice))
                intelDevices.add(entry.getKey());
        }
        Iterator<Map.Entry<String, UsbDescAndroid>> iter = mDescriptors.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, UsbDescAndroid> entry = iter.next();
            if(!intelDevices.contains(entry.getKey())) {
                removeDevice(entry.getValue());
                iter.remove();
            }
        }
        for(String name : intelDevices) {
            if(!mDescriptors.containsKey(name) && UsbUtilities.hasUsbPermission(mContext, devicesMap.get(name))) {
                addDevice(devicesMap.get(name));
            }
        }
    }

    protected void removeDevice(UsbDesc desc) {
        System.out.println("Removing device: " + desc.name);

        nRemoveUsbDevice(desc.descriptor);
        ((UsbDescAndroid)desc).connection.close();
        updateListeners(UpdateListenersType.DETACH);
        System.out.println("Device: " + desc.name + " removed successfully");
    }

    private void addDevice(UsbDevice device) {
        if (device == null)
            return;

        UsbManager usbManager = (UsbManager) mContext.getSystemService(Context.USB_SERVICE);
        UsbDeviceConnection conn = usbManager.openDevice(device);
        if(conn == null)
            return;
        UsbDescAndroid desc = new UsbDescAndroid(device.getDeviceName(), conn.getFileDescriptor(), conn);
        System.out.println("Adding device: " + desc.name);
        mDescriptors.put(device.getDeviceName(), desc);
        nAddUsbDevice(desc.name, desc.descriptor);

        updateListeners(UpdateListenersType.ATTACH);
        System.out.println("Device: " + desc.name + " added successfully");
    }

    @Override
    public void close() {
        Iterator<Map.Entry<String, UsbDescAndroid>> iter = mDescriptors.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, UsbDescAndroid> entry = iter.next();
            removeDevice(entry.getValue());
            iter.remove();
        }
    }
}
