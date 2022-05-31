package com.example.todo;


import com.example.todo.model.Todo;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TodoWebTestClientTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(1)
    public void testCreate() {
        Todo todo = new Todo();
        todo.setTask("watch the video");

        webTestClient.post().uri("/todo")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(todo), Todo.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.task").isNotEmpty()
                .jsonPath("$.task").isEqualTo(todo.getTask());
    }

    @Test
    @Order(2)
    public void testFindAll() {

        webTestClient.get().uri("/todo")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Todo.class);

    }
}