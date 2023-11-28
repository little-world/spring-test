package com.example.todo;


import com.example.todo.model.Todo;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TodoRestTemplateTests
{
    @LocalServerPort
    int randomServerPort;

    @Test
    @Order(1)
    public void testCreate() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/todo/";
        URI uri = new URI(baseUrl);

        Todo todo = new Todo();
        todo.setTask("watch the video");

        ResponseEntity<Todo> result = restTemplate.postForEntity(uri, todo, Todo.class);

        //Verify request succeed
        Assertions.assertEquals(200, result.getStatusCodeValue());
        Assertions.assertEquals(result.getBody().getTask(), todo.getTask());

    }

    @Test
    @Order(2)
    public void testFindAll() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/todo/";
        URI uri = new URI(baseUrl);


        ResponseEntity<Todo[]> result = restTemplate.getForEntity(uri, Todo[].class);

        //Verify request succeed
        Assertions.assertEquals(200, result.getStatusCodeValue());
        Assertions.assertEquals(result.getBody()[0].getTask(), "watch the video" );

    }
}