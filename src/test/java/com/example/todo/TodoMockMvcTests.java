package com.example.todo;


import com.example.todo.controller.TodoController;
import com.example.todo.model.Todo;
import com.example.todo.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TodoMockMvcTests {

    @Autowired
    MockMvc mvc;

    @MockBean
    TodoService todoRepository;

    @Test
    @Order(1)
    public void testCreate() throws Exception {

        Todo todo = new Todo();
        todo.setId(0);
        todo.setTask("watch the video");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(todo);

        System.out.println(json);

        when(todoRepository.save(any())).thenReturn(todo);

        mvc.perform(post("/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())


                .andExpect( jsonPath("$.task",is(todo.getTask())));


    }

    @Test
    @Order(2)
    public void testFindAll() throws Exception {
        List<Todo> mockTodos = new ArrayList<Todo>();
        mockTodos.add(new Todo(0, "task0"));
        mockTodos.add(new Todo(1, "task1"));

        when(todoRepository.findAll()).thenReturn(mockTodos);

        mvc.perform(get("/todo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(0))).andExpect(jsonPath("$[0].task", is("task0")))
                .andExpect(jsonPath("$[1].id", is(1))).andExpect(jsonPath("$[1].task", is("task1")));
    }
}
