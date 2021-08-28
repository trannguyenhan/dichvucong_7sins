package com.example.dichvucong_7sin.services;

import com.example.dichvucong_7sin.Repositories.DataRepository;
import com.example.dichvucong_7sin.models.Data;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import support.TFIDFCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DataService implements IService<Data> {
    @Autowired
    private DataRepository dataRepository;

    // loaded
    private ArrayList<List<String>> titles = new ArrayList<>();
    private ArrayList<String> ids = new ArrayList<>();

    @Override
    public List<Data> getAll() {
        return dataRepository.findAll();
    }

    @Override
    public Data get(String id) {
        Optional<Data> oData = dataRepository.findById(new ObjectId(id));
        if(oData.isPresent()){
            return oData.get();
        }
        return null;
    }

    @Override
    public List<Data> search(String key) {
        if(titles.size() == 0) {
            loadTitles();
        }

        TFIDFCalculator cal = new TFIDFCalculator();
        ArrayList<Double> scores = new ArrayList<>();

        for (List<String> title : titles) {
            double score = 0;

            String[] words = key.split(" ");
            for (String word : words) {
                score += cal.tfIdf(title, titles, word);
            }

            scores.add(score);
        }

        double base = Double.MIN_VALUE;
        int index = -1;
        for (int i = 0; i < scores.size(); i++) {
            if(scores.get(i) > base) {
                index = i;
                base = scores.get(i);
            }
        }; if(index == -1) index = 0; // need fix, temporary solution

        String idS = ids.get(index);
        Data data = this.get(idS);

        List<Data> listData = new ArrayList<Data>();
        listData.add(data);

        return listData;
    }

    @Override
    public Data find(String key) {
        List<Data> list = this.search(key);
        if(list.size() == 0){
            return null;
        }

        return list.get(0);
    }

    private void loadTitles()
    {
        List<Data> datas = this.getAll();
        for (Data data : datas) {
            String title = data.getTitle().toLowerCase();

            StringBuilder str =  new StringBuilder(title);

            for (int i = 0; i < str.length(); i++) {
                if(str.charAt(i) == ',' || str.charAt(i) == '.' || str.charAt(i) == '?')
                {
                    str.setCharAt(i, ' ');
                }
            }

            List<String> words = List.of(str.toString().split(" "));
            titles.add(words.stream().filter(c -> (!c.isBlank())).collect(Collectors.toList()));
            ids.add(data.getId());
        }

    }
}
