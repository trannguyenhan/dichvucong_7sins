package com.example.dichvucong_7sin.Controller;

import java.util.Optional;

import com.example.dichvucong_7sin.Repositories.DataRepository;
import com.example.dichvucong_7sin.models.Data;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {
    @Autowired
    private DataRepository dataRepository;

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
    
}
