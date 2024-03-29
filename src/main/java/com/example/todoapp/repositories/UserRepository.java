package com.example.todoapp.repositories;

import com.example.todoapp.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    @Query("{ 'username': ?0}")
    User findOneUser(String username);
}