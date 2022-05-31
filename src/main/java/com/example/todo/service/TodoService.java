package com.example.todo.service;

import com.example.todo.model.Todo;
import org.springframework.data.repository.CrudRepository;

public interface TodoService extends CrudRepository<Todo, Integer> {
}
