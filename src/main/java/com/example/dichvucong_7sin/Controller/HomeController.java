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
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Semaphore;

import com.example.dichvucong_7sin.Repositories.DataRepository;


//@Controller
@RestController
public class HomeController {

    JNA jna = null;
    private static final Semaphore mutex = new Semaphore(1);
    private static Thread thr = null;
    private static final Object lock = new Object();
    private static final Object lock2 = new Object();

    private static Queue<String> request = new ArrayDeque<String>();
    private static Queue<String> response = new ArrayDeque<String>();

    public HomeController(){
        thr = new Thread(() -> {
            while(true){
                /*try {
                    mutex.acquire();
                    try {
                        System.out.println(process("hahahaha"));
                    } finally {
                        mutex.release();
                    }
                } catch(InterruptedException ie) {
                    System.out.println(ie);
                }*/
                synchronized (lock){
                    while(request.size() != 0){
                        String desc = request.poll();
                        String ret = process(desc);
                        System.out.println(desc+ ": " + ret);

                        /*synchronized (lock2) {
                            response.add(ret);
                        }*/
                        try {
                            mutex.acquire();
                            try {
                                response.add(ret);
                            } finally {
                                mutex.release();
                            }
                        } catch(InterruptedException ie) {
                            System.out.println(ie);
                        }
                    }

                }

                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });

        thr.start();
    }


    private String process(String desc)
    {
        if(jna == null)
        {

            String srcDir = Path.of("").toAbsolutePath().toString();

            String pathpython3v = "";
            String pathpython3 = "";
            String pythonJNI = "";

            if(Platform.isWindows()){
                pathpython3v = srcDir + "/runtime/Windows/Python37.dll";
                pathpython3 = srcDir + "/runtime/Windows/Python3.dll";
                pythonJNI = srcDir + "/runtime/Windows/PythonJNI.dll";
                Runtime.getRuntime().load(pathpython3v);
                jna = Native.load(pythonJNI, JNA.class);
            }else if(Platform.isLinux()){
                pathpython3v = "/usr/lib/x86_64-linux-gnu/libpython3.8.so";

                pythonJNI = srcDir + "/runtime/Linux/libpythonjni.so";

                Runtime.getRuntime().load(pathpython3v);

                jna = JNA.INSTANCE;
                jna.InitPython();
            }

            //read python file
            String script =
                    //"import sys\n" +
                    //"sys.stdout = sys.__stdout__\n" +
                    //"sys.stderr = sys.__stderr__\n" +
                    //"catchOutErr = CatchOutErr()\n" +
                    "import os\n" +
                    "os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'\n" +
                    "import torch\n" +
                    "from transformers import AutoModel, AutoTokenizer\n" +
                    "import underthesea\n" +
                    "import regex as re\n" +
                    "\n" +
                    "import pandas as pd\n" +
                    "import numpy as np\n" +
                    "import matplotlib.pyplot as plt\n" +
                    "import pickle\n" +
                    "import tensorflow as tf\n" +
                    "from tensorflow import keras\n" +
                    "from keras.preprocessing.text import Tokenizer\n" +
                    "from keras.preprocessing.sequence import pad_sequences\n" +
                    "from keras.models import Sequential\n" +
                    "from keras import Input\n" +
                    "from keras.layers import Flatten, Conv2D, MaxPool2D, Conv1D, GlobalMaxPooling1D, Dropout, Dense, BatchNormalization\n" +
                    "from keras.models import load_model\n" +
                    "from keras import models\n" +

                    "from random import randint\n" +
                    "import gc\n" +

                    "import asyncio\n" +

                    "def standardize_data(row):\n" +
                    "    # Xóa dấu chấm, phẩy, hỏi ở cuối câu\n" +
                    "    row = re.sub(r\"[\\.,\\?]+$-\", \"\", row)\n" +
                    "    # Xóa tất cả dấu chấm, phẩy, chấm phẩy, chấm thang, ... trong câu\n" +
                    "    row = row.replace(\",\", \" \").replace(\".\", \" \") \\\n" +
                    "        .replace(\";\", \" \").replace(\"“\", \" \") \\\n" +
                    "        .replace(\":\", \" \").replace(\"”\", \" \") \\\n" +
                    "        .replace('\"', \" \").replace(\"'\", \" \") \\\n" +
                    "        .replace(\"!\", \" \").replace(\"?\", \" \") \\\n" +
                    "        .replace(\"-\", \" \").replace(\"?\", \" \")\n" +
                    "    row = row.strip().lower()\n" +
                    "    return row\n" +
                    "\n" +
                    "def word2vec(line, phobert, tokenizer):\n" +
                    "    max_len=100\n" +
                    "    \n" +
                    "    line = standardize_data(line)\n" +
                    "    padded = underthesea.word_tokenize(line, format=\"text\")\n" +
                    "    padded = tokenizer.encode(padded, add_special_tokens = True)\n" +
                    "    # Chèn thêm số 1 vào cuối câu nếu như không đủ 100 từ\n" +
                    "    padded = padded + [1]*(max_len - len(padded))\n" +
                    "    padded = np.array(padded)\n" +
                    "\n" +
                    "    # Đánh dấu các từ thêm vào = 0 để không tính vào quá trình lấy features\n" +
                    "    mask = np.where(padded == 1, 0, 1)\n" +
                    "\n" +
                    "    mask = torch.tensor([mask])\n" +
                    "    input_ids = torch.tensor([padded])\n" +
                    "\n" +
                    "    with torch.no_grad():\n" +
                    "      features = phobert(input_ids, attention_mask=mask) \n" +
                    "      emb_vecs = features[0].cpu().numpy()[0]\n" +
                    "    #print(emb_vecs)\n" +
                    "    return emb_vecs" +


                    "\n" +
                    "print(\"bat dau\")\n" +
                    "\n" +
                    "def predict2(text):\n" +
                    //"    #model = load_model('model.h5')\n" +
                    //"    global model1\n" +
                    //"    if model1 is not None: tf.keras.backend.clear_session()\n" +
                    //"    model1 = load_model('model.h5')\n" +
                    "    X_test = word2vec(text, phobert, tokenizer)\n" +
                    "    x_test_tensor = tf.convert_to_tensor(X_test)\n" +
                    "\n" +
                    "    X_tests = []\n" +
                    "    X_tests.append(x_test_tensor)\n" +
                    "\n" +
                    "    X_tests =  tf.convert_to_tensor(X_tests)\n" +
                    "    #y = model1.predict_classes(X_tests)\n" +
                    "    y = [0]\n" +
                    "    y = model1.predict(X_tests)\n" +
                    "    y_predict = np.argmax(y, axis=-1)\n" +
                    "\n" +
                    "    #catchOutErr.value = str(y_predict[0] + 1)\n" +
                    "    return str(y_predict[0] + 1)\n" +
                    "    #return str(model1.summary())\n" +
                    "    \n" +
                    "    \n\n" +

                    "async def callPredict(text):\n" +
                    "    try:\n" +
                    "        catchOutErr.value = str(randint(0, 5))\n" +
                    "        result = await asyncio.wait_for(predict2(text), timeout=3.0) \n" +
                    "        return result\n" +
                    "    except asyncio.TimeoutError:\n" +
                    "        catchOutErr.value = str(randint(0, 5))\n" +
                    "        return None\n\n" +

                    "phobert = AutoModel.from_pretrained(\"vinai/phobert-base\")\n" +
                    "tokenizer = AutoTokenizer.from_pretrained(\"vinai/phobert-base\", use_fast=False)\n" +
                    "model1 = keras.models.load_model('model.h5')\n" +

                    "class Obj2:\n" +
                    "    def standardize_data(self, row):\n" +
                    "        # Xóa dấu chấm, phẩy, hỏi ở cuối câu\n" +
                    "        row = re.sub(r\"[\\.,\\?]+$-\", \"\", row)\n" +
                    "    # Xóa tất cả dấu chấm, phẩy, chấm phẩy, chấm thang, ... trong câu\n" +
                    "        row = row.replace(\",\", \" \").replace(\".\", \" \") \\\n" +
                    "            .replace(\";\", \" \").replace(\"“\", \" \") \\\n" +
                    "            .replace(\":\", \" \").replace(\"”\", \" \") \\\n" +
                    "            .replace('\"', \" \").replace(\"'\", \" \") \\\n" +
                    "            .replace(\"!\", \" \").replace(\"?\", \" \") \\\n" +
                    "            .replace(\"-\", \" \").replace(\"?\", \" \")\n" +
                    "        row = row.strip().lower()\n" +
                    "        return row\n\n" +
                    "    def wordVec(self, text):\n" +
                    "        max_len=100\n" +
                    "    \n" +
                    "        line = self.standardize_data(text)\n" +
                    "        padded = underthesea.word_tokenize(line, format=\"text\")\n" +
                    "        padded = tokenizer.encode(padded, add_special_tokens = True)\n" +
                    "    # Chèn thêm số 1 vào cuối câu nếu như không đủ 100 từ\n" +
                    "        padded = padded + [1]*(max_len - len(padded))\n" +
                    "        padded = np.array(padded)\n" +
                    "\n" +
                    "    # Đánh dấu các từ thêm vào = 0 để không tính vào quá trình lấy features\n" +
                    "        mask = np.where(padded == 1, 0, 1)\n" +
                    "\n" +
                    "        mask = torch.tensor([mask])\n" +
                    "        input_ids = torch.tensor([padded])\n" +
                    "\n" +
                    "        with torch.no_grad():\n" +
                    "            features = phobert(input_ids, attention_mask=mask) \n" +
                    "            emb_vecs = features[0].cpu().numpy()[0]\n" +
                    "    #print(emb_vecs)\n" +
                    "        return emb_vecs\n\n" +
                    "    def func(self, text, mol):\n" +
                    "        X_test = self.wordVec(text)\n" +
                    "        x_test_tensor = tf.convert_to_tensor(X_test)\n" +
                    "\n" +
                    "        X_tests = []\n" +
                    "        X_tests.append(x_test_tensor)\n" +
                    "\n" +
                    "        X_tests =  tf.convert_to_tensor(X_tests)\n" +
                    "        #model1 = keras.models.load_model('model.h5')\n"+
                    "        y = mol.predict(X_tests)\n" +
                    "        #y_predict = np.argmax(y, axis=-1)\n" +
                    "\n" +
                    "        #return y_predict+1\n\n" +
                    "        return str(X_tests)\n\n" +
                    "obj2 = Obj2()";

            System.out.println(jna.PythonExecute(script));


        }

        return jna.PythonExecute("catchOutErr.value = str(predict2('" + desc + "'))");
    }

    @RequestMapping(value = "/predict/{desc}", method = RequestMethod.GET)
    @ResponseBody
    public String index(@PathVariable("desc") String desc){
        String ret = "null";

        synchronized (lock){
            request.add(desc);
            ret = "Accepted.";
        }

        while (response.size() == 0) {
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            mutex.acquire();
            try {
                ret = response.poll();
            } finally {
                mutex.release();
            }
        } catch (InterruptedException ie) {
            System.out.println(ie);
        }

        return ret;
    }
}
