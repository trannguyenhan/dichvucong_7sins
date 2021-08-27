package com.example.dichvucong_7sin.services;

import java.util.List;

public interface IService<T> {
    /**
     * Get all element of models T
     * @return list of T
     */
    List<T> getAll();

    /**
     * Find element have key is id
     * @param id is primary key of model T
     * @return instance T have key is id
     */
    T get(String id);

    /**
     * Search list model T have description with key search
     * @param key String
     * @return list model T
     */
    List<T> search(String key);

    /**
     * Find one instance best match with key search
     * @param key String
     * @return model T
     */
    T find(String key);
}
