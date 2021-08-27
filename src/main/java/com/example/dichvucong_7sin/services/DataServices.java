package com.example.dichvucong_7sin.services;

import com.example.dichvucong_7sin.Repositories.DataRepository;
import com.example.dichvucong_7sin.models.Data;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import support.TFIDFCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DataServices implements IServices<Data> {
    @Autowired
    private DataRepository dataRepository;

    //loaded
    ArrayList<List<String>> titles = new ArrayList<>();
    ArrayList<String> ids = new ArrayList<>();

    @Override
    public List<Data> getAll() {
        List<Data> listData = dataRepository.findAll();
        return listData;
    }

    @Override
    public Data get(String id) {
        Optional<Data> data = dataRepository.findById(new ObjectId(id));
        if(data.isPresent()){
            return data.get();
        } else {
            return null;
        }
    }

    @Override
    public List<Data> search(String keySearch) {
        if(titles.size() == 0) {
            loadTitles();
        }

        TFIDFCalculator cal = new TFIDFCalculator();
        ArrayList<Double> scores = new ArrayList<>();

        for (List<String> title : titles) {
            double score = 0;

            String[] words = keySearch.split(" ");
            for (String word : words) {
                score += cal.tfIdf(title, titles, word);
            }

            scores.add(score);
        }

        double base = Double.MIN_VALUE;
        int index = -1;
        for (int i = 0; i < scores.size(); i++) {
            if(scores.get(i) > base)
            {
                index = i;
                base = scores.get(i);
            }
        }

        List<String> target = titles.get(index); // must return title
        String idS = ids.get(index);

        List<Data> listResults = new ArrayList<Data>();
        listResults.add(this.get(idS));
        return listResults;
    }

    @Override
    public Data find(String keySearch) {
        return this.search(keySearch).get(0);
    }

    /**
     * Initialize titles and ids
     */
    private void loadTitles() {
        List<Data> listData = dataRepository.findAll();
        for (Data data : listData) {
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
