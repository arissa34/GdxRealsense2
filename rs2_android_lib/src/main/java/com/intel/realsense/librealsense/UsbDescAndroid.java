package com.intel.realsense.librealsense;

import android.hardware.usb.UsbDeviceConnection;

class UsbDescAndroid extends UsbDesc{

    UsbDeviceConnection connection;

    public UsbDescAndroid(String nane, int descriptor, UsbDeviceConnection connection) {
        super(nane, descriptor);
        this.connection = connection;
    }
}
