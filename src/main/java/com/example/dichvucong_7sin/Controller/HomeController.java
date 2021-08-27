package com.example.dichvucong_7sin.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dichvucong_7sin.Repositories.DataRepository;

//@Controller
@RestController
public class HomeController {
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String index(){
        return "index";
    }
}
