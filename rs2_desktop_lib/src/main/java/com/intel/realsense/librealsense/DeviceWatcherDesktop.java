package com.intel.realsense.librealsense;

import org.usb4java.Context;
import org.usb4java.DeviceDescriptor;
import org.usb4java.LibUsb;
import org.usb4java.LibUsbException;
import org.usb4java.DeviceList;
import org.usb4java.Device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DeviceWatcherDesktop extends DeviceWatcher {

    private final Context libUsbContext;
    private final Enumerator mEnumerator;

    private HashMap<String, UsbDesc> mDescriptors = new LinkedHashMap<>();

    public DeviceWatcherDesktop(){
        super();
        libUsbContext = new Context();
        int result = LibUsb.init(libUsbContext);
        if (result != LibUsb.SUCCESS) throw new LibUsbException("Unable to initialize libusb.", result);
        mEnumerator = new Enumerator(this);
    }

    @Override
    protected void invalidateDevices() {
        List<String> intelDevices = new ArrayList<String>();
        HashMap<String, DeviceDescriptor> devicesMap = new HashMap();

        DeviceList deviceList = new DeviceList();
        int result = LibUsb.getDeviceList(libUsbContext, deviceList);
        if (result < 0)
            throw new LibUsbException("Unable to get device list", result);
        Iterator<Device> iterator = deviceList.iterator();
        while (iterator.hasNext()){
            Device device = iterator.next();
            DeviceDescriptor descriptor = new DeviceDescriptor();
            result = LibUsb.getDeviceDescriptor(device, descriptor);
            if (result < 0)
                throw new LibUsbException( "Unable to read device descriptor", result);

            String name = String.format("0x%x", device.getPointer());
            //String vendorId = String.format("0x%04x", descriptor.idVendor() & 0xffff);
            devicesMap.put(name, descriptor);
            if(UsbUtilities.isIntel(descriptor)){
                intelDevices.add(name);
            }
        }

        Iterator<Map.Entry<String, UsbDesc>> iter = mDescriptors.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, UsbDesc> entry = iter.next();
            if(!intelDevices.contains(entry.getKey())) {
                removeDevice(entry.getValue());
                iter.remove();
            }
        }
        for(String name : intelDevices) {
            if(!mDescriptors.containsKey(name)) {
                addDevice(name, devicesMap.get(name));
            }
        }
    }

    private void addDevice(String deviceName, DeviceDescriptor device) {
        UsbDesc desc = new UsbDescDesktop(deviceName, (int) device.idProduct());
        //System.out.println("Adding device: " + desc.name);
        mDescriptors.put(deviceName, desc);
        //nAddUsbDevice(desc.name, desc.descriptor);

        updateListeners(UpdateListenersType.ATTACH);
        //System.out.println("Device: " + desc.name + " added successfully");
    }

    @Override
    protected void removeDevice(UsbDesc desc) {
        //System.out.println("Removing device: " + desc.name);

        //nRemoveUsbDevice(desc.descriptor);
        //((UsbDescDesktop)desc).connection.close();
        updateListeners(UpdateListenersType.DETACH);
        //System.out.println("Device: " + desc.name + " removed successfully");
    }

    @Override
    public void close() {
        Iterator<Map.Entry<String, UsbDesc>> iter = mDescriptors.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, UsbDesc> entry = iter.next();
            removeDevice(entry.getValue());
            iter.remove();
        }
        LibUsb.exit(libUsbContext);
    }
}
