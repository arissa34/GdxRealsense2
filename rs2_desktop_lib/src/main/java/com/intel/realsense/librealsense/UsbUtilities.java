package com.intel.realsense.librealsense;

import org.usb4java.DeviceDescriptor;

public class UsbUtilities {

    public static boolean isIntel(DeviceDescriptor deviceDescriptor){
        if (String.format("0x%04x", deviceDescriptor.idVendor() & 0xffff).equals("0x8086"))
            return true;
        return false;
    }
}
