package com.example.dichvucong_7sin.Controller;

import com.example.dichvucong_7sin.models.Data;
import com.example.dichvucong_7sin.services.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QueryController {
    @Autowired
    private IService<Data> service;

    @GetMapping(value = "/result")
    @ResponseBody
    public List<Data> show(@RequestParam(value = "key", required = false) String key){
        if(key == null){
            return service.getAll();
        }

        return service.search(key);
    }
}
