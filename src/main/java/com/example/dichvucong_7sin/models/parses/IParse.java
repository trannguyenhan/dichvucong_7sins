package com.example.dichvucong_7sin.models.parses;

import java.util.List;

public interface IParse<T> {
    String parse(List<T> models);
    String parse(T model);
}
