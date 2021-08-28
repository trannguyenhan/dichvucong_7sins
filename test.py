from utils import *
from wordEmbedding import *
import sys

print("bat dau")

def predict(text, phobert, tokenizer):
    #model = load_model('model.h5')
    X_test = word2vec(text, phobert, tokenizer)
    x_test_tensor = tf.convert_to_tensor(X_test)

    X_tests = []
    X_tests.append(x_test_tensor)

    X_tests =  tf.convert_to_tensor(X_tests)
    y = model.predict(X_tests)
    y_predict = np.argmax(y, axis=-1)

    return y_predict+1
    
    
def intiEnv():
    phobert = AutoModel.from_pretrained("vinai/phobert-base")
    tokenizer = AutoTokenizer.from_pretrained("vinai/phobert-base", use_fast=False)
    model = load_model('model.h5')

#if name == "main":
    #print("Chỗ này hơi lâu bạn đợi tí")
    #phobert = AutoModel.from_pretrained("vinai/phobert-base")
    #tokenizer = AutoTokenizer.from_pretrained("vinai/phobert-base", use_fast=False)
    #print("xong")
    #predict(sys.argv[1], phobert, tokenizer)
    #print("ok")
