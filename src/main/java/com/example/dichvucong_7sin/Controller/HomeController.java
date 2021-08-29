package com.example.dichvucong_7sin.Controller;

import com.example.dichvucong_7sin.models.Data;
import com.example.dichvucong_7sin.models.Search;
import com.example.dichvucong_7sin.models.parses.FileParse;
import com.example.dichvucong_7sin.models.parses.FormualParse;
import com.example.dichvucong_7sin.services.IService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import support.NormalizeText;

import javax.validation.Valid;
import java.io.IOException;

@Controller
public class HomeController {
    @Autowired
    private IService<Data> service;

    @GetMapping(value = "/")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("search", new Search());
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @PostMapping(value = "/")
    public ModelAndView rs(@Valid Search key){

        Document document = null;
        try {
            document = Jsoup.connect("http://localhost:5000/predict/" + key.getStr()).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String result = "";
        if(document == null){
            result = "Not enable serve";
        } else {
            String ret = document.text();
            Data data = service.find(key.getStr());

            if(ret.equals("1")){
                result = new FileParse().parse(data.getThanhPhanHoSo());
            } else if(ret.equals("2")){
                result = NormalizeText.run(data.getTrinhTuThucHien());
            } else if(ret.equals("3")){
                result = data.getCoQuanThucHien();
            } else if(ret.equals("4")){
                result = NormalizeText.run(new FormualParse().parse(data.getCachThucThucHien()));
            }
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("search", new Search(key.getStr()));
        //modelAndView.addObject("q", key.getStr());
        modelAndView.addObject("rs", result);
        modelAndView.setViewName("result");
        return modelAndView;
    }

}

