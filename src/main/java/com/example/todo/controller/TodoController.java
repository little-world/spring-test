package com.example.todo.controller;

import com.example.todo.model.Todo;
import com.example.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {

    @Autowired
    TodoService todoService;

    @PostMapping("/todo")
    public Todo save (@RequestBody  Todo todo) {
            return todoService.save(todo);
    }


    @GetMapping("/todo")
    Iterable<Todo> findAll() {
        return todoService.findAll();
    }


}
