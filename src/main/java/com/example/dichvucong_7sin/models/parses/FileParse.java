package com.example.dichvucong_7sin.models.parses;

import com.example.dichvucong_7sin.models.data.File;

import java.util.List;

public class FileParse implements IParse<File>{
    @Override
    public String parse(List<File> models) {
        return parse(models.get(0));
    }

    @Override
    public String parse(File model) {
        return "Bạn cần có " + model.getTenGiayTo() + "<br />" +
                "Với " + model.getSoLuong() + "<br />" +
                "Bạn có thể tải giấy tờ trên tại <a href=' " + model.getToKhai() + " '></a>";
    }
}
