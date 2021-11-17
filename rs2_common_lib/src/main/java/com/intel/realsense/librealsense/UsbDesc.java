package com.intel.realsense.librealsense;

//import android.hardware.usb.UsbDeviceConnection;

abstract class UsbDesc {
    String name;
    int descriptor;

    public UsbDesc(String name, int descriptor) {
        this.name = name;
        this.descriptor = descriptor;
    }

}
