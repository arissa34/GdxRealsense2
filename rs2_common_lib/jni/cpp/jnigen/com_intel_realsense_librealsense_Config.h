/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_intel_realsense_librealsense_Config */

#ifndef _Included_com_intel_realsense_librealsense_Config
#define _Included_com_intel_realsense_librealsense_Config
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_intel_realsense_librealsense_Config
 * Method:    nCreate
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_intel_realsense_librealsense_Config_nCreate
  (JNIEnv *, jclass);

/*
 * Class:     com_intel_realsense_librealsense_Config
 * Method:    nDelete
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Config_nDelete
  (JNIEnv *, jclass, jlong);

/*
 * Class:     com_intel_realsense_librealsense_Config
 * Method:    nEnableStream
 * Signature: (JIIIIII)V
 */
JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Config_nEnableStream
  (JNIEnv *, jclass, jlong, jint, jint, jint, jint, jint, jint);

/*
 * Class:     com_intel_realsense_librealsense_Config
 * Method:    nDisableStream
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Config_nDisableStream
  (JNIEnv *, jclass, jlong, jint);

/*
 * Class:     com_intel_realsense_librealsense_Config
 * Method:    nEnableAllStreams
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Config_nEnableAllStreams
  (JNIEnv *, jclass, jlong);

/*
 * Class:     com_intel_realsense_librealsense_Config
 * Method:    nDisableAllStreams
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Config_nDisableAllStreams
  (JNIEnv *, jclass, jlong);

/*
 * Class:     com_intel_realsense_librealsense_Config
 * Method:    nEnableDeviceFromFile
 * Signature: (JLjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Config_nEnableDeviceFromFile
  (JNIEnv *, jclass, jlong, jstring);

/*
 * Class:     com_intel_realsense_librealsense_Config
 * Method:    nEnableDevice
 * Signature: (JLjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Config_nEnableDevice
  (JNIEnv *, jclass, jlong, jstring);

/*
 * Class:     com_intel_realsense_librealsense_Config
 * Method:    nEnableRecordToFile
 * Signature: (JLjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_intel_realsense_librealsense_Config_nEnableRecordToFile
  (JNIEnv *, jclass, jlong, jstring);

/*
 * Class:     com_intel_realsense_librealsense_Config
 * Method:    nCanResolve
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_intel_realsense_librealsense_Config_nCanResolve
  (JNIEnv *, jclass, jlong, jlong);

/*
 * Class:     com_intel_realsense_librealsense_Config
 * Method:    nResolve
 * Signature: (JJ)J
 */
JNIEXPORT jlong JNICALL Java_com_intel_realsense_librealsense_Config_nResolve
  (JNIEnv *, jclass, jlong, jlong);

#ifdef __cplusplus
}
#endif
#endif