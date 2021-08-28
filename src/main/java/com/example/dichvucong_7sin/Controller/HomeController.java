package com.example.dichvucong_7sin.Controller;

/* import org.python.core.Py;
import org.python.core.PyString;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter; */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import support.JNA;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;

import java.nio.file.Path;

import com.example.dichvucong_7sin.Repositories.DataRepository;



//@Controller
@RestController
public class HomeController {

    JNA jna = null;

    @RequestMapping(value = "/hello/{src}", method = RequestMethod.GET)
    @ResponseBody
    public String index(@PathVariable("src") String src){

        if(jna == null)
        {
            String srcDir = Path.of("").toAbsolutePath().toString();

            Runtime.getRuntime().load((Platform.isWindows() ? 
                srcDir + "\\runtime\\Windows\\Python37.dll" 
                    : "<path to *.so file>"));

            Runtime.getRuntime().load((Platform.isWindows() ? 
                srcDir + "\\runtime\\Windows\\Python3.dll" 
                    : "<path to *.so file>"));

            jna = Native.load((Platform.isWindows() ? 
                srcDir + "\\runtime\\Windows\\PythonJNI.dll" 
                    : "<path to *.so file>"), JNA.class);
        }

        /* String ret = jna.PythonExecute(
              "import os\n"
            + "from bs4 import BeautifulSoup\n"
            + "print('Hello World')"
            ); */

        src = src.replaceAll("--", "\n");
        String ret = jna.PythonExecute(src);
    
        return ret;
    }
}
