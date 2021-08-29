# Load libraries
import flask
from flask import request, render_template, request, redirect, send_from_directory, url_for

from utils import *
from wordEmbedding import *
import speech_recognition as sr
import pyaudio
from gtts import gTTS
import playsound

# instantiate flask 
app = flask.Flask(__name__)

phobert = AutoModel.from_pretrained("vinai/phobert-base")
tokenizer = AutoTokenizer.from_pretrained("vinai/phobert-base", use_fast=False)

# define a predict function as an endpoint 
@app.route("/predict/<text>", methods=["GET","POST"])
def predict(text):
    model = load_model('model.h5')
    X_test = word2vec(text, phobert, tokenizer)

    X_tests = []
    X_tests.append(X_test)

    X_tests =  tf.convert_to_tensor(X_tests)
    y = model.predict(X_tests)
    y_predict = np.argmax(y)

    print(y_predict+1)   
    return str(y_predict+1)

@app.route("/speechtotext", methods=["GET","POST"])
def speechtotext():
    r = sr.Recognizer()
    with sr.Microphone() as source:
        print("Mời bạn nói: ")
        audio = r.listen(source, phrase_time_limit=8)
        try:
            text = r.recognize_google(audio,language="vi-VI")
            print("Bạn -->: {}".format(text))
            return text
        except:
            print("Xin lỗi! tôi không nhận được voice!",audio)
    return "no no"

@app.route("/textToSpeech/<text>", methods=["GET","POST"])
def texttospeech(text):
    output = gTTS(text,lang="vi", slow=False)
    output.save("output.mp3")
    playsound.playsound("output.mp3", True)
    os.remove("output.mp3")
    return "ok"











# start the flask app, allow remote connections 
app.run(host='0.0.0.0')
