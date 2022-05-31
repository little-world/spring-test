package com.example.todo;

import com.example.todo.controller.TodoController;
import com.example.todo.model.Todo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TodoApplicationTests {
	@Autowired
	TodoController todoController;

	@Test
	void contextLoads() {
		Assertions.assertNotNull(todoController);
	}

	@Test
	void createTodo() {
		Todo todo = new Todo();
		todo.setTask("watch the video");
		Todo newTodo = todoController.save(todo);

		Assertions.assertNotNull(newTodo);
		Assertions.assertEquals(todo.getTask(), newTodo.getTask());
	}

}
