LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)
 
LOCAL_MODULE    := rs2
LOCAL_C_INCLUDES := cpp/jnigen cpp/realsense cpp/realsense/include 
 
LOCAL_CFLAGS := $(LOCAL_C_INCLUDES:%=-I%) -O2 -Wall -D__ANDROID__ -w -frtti -fexceptions
LOCAL_CPPFLAGS := $(LOCAL_C_INCLUDES:%=-I%) -O2 -Wall -D__ANDROID__ -w -std=c++11 -frtti -fexceptions
LOCAL_LDLIBS := -lm
LOCAL_ARM_MODE  := arm
LOCAL_STATIC_LIBRARIES := librealsense2
 
LOCAL_SRC_FILES := memcpy_wrap.c\
	cpp/jnigen/com_intel_realsense_librealsense_ThresholdFilter.cpp\
	cpp/jnigen/com_intel_realsense_librealsense_RoiSensor.cpp\
	cpp/jnigen/com_intel_realsense_librealsense_FwLogger.cpp\
	cpp/jnigen/com_intel_realsense_librealsense_Pointcloud.cpp\
	cpp/jnigen/com_intel_realsense_librealsense_FwLogParsedMsg.cpp\
	cpp/jnigen/com_intel_realsense_librealsense_SpatialFilter.cpp\
	cpp/jnigen/com_intel_realsense_librealsense_FwLogMsg.cpp\
	cpp/jnigen/com_intel_realsense_librealsense_DepthFrame.cpp\
	cpp/jnigen/com_intel_realsense_librealsense_HoleFillingFilter.cpp\
	cpp/jnigen/com_intel_realsense_librealsense_MotionStreamProfile.cpp\
	cpp/jnigen/com_intel_realsense_librealsense_Align.cpp\
	cpp/jnigen/com_intel_realsense_librealsense_ProcessingBlock.cpp\
	cpp/jnigen/com_intel_realsense_librealsense_FrameSet.cpp\
	cpp/jnigen/com_intel_realsense_librealsense_Sensor.cpp\
	cpp/jnigen/com_intel_realsense_librealsense_YuyDecoder.cpp\
	cpp/jnigen/com_intel_realsense_librealsense_Config.cpp\
	cpp/jnigen/com_intel_realsense_librealsense_Pipeline.cpp\
	cpp/jnigen/com_intel_realsense_librealsense_DeviceList.cpp\
	cpp/jnigen/com_intel_realsense_librealsense_DisparityTransformFilter.cpp\
	cpp/jnigen/com_intel_realsense_librealsense_DecimationFilter.cpp\
	cpp/jnigen/com_intel_realsense_librealsense_HdrMerge.cpp\
	cpp/jnigen/com_intel_realsense_librealsense_Points.cpp\
	cpp/jnigen/com_intel_realsense_librealsense_FrameQueue.cpp\
	cpp/jnigen/com_intel_realsense_librealsense_ZeroOrderInvalidationFilter.cpp\
	cpp/jnigen/com_intel_realsense_librealsense_RsContext.cpp\
	cpp/jnigen/com_intel_realsense_librealsense_Device.cpp\
	cpp/jnigen/com_intel_realsense_librealsense_Frame.cpp\
	cpp/jnigen/com_intel_realsense_librealsense_Utils.cpp\
	cpp/jnigen/com_intel_realsense_librealsense_PipelineProfile.cpp\
	cpp/jnigen/com_intel_realsense_librealsense_VideoStreamProfile.cpp\
	cpp/jnigen/com_intel_realsense_librealsense_VideoFrame.cpp\
	cpp/jnigen/com_intel_realsense_librealsense_TemporalFilter.cpp\
	cpp/jnigen/com_intel_realsense_librealsense_SequenceIdFilter.cpp\
	cpp/jnigen/com_intel_realsense_librealsense_StreamProfile.cpp\
	cpp/jnigen/com_intel_realsense_librealsense_Options.cpp\
	cpp/jnigen/com_intel_realsense_librealsense_Colorizer.cpp\
	cpp/jnigen/com_intel_realsense_librealsense_DepthSensor.cpp\
	cpp/realsense/error.cpp\
	cpp/realsense/frame_callback_android.cpp
 
include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := librealsense2
ifeq ($(TARGET_ARCH_ABI), arm64-v8a)
LOCAL_SRC_FILES := libs/arm64-v8a/librealsense2.so
endif
ifeq ($(TARGET_ARCH_ABI), armeabi-v7a)
LOCAL_SRC_FILES := libs/armeabi-v7a/librealsense2.so
endif
ifeq ($(TARGET_ARCH_ABI), x86)
LOCAL_SRC_FILES := libs/X86/librealsense2.so
endif
ifeq ($(TARGET_ARCH_ABI), x86_64)
LOCAL_SRC_FILES := libs/X86_64/librealsense2.so
endif
include $(PREBUILT_SHARED_LIBRARY)