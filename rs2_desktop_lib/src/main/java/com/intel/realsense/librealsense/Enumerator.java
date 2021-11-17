package com.intel.realsense.librealsense;

import org.usb4java.Context;
import org.usb4java.DeviceDescriptor;
import org.usb4java.HotplugCallback;
import org.usb4java.HotplugCallbackHandle;
import org.usb4java.LibUsb;
import org.usb4java.LibUsbException;
import org.usb4java.Device;

public class Enumerator {

    private DeviceListener mListener;
    private final EventHandlingThread mEventHandlingThread;
    private final HotplugCallbackHandle mCallbackHandle;

    public Enumerator(DeviceListener listener){
        if(listener == null) {
            System.err.println("Enumerator: provided listener is null");
            throw new NullPointerException("provided listener is null");
        }
        // Check if hotplug is available
        if (!LibUsb.hasCapability(LibUsb.CAP_HAS_HOTPLUG))
        {
            System.err.println("libusb doesn't support hotplug on this system");
        }

        mListener = listener;

        mEventHandlingThread = new EventHandlingThread();
        mEventHandlingThread.start();

        mCallbackHandle = new HotplugCallbackHandle();
        int result = LibUsb.hotplugRegisterCallback(null,
                LibUsb.HOTPLUG_EVENT_DEVICE_ARRIVED
                        | LibUsb.HOTPLUG_EVENT_DEVICE_LEFT,
                LibUsb.HOTPLUG_ENUMERATE,
                LibUsb.HOTPLUG_MATCH_ANY,
                LibUsb.HOTPLUG_MATCH_ANY,
                LibUsb.HOTPLUG_MATCH_ANY,
                new Callback(mListener), null, mCallbackHandle);
        if (result != LibUsb.SUCCESS)
        {
            throw new LibUsbException("Unable to register hotplug callback",
                    result);
        }
    }

    /**
     * Stop listening to the USB events and clean resources.
     */
    public synchronized void close() throws InterruptedException {
        mEventHandlingThread.abort();
        LibUsb.hotplugDeregisterCallback(null, mCallbackHandle);
        mEventHandlingThread.join();
    }

    @Override
    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }

    /**
     * This is the event handling thread. libusb doesn't start threads by its
     * own so it is our own responsibility to give libusb time to handle the
     * events in our own thread.
     */
    static class EventHandlingThread extends Thread
    {
        /** If thread should abort. */
        private volatile boolean abort;

        /**
         * Aborts the event handling thread.
         */
        public void abort()
        {
            this.abort = true;
        }

        @Override
        public void run()
        {
            while (!this.abort)
            {
                // Let libusb handle pending events. This blocks until events
                // have been handled, a hotplug callback has been deregistered
                // or the specified time of 1 second (Specified in
                // Microseconds) has passed.
                int result = LibUsb.handleEventsTimeout(null, 1000000);
                if (result != LibUsb.SUCCESS)
                    throw new LibUsbException("Unable to handle events", result);
            }
        }
    }

    /**
     * The hotplug callback handler
     */
    static class Callback implements HotplugCallback
    {
        DeviceListener mListener;

        public Callback(DeviceListener listener){
            mListener = listener;
        }

        @Override
        public int processEvent(Context context, Device device, int event,
                                Object userData)
        {
            DeviceDescriptor descriptor = new DeviceDescriptor();
            int result = LibUsb.getDeviceDescriptor(device, descriptor);
            if (result != LibUsb.SUCCESS)
                throw new LibUsbException("Unable to read device descriptor",
                        result);

            if(event == LibUsb.HOTPLUG_EVENT_DEVICE_ARRIVED){
                mListener.onDeviceAttach();
            }else{
                mListener.onDeviceDetach();
            }
            //System.out.format("%s: %04x:%04x%n",
            //        event == LibUsb.HOTPLUG_EVENT_DEVICE_ARRIVED ? "Connected" :
            //                "Disconnected",
            //        descriptor.idVendor(), descriptor.idProduct());
            return 0;
        }

    }


}
