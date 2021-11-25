import com.badlogic.gdx.jnigen.AntScriptGenerator;
import com.badlogic.gdx.jnigen.BuildConfig;
import com.badlogic.gdx.jnigen.BuildExecutor;
import com.badlogic.gdx.jnigen.BuildTarget;
import com.badlogic.gdx.jnigen.NativeCodeGenerator;
import com.badlogic.gdx.utils.SharedLibraryLoader;

/*
You will need to install and put on your path:
    - ant
    - mingw : https://sourceforge.net/projects/mingw-w64/
 */
public class RS2WindowsBuilder {

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
                "**/*.cpp"
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

        BuildTarget win64 = BuildTarget.newDefaultTarget(BuildTarget.TargetOs.Windows, true, false);
        win64.cppCompiler = "c++";
        win64.cFlags += cFlags;
        win64.cppFlags += cppFlags;
        win64.headerDirs = headerDirs;
        win64.cIncludes = cIncludes;
        win64.cppIncludes = cppIncludes;
        win64.cppExcludes = excludes;
        win64.compilerSuffix = ".exe";  // If you build that on windows don't forget that !
        win64.libraries = "-L./../../../../../jni/libs/win64 -lrealsense2";

        new AntScriptGenerator().generate(buildConfig, win64);

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