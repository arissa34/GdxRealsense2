package com.intel.realsense.librealsense;

import java.util.HashMap;
import java.util.Map;

public abstract class Options extends LrsClass implements OptionsInterface {
    private Map<Option,OptionRange> mOptionRange = new HashMap<>();

    private class OptionRange {
        public float min;
        public float max;
        public float step;
        public float def;
    }

    private synchronized OptionRange getRange(Option option){
        if(!mOptionRange.containsKey(option)) {
            OptionRange optionRange = new OptionRange();
            nGetRange(mHandle, option.value(), optionRange);
            mOptionRange.put(option, optionRange);
        }
        return mOptionRange.get(option);
    }

    @Override
    public boolean supports(Option option) {
        return nSupports(mHandle, option.value());
    }

    @Override
    public float getValue(Option option) {
        return nGetValue(mHandle, option.value());
    }

    @Override
    public void setValue(Option option, float value) {
        nSetValue(mHandle, option.value(), value);
    }

    @Override
    public float getMinRange(Option option) {
        return getRange(option).min;
    }

    @Override
    public float getMaxRange(Option option) {
        return getRange(option).max;
    }

    @Override
    public float getStep(Option option) {
        return getRange(option).step;
    }

    @Override
    public float getDefault(Option option) {
        return getRange(option).def;
    }

    @Override
    public boolean isReadOnly(Option option) {
        return nIsReadOnly(mHandle, option.value());
    }

    @Override
    public String getDescription(Option option) {
        return nGetDescription(mHandle, option.value());
    }

    @Override
    public String valueDescription(Option option, float value) {
        return nGetValueDescription(mHandle, option.value(), value);
    }

    /*JNI
    #include <jni.h>
    #include <error.h>

    #include <librealsense2/rs.h>
     */

    private static native boolean nSupports(long handle, int option);/*
    rs2_error* e = NULL;
    auto rv = rs2_supports_option((const rs2_options *) handle, (rs2_option) option, &e);
    handle_error(env, e);
    return rv > 0;
    */
    private static native float nGetValue(long handle, int option);/*
    rs2_error* e = NULL;
    float rv = rs2_get_option(reinterpret_cast<const rs2_options *>(handle),
                              static_cast<rs2_option>(option), &e);
    handle_error(env, e);
    return rv;
     */
    private static native void nSetValue(long handle, int option, float value);/*
    rs2_error* e = NULL;
    rs2_set_option(reinterpret_cast<const rs2_options *>(handle), static_cast<rs2_option>(option), value, &e);
    handle_error(env, e);
     */
    private static native void nGetRange(long handle, int option, OptionRange outParams);/*
    float min = -1;
    float max = -1;
    float step = -1;
    float def = -1;
    rs2_error *e = NULL;
    jclass claxx = env->GetObjectClass(outParams);

    rs2_get_option_range(reinterpret_cast<const rs2_options *>(handle),
                         static_cast<rs2_option>(option), &min, &max, &step, &def, &e);
    handle_error(env, e);

    if(e)
        return;

    jfieldID minField = env->GetFieldID(claxx, "min", "F");
    jfieldID maxField = env->GetFieldID(claxx, "max", "F");
    jfieldID stepField = env->GetFieldID(claxx, "step", "F");
    jfieldID defField = env->GetFieldID(claxx, "def", "F");

    env->SetFloatField(outParams, minField, min);
    env->SetFloatField(outParams, maxField, max);
    env->SetFloatField(outParams, stepField, step);
    env->SetFloatField(outParams, defField, def);
     */
    private static native boolean nIsReadOnly(long handle, int option);/*
    rs2_error* e = NULL;
    int rv = rs2_is_option_read_only(reinterpret_cast<const rs2_options *>(handle),
                                     static_cast<rs2_option>(option), &e);
    handle_error(env, e);
    return rv > 0;
     */
    private static native String nGetDescription(long handle, int option);/*
    rs2_error* e = NULL;
    const char *rv = rs2_get_option_description(reinterpret_cast<const rs2_options *>(handle),
                                                static_cast<rs2_option>(option), &e);
    handle_error(env, e);
    return env->NewStringUTF(rv);
     */
    private static native String nGetValueDescription(long handle, int option, float value);/*
    rs2_error* e = NULL;
    const char *rv = rs2_get_option_value_description(reinterpret_cast<const rs2_options *>(handle),
                                                      static_cast<rs2_option>(option), value, &e);
    handle_error(env, e);
    return env->NewStringUTF(rv);
     */

}
