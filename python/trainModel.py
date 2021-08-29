from utils import *
from wordEmbedding import *
    
data = pd.read_csv("intent2.csv")
X=data['user say'].values
y=data['action'].values-1

phobert = AutoModel.from_pretrained("vinai/phobert-base")
tokenizer = AutoTokenizer.from_pretrained("vinai/phobert-base", use_fast=False)

for i in range(len(X)):
  X[i]=word2vec(X[i], phobert, tokenizer)

y_tensor = tf.convert_to_tensor(y, dtype=tf.int64) 
x_tensor = []
for i in range(len(X)):
  x_tensor.append(tf.convert_to_tensor(X[i]))

x_tensor = tf.convert_to_tensor(x_tensor)

model = Sequential()

model.add(Conv1D(128, kernel_size=4, input_shape=X[0].shape))
model.add(Dropout(0.2))
model.add(Conv1D(64, kernel_size=2, activation='relu'))
model.add(Dropout(0.2))
model.add(GlobalMaxPooling1D())
model.add(Dense(64, activation='relu'))
model.add(Dense(max(y)+1, activation='softmax'))

model.compile(optimizer='adam', loss='sparse_categorical_crossentropy', metrics=['accuracy'])
model.summary()

model.fit(x_tensor, y_tensor, epochs=10, verbose=1, use_multiprocessing=True, batch_size=8)
model.save("model.h5")
print("Saved model to disk")




    
