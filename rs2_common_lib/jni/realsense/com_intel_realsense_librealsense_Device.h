/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_intel_realsense_librealsense_Device */

#ifndef _Included_com_intel_realsense_librealsense_Device
#define _Included_com_intel_realsense_librealsense_Device
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_intel_realsense_librealsense_Device
 * Method:    nSupportsInfo
 * Signature: (JI)Z
 */
JNIEXPORT jboolean JNICALL Java_com_intel_realsense_librealsense_Device_nSupportsInfo
  (JNIEnv *, jclass, jlong, jint);

/*
 * Class:     com_intel_realsense_librealsense_Device
 * Method:    nGetInfo
 * Signature: (JI)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_intel_realsense_librealsense_Device_nGetInfo
  (JNIEnv *, jclass, jlong, jint);

/*
 * Class:     com_intel_realsense_librealsense_Device
 * Method:    nToggleAdvancedMode
 * Signature: (JZ)V
 */
JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Device_nToggleAdvancedMode
  (JNIEnv *, jclass, jlong, jboolean);

/*
 * Class:     com_intel_realsense_librealsense_Device
 * Method:    nIsInAdvancedMode
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_com_intel_realsense_librealsense_Device_nIsInAdvancedMode
  (JNIEnv *, jclass, jlong);

/*
 * Class:     com_intel_realsense_librealsense_Device
 * Method:    nLoadPresetFromJson
 * Signature: (J[B)V
 */
JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Device_nLoadPresetFromJson
  (JNIEnv *, jclass, jlong, jbyteArray);

/*
 * Class:     com_intel_realsense_librealsense_Device
 * Method:    nSerializePresetToJson
 * Signature: (J)[B
 */
JNIEXPORT jbyteArray JNICALL Java_com_intel_realsense_librealsense_Device_nSerializePresetToJson
  (JNIEnv *, jclass, jlong);

/*
 * Class:     com_intel_realsense_librealsense_Device
 * Method:    nQuerySensors
 * Signature: (J)[J
 */
JNIEXPORT jlongArray JNICALL Java_com_intel_realsense_librealsense_Device_nQuerySensors
  (JNIEnv *, jclass, jlong);

/*
 * Class:     com_intel_realsense_librealsense_Device
 * Method:    nHardwareReset
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Device_nHardwareReset
  (JNIEnv *, jclass, jlong);

/*
 * Class:     com_intel_realsense_librealsense_Device
 * Method:    nIsDeviceExtendableTo
 * Signature: (JI)Z
 */
JNIEXPORT jboolean JNICALL Java_com_intel_realsense_librealsense_Device_nIsDeviceExtendableTo
  (JNIEnv *, jclass, jlong, jint);

/*
 * Class:     com_intel_realsense_librealsense_Device
 * Method:    nRelease
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Device_nRelease
  (JNIEnv *, jclass, jlong);

#ifdef __cplusplus
}
#endif
#endif
