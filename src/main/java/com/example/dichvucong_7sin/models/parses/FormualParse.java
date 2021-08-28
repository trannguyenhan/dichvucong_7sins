package com.example.dichvucong_7sin.models.parses;

import com.example.dichvucong_7sin.models.data.Formula;

import java.util.List;

public class FormualParse implements IParse<Formula> {
    @Override
    public String parse(List<Formula> models) {
        String result = "Các hình thức mà bạn có thể là : ";
        for(Formula model : models){
            result = result + model.getHinhThucNop() + ", ";
        }
        return result;
    }

    @Override
    public String parse(Formula model) {
        return "Bạn có thể nộp bằng hình thức " + model.getHinhThucNop() + "<br />" +
                "Với thời hạn giải quyết là " + model.getThoiHanGiaiQuyet() + "<br />" +
                model.getLePhi() + "<br />" +
                "Lưu ý: " + model.getMoTa();
    }
}
