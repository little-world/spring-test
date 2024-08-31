package com.example.todo;

import com.example.todo.model.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class TodoRestClientTests {

  @LocalServerPort
  private int port;

  private RestClient restClient;

  @BeforeEach
  public void setup() {
    this.restClient = RestClient.builder()
        .baseUrl("http://localhost:" + port)
        .build();
  }

  @Test
  @Order(1)
  public void testCreate() {
    // Arrange
    Todo todo = new Todo();
    todo.setTask("watch the video");

    Todo createdTodo = this.restClient.post()
        .uri("/todo")
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
        .body(todo)
        .retrieve()
        .body(Todo.class);

    assertThat(createdTodo).isNotNull();
    assertThat(createdTodo.getTask()).isNotEmpty();
    assertThat(createdTodo.getTask()).isEqualTo(todo.getTask());

  }

  @Test
  @Order(2)
  public void testFindAll() throws URISyntaxException {
    // Arrange
    URI uri = new URI("http://localhost:" + port + "/todo");

    // Act & Assert

    Todo[] todos = this.restClient.get()
        .uri(uri)
        .retrieve()
        .body(Todo[].class);

    // Verify request succeeded
    assertThat(todos).isNotEmpty();
    assertThat(todos[0].getTask()).isEqualTo("watch the video");
  }
}
