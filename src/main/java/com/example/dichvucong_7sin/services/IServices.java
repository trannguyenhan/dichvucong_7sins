package com.example.dichvucong_7sin.services;

import com.example.dichvucong_7sin.models.Data;

import java.util.List;

public interface IServices<T> {
    /**
     * Get all data in Database (list data)
     */
    List<T> getAll();

    /**
     * Get data with id
     */
    T get(String id);

    /**
     * Get list data with key search
     */
    List<T> search(String keySearch);

    /**
     * Get data with key search
     */
    T find(String keySearch);
}
