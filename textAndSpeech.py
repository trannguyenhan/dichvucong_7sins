import speech_recognition as sr
import pyaudio
from gtts import gTTS
import playsound

def speechtotext():
    r = sr.Recognizer()
    with sr.Microphone() as source:
        print("Mời bạn nói: ")
        audio = r.listen(source, phrase_time_limit=8)
        try:
            text = r.recognize_google(audio,language="vi-VI")
            print("Bạn -->: {}".format(text))
        except:
            print("Xin lỗi! tôi không nhận được voice!")
    return text

def texttospeech(text):
    output = gTTS(text,lang="vi", slow=False)
    output.save("output.mp3")
    playsound.playsound("output.mp3", True)
    os.remove("output.mp3")
   
while True:
    speechtotext()
