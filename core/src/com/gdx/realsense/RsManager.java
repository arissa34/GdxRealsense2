package com.gdx.realsense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.LifecycleListener;
import com.gdx.realsense.threading.RsThread;
import com.gdx.realsense.threading.RsUpdatableThread;
import com.intel.realsense.librealsense.Align;
import com.intel.realsense.librealsense.CameraInfo;
import com.intel.realsense.librealsense.Colorizer;
import com.intel.realsense.librealsense.Config;
import com.intel.realsense.librealsense.DepthFrame;
import com.intel.realsense.librealsense.DeviceList;
import com.intel.realsense.librealsense.DeviceListener;
import com.intel.realsense.librealsense.Extension;
import com.intel.realsense.librealsense.Frame;
import com.intel.realsense.librealsense.FrameCallback;
import com.intel.realsense.librealsense.FrameReleaser;
import com.intel.realsense.librealsense.FrameSet;
import com.intel.realsense.librealsense.Pipeline;
import com.intel.realsense.librealsense.PipelineProfile;
import com.intel.realsense.librealsense.ProductLine;
import com.intel.realsense.librealsense.RsContext;
import com.intel.realsense.librealsense.StreamType;


public class RsManager implements DeviceListener, LifecycleListener, RsUpdatableThread {

    private static String TAG = RsManager.class.getSimpleName();

    public static final int CAM_WIDTH = 640;
    public static final int CAM_HEIGHT = 480;

    private final RsContext rsContext;
    private final Pipeline pipeline;
    private RsThread rsThread;
    private Align align;
    private Colorizer colorizer;
    private boolean isStarted = false;

    public RsManager() {
        rsContext = new RsContext();
        pipeline = new Pipeline();
        rsThread = new RsThread();

        try (DeviceList dl = rsContext.queryDevices()) {
            if (dl.getDeviceCount() > 0) {
                start();
            }
        }
    }

    private void start() {
        loadConfigAndStartPipe(pipeline);
    }

    private void stop() {
        rsThread.unregister(this);
        isStarted = false;
        pipeline.stop();
        colorizer.close();
        align.close();
    }

    @Override
    public void resume() {
        Gdx.app.log(TAG, "====> resume");
    }


    @Override
    public void pause() {
        Gdx.app.log(TAG, "====> pause");
        stop();
    }

    public RsContext getRsContext() {
        return rsContext;
    }

    @Override
    public void onDeviceAttach() {
        System.out.println("====> Adding device");

        DeviceList deviceList = rsContext.queryDevices(ProductLine.D400);
        deviceList.foreach(device ->
                System.out.println("====> device detected : " + device.getInfo(CameraInfo.NAME))
        );
        deviceList.close();

        start();
    }

    private synchronized void loadConfigAndStartPipe(Pipeline pipe) {
        try (Config config = new Config()) {

            int widthColor = CAM_WIDTH;
            int heightColor = CAM_HEIGHT;

            int widthDepth = CAM_WIDTH;
            int heightDepth = CAM_HEIGHT;

            config.enableStream(StreamType.COLOR, widthColor, heightColor);
            config.enableStream(StreamType.DEPTH, widthDepth, heightDepth);

            try (PipelineProfile pp = pipe.start(config)) {
                colorizer = new Colorizer();
                align = new Align(StreamType.COLOR);
                rsThread.register(this);
                isStarted = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDeviceDetach() {
        System.out.println("====> Detach device");

        stop();
    }

    private static final byte[] colorFrameBytes = new byte[CAM_WIDTH * CAM_HEIGHT * 3];
    private static final byte[] depthFrameBytes = new byte[CAM_WIDTH * CAM_HEIGHT * 3];

    public boolean isRunning() {
        return isStarted;
    }

    public byte[] getColorFrameBytes() {
        return colorFrameBytes;
    }

    public byte[] getDepthFrameBytes() {
        return depthFrameBytes;
    }

    FrameCallback frameCallback = new FrameCallback() {
        @Override
        public void onFrame(Frame processed) {
            if (processed.is(Extension.DEPTH_FRAME)) {
                DepthFrame depth = processed.as(Extension.DEPTH_FRAME);
                try (FrameReleaser fr = new FrameReleaser()) {
                    Frame frame = depth.applyFilter(colorizer).releaseWith(fr);
                    frame.getData(depthFrameBytes);
                }
            }
        }
    };

    public void close() {
        if (isRunning()) stop();
        pipeline.close();
        rsContext.close();
    }

    @Override
    public void dispose() {
        rsThread.dispose();
    }

    @Override
    public void updateOnThread() {
        if (!isRunning()) return;

        try {
            try (FrameSet frameSet = pipeline.waitForFrames()) {
                try (FrameSet process = align.process(frameSet)) {
                    process.foreach(frameCallback);
                }
                try (Frame color = frameSet.first(StreamType.COLOR)) {
                    color.getData(colorFrameBytes);
                }
            }
        } catch (Exception e) {
            Gdx.app.error(TAG, e.getMessage());
            stop();
        }

    }
}
