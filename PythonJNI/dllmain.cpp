// dllmain.cpp : Defines the entry point for the DLL application.
//#include "pch.h"
#include <Python.h>
#include <iostream>
#include <string>
#include <sstream>

#define DllExport   __attribute__((visibility("default")))

//std::stringstream buffer;
//std::streambuf* old = nullptr;
std::string ret;

PyObject* output = nullptr;
PyObject* catcher = nullptr;

void _InitPython()
{
    std::string stdOutErr =
"import sys\n\
class CatchOutErr:\n\
    def __init__(self):\n\
        self.value = 'xyz'\n\
    def write(self, txt):\n\
        self.value += txt\n\
catchOutErr = CatchOutErr()\n\
#sys.stdout = catchOutErr\n\
#sys.stderr = catchOutErr\n\
"; 

    Py_Initialize();

    PyObject* pModule = PyImport_AddModule("__main__"); 
    PyRun_SimpleString(stdOutErr.c_str());
    //PyRun_SimpleString("print(1+1)");
    //PyRun_SimpleString("1+a");
    catcher = PyObject_GetAttrString(pModule, "catchOutErr");

    PyErr_Print();
}

void GetOutput()
{
    output = PyObject_GetAttrString(catcher, "value");
    if (PyUnicode_Check(output)) {
        PyObject* temp_bytes = PyUnicode_AsEncodedString(output, "UTF-8", "strict"); // Owned reference
        if (temp_bytes != NULL)
        {
            auto my_result = PyBytes_AS_STRING(temp_bytes); // Borrowed pointer
            //my_result = strdup(my_result);

            //std::cout << my_result << '\n';
            ret = my_result;
           

            Py_DECREF(temp_bytes);
        }
        else
        {
            ret = "python return null.";
        }
    }
    Py_DECREF(output);
}

void Cls()
{
    //PyObject_SetAttrString(catcher, "value", PyLong_FromLong(0));
    PyRun_SimpleString("catchOutErr.value = 'null'\n");
}

extern "C"
{

DllExport char* PythonExecute(char* source)
{
    Cls();
    PyRun_SimpleString(source);
    GetOutput();

    return &ret[0];
}

DllExport void InitPython()
{
     _InitPython();
}

}

#ifdef _WIN32
BOOL APIENTRY DllMain( HMODULE hModule,
                       DWORD  ul_reason_for_call,
                       LPVOID lpReserved
                     )
{
    
    switch (ul_reason_for_call)
    {
    case DLL_PROCESS_ATTACH:
    {
        InitPython();

        std::cout << "DLL Loaded.\n";
    }
    break;
    case DLL_THREAD_ATTACH:
    break;
    case DLL_THREAD_DETACH:
    break;
    case DLL_PROCESS_DETACH:
    {
        Py_Finalize();

        std::cout << "DLL Unloaded.\n";
    }
    break;
    }

    return TRUE;
}
#endif
