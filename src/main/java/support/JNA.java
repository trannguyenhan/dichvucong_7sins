package support;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;

import java.nio.file.Path;

class TempJNA
{
    public static String pythonJNI = "";
    static
    {
        String srcDir = Path.of("").toAbsolutePath().toString();
        if(Platform.isWindows()){
            TempJNA.pythonJNI = srcDir + "/runtime/Windows/PythonJNI.dll";
        }else if(Platform.isLinux()) {
            TempJNA.pythonJNI = srcDir + "/runtime/Linux/libpythonjni.so";
        }
    }
}

public interface JNA extends Library {
    //public static String pythonJNI = "";

    public final JNA INSTANCE = (JNA)Native.load(TempJNA.pythonJNI, JNA.class);

    public String PythonExecute(String source);
    public void InitPython();
}