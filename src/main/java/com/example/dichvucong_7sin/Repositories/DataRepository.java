package com.example.dichvucong_7sin.Repositories;

import java.util.Optional;

import com.example.dichvucong_7sin.models.Data;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepository extends MongoRepository<Data, String> {
    //@Query(value = "select * from `users` where id = ?1")
    Optional<Data> findByTitle(String title);
    Optional<Data> findById(ObjectId id);
}
