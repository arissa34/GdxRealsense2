import com.badlogic.gdx.jnigen.AntScriptGenerator;
import com.badlogic.gdx.jnigen.BuildConfig;
import com.badlogic.gdx.jnigen.BuildExecutor;
import com.badlogic.gdx.jnigen.BuildTarget;
import com.badlogic.gdx.jnigen.NativeCodeGenerator;
import com.badlogic.gdx.utils.SharedLibraryLoader;

public class RS2DesktopBuilder {

    public static void main(String[] args) throws Exception {

        String libName = "rs2";

        String[] classes = new String[]{
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
        };

        new NativeCodeGenerator().generate(
                "src/main/java/",
                "build/classes/java/main",
                "jni/realsense",
                classes,
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
        mac64.cppCompiler = "ccache_g++";  // I used this trick : https://github.com/libgdx/libgdx/wiki/jnigen#ccache
        mac64.cFlags += cFlags;
        mac64.cppFlags += cppFlags;
        mac64.headerDirs = headerDirs;
        mac64.cIncludes = cIncludes;
        mac64.cppIncludes = cppIncludes;
        mac64.cppExcludes = excludes;
        mac64.libraries = "-Wl,-rpath /usr/local/lib -L/usr/local/lib -lrealsense2";

        BuildTarget win64 = BuildTarget.newDefaultTarget(BuildTarget.TargetOs.Windows, true, false);
        win64.compilerPrefix = "ccache_x86_64-w64-mingw32-";   // I used this trick : https://github.com/libgdx/libgdx/wiki/jnigen#ccache
        win64.cppCompiler = "g++";
        win64.cCompiler = "gcc";
        win64.cFlags += cFlags;
        win64.cppFlags += cppFlags;
        win64.headerDirs = headerDirs;
        win64.cIncludes = cIncludes;
        win64.cppIncludes = cppIncludes;
        win64.cppExcludes = excludes;
        //win64.compilerSuffix = ".exe"; // If you build that on windows don't forget that ! (I build that on mac so I don't need it)
        win64.libraries = "-L./../../../../../jni/realsense/libs/win64 -lrealsense2";

        new AntScriptGenerator().generate(buildConfig, mac64, win64);

        boolean macAntExecutionStatus = BuildExecutor.executeAnt("jni/build-macosx64.xml", "-v", "-Drelease=true", "clean", "postcompile");
        if (!macAntExecutionStatus) {
            throw new RuntimeException("Failure to execute mac ant.");
        }

        boolean winAntExecutionStatus = BuildExecutor.executeAnt("jni/build-windows64.xml", "-v", "-Drelease=true", "clean", "postcompile");
        if (!winAntExecutionStatus) {
            throw new RuntimeException("Failure to execute windows ant.");
        }

        boolean antExecutionStatus = BuildExecutor.executeAnt("jni/build.xml", "-v", "pack-natives");
        if (!antExecutionStatus) {
            throw new RuntimeException("Failure to execute ant.");
        }
    }
}