from utils import *

def standardize_data(row):
    # Xóa dấu chấm, phẩy, hỏi ở cuối câu
    row = re.sub(r"[\.,\?]+$-", "", row)
    # Xóa tất cả dấu chấm, phẩy, chấm phẩy, chấm thang, ... trong câu
    row = row.replace(",", " ").replace(".", " ") \
        .replace(";", " ").replace("“", " ") \
        .replace(":", " ").replace("”", " ") \
        .replace('"', " ").replace("'", " ") \
        .replace("!", " ").replace("?", " ") \
        .replace("-", " ").replace("?", " ")
    row = row.strip().lower()
    return row

def word2vec(line, phobert, tokenizer):
    max_len=100
    
    line = standardize_data(line)
    padded = underthesea.word_tokenize(line, format="text")
    padded = tokenizer.encode(padded, add_special_tokens = True)
    # Chèn thêm số 1 vào cuối câu nếu như không đủ 100 từ
    padded = padded + [1]*(max_len - len(padded))
    padded = np.array(padded)

    # Đánh dấu các từ thêm vào = 0 để không tính vào quá trình lấy features
    mask = np.where(padded == 1, 0, 1)

    mask = torch.tensor([mask])
    input_ids = torch.tensor([padded])

    with torch.no_grad():
      features = phobert(input_ids, attention_mask=mask) 
      emb_vecs = features[0].cpu().numpy()[0]
    #print(emb_vecs)
    return emb_vecs

