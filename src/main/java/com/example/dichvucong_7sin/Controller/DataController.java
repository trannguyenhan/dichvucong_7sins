package com.example.dichvucong_7sin.Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.dichvucong_7sin.Repositories.DataRepository;
import com.example.dichvucong_7sin.models.Data;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import support.TFIDFCalculator;

@RestController
public class DataController {
    @Autowired
    private DataRepository dataRepository;

    //loaded
    ArrayList<List<String>> titles = new ArrayList<>();
    ArrayList<String> ids = new ArrayList<>();

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Data index(@PathVariable("id") String id){
        Optional<Data> data = dataRepository.findById(new ObjectId(id));

        System.out.println(id);

        if(data.isPresent())
            return data.get();
        else
            return null;
    }

    private void loadTitles()
    {
        List<Data> datas = dataRepository.findAll();
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

    @RequestMapping(value = "/search/{decs}", method = RequestMethod.GET)
    @ResponseBody
    public Pair<String, String> search(@PathVariable("decs") String decs){

        if(titles.size() == 0)
        {
            loadTitles();
        }

        TFIDFCalculator cal = new TFIDFCalculator();
        ArrayList<Double> scores = new ArrayList<>();

        for (List<String> title : titles) {
            double score = 0;

            String[] words = decs.split(" ");
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

        List<String> target = titles.get(index);

        return Pair.of(target.toString().replaceAll(", ", " "), ids.get(index));
    }

    
}
