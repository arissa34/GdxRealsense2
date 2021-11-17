package com.gdx.realsense.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.BufferUtils;

import java.nio.ByteBuffer;

public class StreamTexture extends Texture {

    private static final String TAG = StreamTexture.class.getSimpleName();

    private ByteBuffer buffer;
    private int originalWidth;
    private int originalHeight;

    public StreamTexture(int originalWidth, int originalHeight) {
        super(originalWidth, originalHeight, Pixmap.Format.RGB888);
        this.originalWidth = originalWidth;
        this.originalHeight = originalHeight;
    }

    private void ensureCapacity(int numBytes) {
        if (buffer == null || buffer.capacity() < numBytes) {
            if (buffer != null) {
                BufferUtils.disposeUnsafeByteBuffer(buffer);
            }
            buffer = BufferUtils.newUnsafeByteBuffer(numBytes);
        }
    }

    public void updateTexture(byte[] data) {
        if (data == null) return;

        ensureCapacity(data.length);

        BufferUtils.copy(data, 0, buffer, data.length);

        //Gdx.app.log(TAG, "===== data.length : "+data.length+ " data[0] : "+data[0]+ " data[141291] : "+data[141291]);

        bind(0);
        Gdx.gl.glTexImage2D(GL20.GL_TEXTURE_2D,
                0,
                GL20.GL_RGB,
                originalWidth,
                originalHeight,
                0,
                GL20.GL_RGB,
                GL20.GL_UNSIGNED_BYTE,
                buffer);
        Gdx.gl.glBindTexture(glTarget, 0);
    }

    @Override
    public void dispose() {
        if (buffer != null) {
            BufferUtils.disposeUnsafeByteBuffer(buffer);
        }
        super.dispose();
    }
}
