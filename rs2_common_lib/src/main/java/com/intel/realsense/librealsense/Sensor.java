package com.intel.realsense.librealsense;

import java.util.ArrayList;
import java.util.List;

public class Sensor extends Options {

    Sensor(long h) {
        mOwner = false;
        mHandle = h;
    }

    public List<StreamProfile> getStreamProfiles(){
        long[] streamProfilesHandles = nGetStreamProfiles(mHandle);
        List<StreamProfile> rv = new ArrayList<>();
        for(long h : streamProfilesHandles){
            rv.add(new StreamProfile(h));
        }
        return rv;
    }

    public <T extends Sensor> T as(Extension extension) throws RuntimeException {
        if (this.is(extension)) {
            switch (extension){
                case ROI: return (T) new RoiSensor(mHandle);
                case DEPTH_SENSOR: return (T) new DepthSensor(mHandle);
                case COLOR_SENSOR: return (T) new ColorSensor(mHandle);
                default: throw new RuntimeException("this API version does not support " + extension.name());
            }
        } else{
            throw new RuntimeException("this sensor is not extendable to " + extension.name());
        }
    }

    public boolean is(Extension extension) {
        return nIsSensorExtendableTo(mHandle, extension.value());
    }

    // this method's name is not open() so that the user will call the
    // closeSensor to close the sensor (and not the close() method which has another aim)
    public void openSensor(StreamProfile sp) {
        nOpen(mHandle, sp.getHandle());
    }

    public void openSensor(List<StreamProfile> sp_list) {
        int size = sp_list.size();
        long[] profiles_array = new long[size];
        for (int i = 0; i < size; ++i) {
            profiles_array[i] = sp_list.get(i).getHandle();
        }
        nOpenMultiple(mHandle, profiles_array, sp_list.size());
    }

    public void start(FrameCallback cb) {
        nStart(mHandle, cb);
    }

    public void stop() {
        nStop(mHandle);
    }

    // release resources - from AutoCloseable interface
    @Override
    public void close() {
        if (mOwner)
            nRelease(mHandle);
    }

    // this method's name is not close() because this is already
    // taken by the inherited method from AutoCloseable interface
    public void closeSensor(){
        nClose(mHandle);
    }

    public StreamProfile findProfile(StreamType type, int index, int width, int height, StreamFormat format, int fps) {
        List<StreamProfile> profiles = getStreamProfiles();
        StreamProfile rv = null;

        for (StreamProfile profile : profiles) {
            if (profile.getType().compareTo(type) == 0) {

                if (profile.is(Extension.VIDEO_PROFILE)) {
                    VideoStreamProfile video_stream_profile = profile.as(Extension.VIDEO_PROFILE);

                    // After using the "as" method we can use the new data type
                    //  for additional operations:
                    StreamFormat sf = video_stream_profile.getFormat();
                    int idx = profile.getIndex();
                    int w = video_stream_profile.getWidth();
                    int h = video_stream_profile.getHeight();
                    int frameRate = video_stream_profile.getFrameRate();

                    if ((index == -1 || idx == index) &&
                            w == width && h == height &&
                            frameRate == fps && (sf.compareTo(format) == 0)) {
                        rv = profile;
                        break;
                    }
                }
            }
        }
        return rv;
    }

    /*JNI
    #include <jni.h>
    #include <memory>
    #include <vector>
    #include <error.h>

    #include <librealsense2/rs.h>

    #include <jni_logging.h>
    #include <frame_callback.h>

    static frame_callback_data sdata = {NULL, 0, JNI_FALSE, NULL, NULL};
     */

    private static native long[] nGetStreamProfiles(long handle);/*
    rs2_error* e = nullptr;
    std::shared_ptr<rs2_stream_profile_list> list(
            rs2_get_stream_profiles(reinterpret_cast<rs2_sensor *>(handle), &e),
            rs2_delete_stream_profiles_list);
    handle_error(env, e);

    auto size = rs2_get_stream_profiles_count(list.get(), &e);
    handle_error(env, e);

    std::vector<const rs2_stream_profile*> profiles;

    for (auto i = 0; i < size; i++)
    {
        auto sp = rs2_get_stream_profile(list.get(), i, &e);
        handle_error(env, e);
        profiles.push_back(sp);
    }

    // jlong is 64-bit, but pointer in profiles could be 32-bit, copy element by element
    jlongArray rv = env->NewLongArray(profiles.size());
    for (auto i = 0; i < size; i++)
    {
        env->SetLongArrayRegion(rv, i, 1, reinterpret_cast<const jlong *>(&profiles[i]));
    }
    return rv;
    */
    private static native void nRelease(long handle);/*
    rs2_delete_sensor(reinterpret_cast<rs2_sensor *>(handle));
     */
    private static native boolean nIsSensorExtendableTo(long handle, int extension);/*
    rs2_error *e = NULL;
    int rv = rs2_is_sensor_extendable_to(reinterpret_cast<const rs2_sensor *>(handle),
                                         static_cast<rs2_extension>(extension), &e);
    handle_error(env, e);
    return rv > 0;
     */
    private static native void nOpen(long handle, long sp);/*
    rs2_error* e = nullptr;
    rs2_open(reinterpret_cast<rs2_sensor *>(handle), reinterpret_cast<rs2_stream_profile *>(sp), &e);
    handle_error(env, e);
     */
    private static native void nOpenMultiple(long device_handle, long[] profiles, int num_of_profiles);/*
    // building C++ profiles vector
    rs2_error* e = nullptr;
    std::vector<const rs2_stream_profile*> profs;
    profs.reserve(num_of_profiles);
    for (int i = 0; i < num_of_profiles; ++i)
    {
        auto p = reinterpret_cast<const rs2_stream_profile*>(profiles[i]);
        profs.push_back(p);
    }
    // API call
    rs2_open_multiple(reinterpret_cast<rs2_sensor *>(device_handle),
                      profs.data(), static_cast<int>(num_of_profiles), &e);
    handle_error(env, e);
     */
    private static native void nStart(long handle, FrameCallback jcb);/*
    rs2_error* e = nullptr;

    if (rs_jni_callback_init(env, jcb, &sdata) != true) return;

    auto cb = [&](rs2::frame f) {
        rs_jni_cb(f, &sdata);
    };

    rs2_start_cpp(reinterpret_cast<rs2_sensor *>(handle), new rs2::frame_callback<decltype(cb)>(cb), &e);
    handle_error(env, e);
     */
    private static native void nStop(long handle);/*
    rs2_error* e = nullptr;
    rs_jni_cleanup(env, &sdata);
    rs2_stop(reinterpret_cast<rs2_sensor *>(handle), &e);
    handle_error(env, e);
     */
    private static native void nClose(long handle);/*
    rs2_error* e = nullptr;
    rs2_close(reinterpret_cast<rs2_sensor *>(handle), &e);
    handle_error(env, e);
     */
}
