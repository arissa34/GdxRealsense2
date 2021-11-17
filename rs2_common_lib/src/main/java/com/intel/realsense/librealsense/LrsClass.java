package com.intel.realsense.librealsense;

import com.badlogic.gdx.utils.SharedLibraryLoader;

abstract public class LrsClass implements AutoCloseable{
    protected long mHandle = 0L;
    protected boolean mOwner = true;

    LrsClass() {
    }

    public long getHandle() {
        return this.mHandle;
    }

    static {
        new SharedLibraryLoader().load("rs2");
    }
}
