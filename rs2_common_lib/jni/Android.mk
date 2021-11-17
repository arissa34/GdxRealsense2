LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)
 
LOCAL_MODULE    := rs2
LOCAL_C_INCLUDES := realsense 
 
LOCAL_CFLAGS := $(LOCAL_C_INCLUDES:%=-I%) -O2 -Wall -D__ANDROID__ -frtti -fexceptions
LOCAL_CPPFLAGS := $(LOCAL_C_INCLUDES:%=-I%) -O2 -Wall -D__ANDROID__ -std=c++11 -frtti -fexceptions
LOCAL_LDLIBS := -lm
LOCAL_ARM_MODE  := arm
LOCAL_STATIC_LIBRARIES := librealsense2
 
LOCAL_SRC_FILES := realsense/com_intel_realsense_librealsense_ThresholdFilter.cpp\
	realsense/com_intel_realsense_librealsense_RoiSensor.cpp\
	realsense/com_intel_realsense_librealsense_FwLogger.cpp\
	realsense/com_intel_realsense_librealsense_Pointcloud.cpp\
	realsense/com_intel_realsense_librealsense_FwLogParsedMsg.cpp\
	realsense/com_intel_realsense_librealsense_SpatialFilter.cpp\
	realsense/com_intel_realsense_librealsense_FwLogMsg.cpp\
	realsense/com_intel_realsense_librealsense_DepthFrame.cpp\
	realsense/com_intel_realsense_librealsense_HoleFillingFilter.cpp\
	realsense/com_intel_realsense_librealsense_MotionStreamProfile.cpp\
	realsense/include/error.cpp\
	realsense/include/frame_callback_android.cpp\
	realsense/com_intel_realsense_librealsense_Align.cpp\
	realsense/com_intel_realsense_librealsense_ProcessingBlock.cpp\
	realsense/com_intel_realsense_librealsense_FrameSet.cpp\
	realsense/com_intel_realsense_librealsense_Sensor.cpp\
	realsense/com_intel_realsense_librealsense_YuyDecoder.cpp\
	realsense/com_intel_realsense_librealsense_Config.cpp\
	realsense/com_intel_realsense_librealsense_Pipeline.cpp\
	realsense/com_intel_realsense_librealsense_DeviceList.cpp\
	realsense/com_intel_realsense_librealsense_DisparityTransformFilter.cpp\
	realsense/com_intel_realsense_librealsense_DecimationFilter.cpp\
	realsense/com_intel_realsense_librealsense_HdrMerge.cpp\
	realsense/com_intel_realsense_librealsense_Points.cpp\
	realsense/com_intel_realsense_librealsense_FrameQueue.cpp\
	realsense/com_intel_realsense_librealsense_ZeroOrderInvalidationFilter.cpp\
	realsense/com_intel_realsense_librealsense_RsContext.cpp\
	realsense/com_intel_realsense_librealsense_Device.cpp\
	realsense/com_intel_realsense_librealsense_Frame.cpp\
	realsense/com_intel_realsense_librealsense_Utils.cpp\
	realsense/com_intel_realsense_librealsense_PipelineProfile.cpp\
	realsense/com_intel_realsense_librealsense_VideoStreamProfile.cpp\
	realsense/com_intel_realsense_librealsense_VideoFrame.cpp\
	realsense/com_intel_realsense_librealsense_TemporalFilter.cpp\
	realsense/com_intel_realsense_librealsense_SequenceIdFilter.cpp\
	realsense/com_intel_realsense_librealsense_StreamProfile.cpp\
	realsense/com_intel_realsense_librealsense_Options.cpp\
	realsense/com_intel_realsense_librealsense_Colorizer.cpp\
	realsense/com_intel_realsense_librealsense_DepthSensor.cpp
 
include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := librealsense2
ifeq ($(TARGET_ARCH_ABI), arm64-v8a)
LOCAL_SRC_FILES := realsense/libs/arm64-v8a/librealsense2.so
endif
ifeq ($(TARGET_ARCH_ABI), armeabi-v7a)
LOCAL_SRC_FILES := realsense/libs/armeabi-v7a/librealsense2.so
endif
ifeq ($(TARGET_ARCH_ABI), x86)
LOCAL_SRC_FILES := realsense/libs/X86/librealsense2.so
endif
ifeq ($(TARGET_ARCH_ABI), x86_64)
LOCAL_SRC_FILES := realsense/libs/X86_64/librealsense2.so
endif
include $(PREBUILT_SHARED_LIBRARY)