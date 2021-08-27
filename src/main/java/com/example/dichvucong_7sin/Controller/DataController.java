package com.example.dichvucong_7sin.Controller;

import com.example.dichvucong_7sin.models.Data;

import com.example.dichvucong_7sin.services.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {
    @Autowired
    private IService<Data> service;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Data index(@PathVariable("id") String id){
        return this.service.get(id);
    }

    @RequestMapping(value = "/search/{decs}", method = RequestMethod.GET)
    @ResponseBody
    public Pair<String, String> search(@PathVariable("decs") String desc){
        Data data = this.service.find(desc);
        String title = data.getTitle();
        String id = data.getId();

        return Pair.of(title, id);
    }

    
}
