package com.example.dichvucong_7sin.Controller;

import com.example.dichvucong_7sin.models.Data;
import com.example.dichvucong_7sin.services.DataServices;
import com.example.dichvucong_7sin.services.IServices;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QueryController {
    private IServices<Data> service = new DataServices();

    @GetMapping(value = "/get/result")
    @ResponseBody
    public Data getResult(@RequestParam(value = "desc", required = false) String desc){
        Data data = service.find(desc);


        return data;
    }
}
