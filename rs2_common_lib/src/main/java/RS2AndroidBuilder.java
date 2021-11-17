import com.badlogic.gdx.jnigen.AntScriptGenerator;
import com.badlogic.gdx.jnigen.BuildConfig;
import com.badlogic.gdx.jnigen.BuildExecutor;
import com.badlogic.gdx.jnigen.BuildTarget;
import com.badlogic.gdx.jnigen.NativeCodeGenerator;
import com.badlogic.gdx.utils.SharedLibraryLoader;

public class RS2AndroidBuilder {

    public static void main(String[] args) throws Exception {

        String libName = "rs2";

        new NativeCodeGenerator().generate(
                "src/main/java/",
                "build/classes/java/main",
                "jni/realsense",
                new String[]{
                        "**/RsContext.java",
                        "**/DeviceList.java",
                        "**/Device.java",
                        "**/Pipeline.java",
                        "**/PipelineProfile.java",
                        "**/Config.java",
                        "**/Frame.java",
                        "**/FrameSet.java",
                        "**/FrameQueue.java",
                        "**/DepthFrame.java",
                        "**/VideoFrame.java",
                        "**/Points.java",
                        "**/StreamProfile.java",
                        "**/Sensor.java",
                        "**/Options.java",
                        "**/ProcessingBlock.java",
                        "**/Align.java",
                        "**/Colorizer.java",
                        "**/DecimationFilter.java",
                        "**/DisparityTransformFilter.java",
                        "**/HoleFillingFilter.java",
                        "**/Pointcloud.java",
                        "**/SpatialFilter.java",
                        "**/TemporalFilter.java",
                        "**/ThresholdFilter.java",
                        "**/ZeroOrderInvalidationFilter.java",
                        "**/YuyDecoder.java",
                        "**/HdrMerge.java",
                        "**/SequenceIdFilter.java",
                        "**/DepthSensor.java",
                        "**/RoiSensor.java",
                        "**/VideoStreamProfile.java",
                        "**/MotionStreamProfile.java",
                        "**/Utils.java",
                        "**/FwLogger.java",
                        "**/FwLogMsg.java",
                        "**/FwLogParsedMsg.java",
                },
                new String[]{
                        "**/RS2AndroidBuilder.java",
                        "**/RS2DesktopBuilder.java"
                }
        );
        String[] headerDirs = new String[]{
                "realsense"
        };
        String[] cIncludes = new String[]{
                "**/*.cpp"
        };
        String[] cppIncludes = new String[]{
                "**/*.cpp"
        };
        String[] excludes = new String[]{
                "**/frame_callback_desktop.cpp"
        };
        String cFlags = " -frtti -fexceptions";
        String cppFlags = " -std=c++11 -frtti -fexceptions";

        // PATH START AT NativeCodeGenerator JNI PARAM (check above)
        BuildConfig buildConfig = new BuildConfig(libName, "../build/tmp/realsense/target", "libs", "jni");

        BuildTarget android = BuildTarget.newDefaultTarget(BuildTarget.TargetOs.Android, SharedLibraryLoader.is64Bit, SharedLibraryLoader.isARM);
        android.cFlags += cFlags;
        android.cppFlags += cppFlags;
        android.headerDirs = headerDirs;
        android.cIncludes = cIncludes;
        android.cppIncludes = cppIncludes;
        android.cppExcludes = excludes;
        android.androidABIs = new String[]{"arm64-v8a", "armeabi-v7a", "x86", "x86_64"};
        android.androidSharedLib = "LOCAL_STATIC_LIBRARIES := librealsense2";
        android.androidAndroidMk = new String[]{
                "include $(CLEAR_VARS)",
                "LOCAL_MODULE := librealsense2",
                "ifeq ($(TARGET_ARCH_ABI), arm64-v8a)",
                "LOCAL_SRC_FILES := realsense/libs/arm64-v8a/librealsense2.so",
                "endif",
                "ifeq ($(TARGET_ARCH_ABI), armeabi-v7a)",
                "LOCAL_SRC_FILES := realsense/libs/armeabi-v7a/librealsense2.so",
                "endif",
                "ifeq ($(TARGET_ARCH_ABI), x86)",
                "LOCAL_SRC_FILES := realsense/libs/X86/librealsense2.so",
                "endif",
                "ifeq ($(TARGET_ARCH_ABI), x86_64)",
                "LOCAL_SRC_FILES := realsense/libs/X86_64/librealsense2.so",
                "endif",
                "include $(PREBUILT_SHARED_LIBRARY)", // FOR .so files
        };

        new AntScriptGenerator().generate(buildConfig, android);

        boolean macAntExecutionStatus = BuildExecutor.executeAnt("jni/build-android32.xml", "-v", "-Drelease=true", "clean", "postcompile");
        if (!macAntExecutionStatus) {
            throw new RuntimeException("Failure to execute mac ant.");
        }

        boolean antExecutionStatus = BuildExecutor.executeAnt("jni/build.xml", "-v", "pack-natives");
        if (!antExecutionStatus) {
            throw new RuntimeException("Failure to execute ant.");
        }
    }
}