/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_intel_realsense_librealsense_DeviceList */

#ifndef _Included_com_intel_realsense_librealsense_DeviceList
#define _Included_com_intel_realsense_librealsense_DeviceList
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_intel_realsense_librealsense_DeviceList
 * Method:    nGetDeviceCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_intel_realsense_librealsense_DeviceList_nGetDeviceCount
  (JNIEnv *, jclass, jlong);

/*
 * Class:     com_intel_realsense_librealsense_DeviceList
 * Method:    nCreateDevice
 * Signature: (JI)J
 */
JNIEXPORT jlong JNICALL Java_com_intel_realsense_librealsense_DeviceList_nCreateDevice
  (JNIEnv *, jclass, jlong, jint);

/*
 * Class:     com_intel_realsense_librealsense_DeviceList
 * Method:    nContainsDevice
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_intel_realsense_librealsense_DeviceList_nContainsDevice
  (JNIEnv *, jclass, jlong, jlong);

/*
 * Class:     com_intel_realsense_librealsense_DeviceList
 * Method:    nRelease
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_DeviceList_nRelease
  (JNIEnv *, jclass, jlong);

#ifdef __cplusplus
}
#endif
#endif