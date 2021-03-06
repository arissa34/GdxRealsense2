package com.intel.realsense.librealsense;

import com.badlogic.gdx.math.Vector3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class MotionFrame extends Frame {
    protected MotionFrame(long handle) {
        super(handle);
        mOwner = false;
    }

    public Vector3 getMotionData(){
        ByteBuffer buffer = ByteBuffer.allocate(32);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        getData(buffer.array());
        FloatBuffer fb = buffer.asFloatBuffer();
        return new Vector3(fb.get(0), fb.get(1), fb.get(2));
    }

    public MotionStreamProfile getProfile() {
        return new MotionStreamProfile(nGetStreamProfile(mHandle));
    }
}
