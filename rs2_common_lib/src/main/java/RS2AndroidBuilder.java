import com.badlogic.gdx.jnigen.AntScriptGenerator;
import com.badlogic.gdx.jnigen.BuildConfig;
import com.badlogic.gdx.jnigen.BuildExecutor;
import com.badlogic.gdx.jnigen.BuildTarget;
import com.badlogic.gdx.jnigen.NativeCodeGenerator;
import com.badlogic.gdx.utils.SharedLibraryLoader;

public class RS2AndroidBuilder {

    public static void main(String[] args) throws Exception {

        String libName = "rs2";

        String[] classes = new String[]{
                "**/*.java",
        };

        String[] excludeClasses = new String[]{
                "**/DeviceWatcher.java",
                "**/DebugProtocol.java",
                "**/UpdateDevice.java",
                "**/Updatable.java",

                "**/RS2AndroidBuilder.java",
                "**/RS2DesktopBuilder.java",
                "**/RS2MacOSBuilder.java",
                "**/RS2WindowsBuilder.java"
        };
        new NativeCodeGenerator().generate(
                "src/main/java/",
                "build/classes/java/main",
                "jni/cpp/jnigen",
                classes,
                excludeClasses
        );
        String[] headerDirs = new String[]{
                "cpp/jnigen",
                "cpp/realsense",
                "cpp/realsense/include"
        };
        String[] cIncludes = new String[]{
                "**/*.c"
        };
        String[] cppIncludes = new String[]{
                "**/*.cpp"
        };
        String[] excludes = new String[]{
                "**/frame_callback_desktop.cpp"
        };
        String cFlags = " -w -frtti -fexceptions";
        String cppFlags = " -w -std=c++11 -frtti -fexceptions";

        BuildConfig buildConfig = new BuildConfig(libName, "../build/tmp/realsense/target", "libs", "jni");

        BuildTarget android = BuildTarget.newDefaultTarget(BuildTarget.TargetOs.Android, SharedLibraryLoader.is64Bit, SharedLibraryLoader.isARM);
        android.cFlags += cFlags;
        android.cppFlags += cppFlags;
        android.headerDirs = headerDirs;
        android.cIncludes = cIncludes;
        android.cppIncludes = cppIncludes;
        android.cppExcludes = excludes;
        //android.androidNdkSuffix = ".cmd"; // If you build that on windows don't forget that ! (I build that on mac so I don't need it)
        android.androidABIs = new String[]{"arm64-v8a", "armeabi-v7a", "x86", "x86_64"};
        android.androidSharedLib = "LOCAL_STATIC_LIBRARIES := librealsense2";
        android.androidAndroidMk = new String[]{
                "include $(CLEAR_VARS)",
                "LOCAL_MODULE := librealsense2",
                "ifeq ($(TARGET_ARCH_ABI), arm64-v8a)",
                "LOCAL_SRC_FILES := libs/arm64-v8a/librealsense2.so",
                "endif",
                "ifeq ($(TARGET_ARCH_ABI), armeabi-v7a)",
                "LOCAL_SRC_FILES := libs/armeabi-v7a/librealsense2.so",
                "endif",
                "ifeq ($(TARGET_ARCH_ABI), x86)",
                "LOCAL_SRC_FILES := libs/X86/librealsense2.so",
                "endif",
                "ifeq ($(TARGET_ARCH_ABI), x86_64)",
                "LOCAL_SRC_FILES := libs/X86_64/librealsense2.so",
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