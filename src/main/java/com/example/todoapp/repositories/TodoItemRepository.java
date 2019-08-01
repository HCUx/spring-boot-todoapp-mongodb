package com.example.todoapp.repositories;

import com.example.todoapp.models.TodoItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoItemRepository extends MongoRepository<TodoItem, String> {
    @Query("{ 'parentListId': ?0}")
    List<TodoItem> findAllListsItems(String parentListId);
}