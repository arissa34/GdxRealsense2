import com.badlogic.gdx.jnigen.AntScriptGenerator;
import com.badlogic.gdx.jnigen.BuildConfig;
import com.badlogic.gdx.jnigen.BuildExecutor;
import com.badlogic.gdx.jnigen.BuildTarget;
import com.badlogic.gdx.jnigen.NativeCodeGenerator;
import com.badlogic.gdx.utils.SharedLibraryLoader;

public class RS2MacOSBuilder {

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
                "**/frame_callback_android.cpp"
        };
        String cFlags = " -frtti -fexceptions";
        String cppFlags = " -std=c++11 -frtti -fexceptions";

        // PATH START AT NativeCodeGenerator JNI PARAM (check above)
        BuildConfig buildConfig = new BuildConfig(libName, "../build/tmp/realsense/target", "libs", "jni");

        BuildTarget mac64 = BuildTarget.newDefaultTarget(BuildTarget.TargetOs.MacOsX, SharedLibraryLoader.is64Bit, SharedLibraryLoader.isARM);
        mac64.cFlags += cFlags;
        mac64.cppFlags += cppFlags;
        mac64.headerDirs = headerDirs;
        mac64.cIncludes = cIncludes;
        mac64.cppIncludes = cppIncludes;
        mac64.cppExcludes = excludes;
        mac64.libraries = "-Wl,-rpath /Library/Realsense/lib -L/Library/Realsense/lib -lrealsense2";

        new AntScriptGenerator().generate(buildConfig, mac64);

        boolean macAntExecutionStatus = BuildExecutor.executeAnt("jni/build-macosx64.xml", "-v", "-Drelease=true", "clean", "postcompile");
        if (!macAntExecutionStatus) {
            throw new RuntimeException("Failure to execute mac ant.");
        }

        boolean antExecutionStatus = BuildExecutor.executeAnt("jni/build.xml", "-v", "pack-natives");
        if (!antExecutionStatus) {
            throw new RuntimeException("Failure to execute ant.");
        }
    }
}