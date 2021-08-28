package support;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;

public interface JNA extends Library {
    public String PythonExecute(String source);
}