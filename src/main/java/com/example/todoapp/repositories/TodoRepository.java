package com.example.todoapp.repositories;

import com.example.todoapp.models.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends MongoRepository<Todo, String> {
    @Query("{ 'ownerid': ?0}")
    List<Todo> findAllUsersList(String parentListId);
}