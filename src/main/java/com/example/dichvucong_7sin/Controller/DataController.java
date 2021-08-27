package com.example.dichvucong_7sin.Controller;

import com.example.dichvucong_7sin.models.Data;

import com.example.dichvucong_7sin.services.DataServices;
import com.example.dichvucong_7sin.services.IServices;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {
    private IServices<Data> service = new DataServices();

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Data get(@PathVariable("id") String id){
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
