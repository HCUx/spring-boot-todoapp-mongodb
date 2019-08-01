package com.example.todoapp.controllers;

import com.example.todoapp.models.Todo;
import com.example.todoapp.models.TodoItem;
import com.example.todoapp.models.User;
import com.example.todoapp.repositories.TodoItemRepository;
import com.example.todoapp.repositories.TodoRepository;
import com.example.todoapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class TodoController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    TodoRepository todoRepository;
    @Autowired
    TodoItemRepository todoItemRepository;

    @PostMapping("/register")
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<User> loginUser(@RequestHeader String Authorization) {
        String[] up = Authorization.split(":");
        User user = userRepository.findOneUser(up[0]);
        if (user != null)
            return ResponseEntity.ok().body(user);
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/todos/{ownerid}")
    public List<Todo> getAllUsersTodo(@PathVariable("ownerid") String ownerid) {
        return todoRepository.findAllUsersList(ownerid);
    }

    @PostMapping("/todos")
    public ResponseEntity createTodo(@Valid @RequestBody Todo todo) {
         try {
             todoRepository.insert(todo);
             return ResponseEntity.ok().body(true);
         }catch (Exception ignored){
             return ResponseEntity.ok().body(false);
         }
    }

    @GetMapping(value="/todo/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable("id") String id) {
        return todoRepository.findById(id)
                .map(todo -> ResponseEntity.ok().body(todo))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(value="/todos/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable("id") String id,
                                           @Valid @RequestBody Todo todo) {
        return todoRepository.findById(id)
                .map(todoData -> {
                    todoData = todo;
                    Todo updatedTodo = todoRepository.save(todoData);
                    return ResponseEntity.ok().body(true);
                }).orElse(ResponseEntity.ok().body(false));
    }

    @DeleteMapping(value="/todos/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable("id") String id) {
        return todoRepository.findById(id)
                .map(todo -> {
                    todoRepository.deleteById(id);
                    return ResponseEntity.ok().body(true);
                }).orElse(ResponseEntity.ok().body(false));
    }

    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////


    @GetMapping(value="/getitems/{parentListId}")
    public List<TodoItem> getTodoItemByOwnerId(@PathVariable("parentListId") String id) {
        return todoItemRepository.findAllListsItems(id);
    }

    @PostMapping("/additem")
    public ResponseEntity createTodoItem(@RequestBody TodoItem todoItem) {
        try {
            todoItemRepository.save(todoItem);
            return ResponseEntity.ok().body(true);
        }catch (Exception ignored){
            return ResponseEntity.ok().body(false);
        }
    }

    @GetMapping(value="/oneitem/{id}")
    public ResponseEntity<TodoItem> getTodoItemById(@PathVariable("id") String id) {
        return todoItemRepository.findById(id)
                .map(todoItem -> ResponseEntity.ok().body(todoItem))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(value="/upitem/{id}")
    public ResponseEntity<?> updateTodoItem(@PathVariable("id") String id,
                                           @Valid @RequestBody TodoItem todoItem) {
        if (todoItem.getId().equals(todoItem.getLinkedItemId()) ){
            return ResponseEntity.ok().body(false);
        }
        if (!todoItem.getLinkedItemId().equals("")){
            Optional<TodoItem> tempitem = todoItemRepository.findById(todoItem.getLinkedItemId());
            if(todoItem.getCompleted() == tempitem.get().getCompleted() || tempitem.get().getCompleted()){
                return todoItemRepository.findById(id)
                        .map(todoItemData -> {
                            todoItemData = todoItem;
                            todoItemData.setLinkedItemName(tempitem.get().getTitle());
                            TodoItem updatedTodoItem = todoItemRepository.save(todoItemData);
                            return ResponseEntity.ok().body(true);
                        }).orElse(ResponseEntity.ok().body(false));
            }else {
                return ResponseEntity.ok().body(false);
            }
        }
        else {
            return todoItemRepository.findById(id)
                    .map(todoItemData -> {
                        todoItemData = todoItem;
                        todoItemData.setLinkedItemName("");
                        TodoItem updatedTodoItem = todoItemRepository.save(todoItemData);
                        return ResponseEntity.ok().body(true);
                    }).orElse(ResponseEntity.ok().body(false));
        }
    }

    @DeleteMapping(value="/delitem/{id}")
    public ResponseEntity<?> deleteTodoItem(@PathVariable("id") String id) {
        return todoItemRepository.findById(id)
                .map(todoItem -> {
                    todoItemRepository.deleteById(id);
                    return ResponseEntity.ok().body(true);
                }).orElse(ResponseEntity.ok().body(false));
    }
}