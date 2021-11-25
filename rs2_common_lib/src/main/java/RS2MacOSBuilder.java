import com.badlogic.gdx.jnigen.AntScriptGenerator;
import com.badlogic.gdx.jnigen.BuildConfig;
import com.badlogic.gdx.jnigen.BuildExecutor;
import com.badlogic.gdx.jnigen.BuildTarget;
import com.badlogic.gdx.jnigen.NativeCodeGenerator;
import com.badlogic.gdx.utils.SharedLibraryLoader;

public class RS2MacOSBuilder {

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
                "**/frame_callback_android.cpp"
        };
        String cFlags = " -w -frtti -fexceptions";
        String cppFlags = " -w -std=c++11 -frtti -fexceptions";

        BuildConfig buildConfig = new BuildConfig(libName, "../build/tmp/realsense/target", "libs", "jni");

        BuildTarget mac64 = BuildTarget.newDefaultTarget(BuildTarget.TargetOs.MacOsX, SharedLibraryLoader.is64Bit, SharedLibraryLoader.isARM);
        mac64.cCompiler = "ccache_clang"; // I used this trick : https://github.com/libgdx/libgdx/wiki/jnigen#ccache
        mac64.cppCompiler = "ccache_clang++"; // I used this trick : https://github.com/libgdx/libgdx/wiki/jnigen#ccache
        mac64.cFlags += cFlags;
        mac64.cppFlags += cppFlags;
        mac64.headerDirs = headerDirs;
        mac64.cIncludes = cIncludes;
        mac64.cppIncludes = cppIncludes;
        mac64.cppExcludes = excludes;
        mac64.libraries = "-Wl,-rpath /usr/local/lib -L/usr/local/lib -lrealsense2";

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