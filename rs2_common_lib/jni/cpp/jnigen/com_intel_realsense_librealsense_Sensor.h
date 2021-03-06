/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_intel_realsense_librealsense_Sensor */

#ifndef _Included_com_intel_realsense_librealsense_Sensor
#define _Included_com_intel_realsense_librealsense_Sensor
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_intel_realsense_librealsense_Sensor
 * Method:    nGetStreamProfiles
 * Signature: (J)[J
 */
JNIEXPORT jlongArray JNICALL Java_com_intel_realsense_librealsense_Sensor_nGetStreamProfiles
  (JNIEnv *, jclass, jlong);

/*
 * Class:     com_intel_realsense_librealsense_Sensor
 * Method:    nRelease
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Sensor_nRelease
  (JNIEnv *, jclass, jlong);

/*
 * Class:     com_intel_realsense_librealsense_Sensor
 * Method:    nIsSensorExtendableTo
 * Signature: (JI)Z
 */
JNIEXPORT jboolean JNICALL Java_com_intel_realsense_librealsense_Sensor_nIsSensorExtendableTo
  (JNIEnv *, jclass, jlong, jint);

/*
 * Class:     com_intel_realsense_librealsense_Sensor
 * Method:    nOpen
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Sensor_nOpen
  (JNIEnv *, jclass, jlong, jlong);

/*
 * Class:     com_intel_realsense_librealsense_Sensor
 * Method:    nOpenMultiple
 * Signature: (J[JI)V
 */
JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Sensor_nOpenMultiple
  (JNIEnv *, jclass, jlong, jlongArray, jint);

/*
 * Class:     com_intel_realsense_librealsense_Sensor
 * Method:    nStart
 * Signature: (JLcom/intel/realsense/librealsense/FrameCallback;)V
 */
JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Sensor_nStart
  (JNIEnv *, jclass, jlong, jobject);

/*
 * Class:     com_intel_realsense_librealsense_Sensor
 * Method:    nStop
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Sensor_nStop
  (JNIEnv *, jclass, jlong);

/*
 * Class:     com_intel_realsense_librealsense_Sensor
 * Method:    nClose
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Sensor_nClose
  (JNIEnv *, jclass, jlong);

#ifdef __cplusplus
}
#endif
#endif
