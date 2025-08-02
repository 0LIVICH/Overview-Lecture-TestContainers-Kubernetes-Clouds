package com.example.demo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class DemoApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Container
    private static final GenericContainer<?> devApp = new GenericContainer<>("devapp")
            .withExposedPorts(8080);

    @Container
    private static final GenericContainer<?> prodApp = new GenericContainer<>("prodapp")
            .withExposedPorts(8081);

    @BeforeAll
    public static void setUp() {
        devApp.start();
        prodApp.start();
    }

    @Test
    void testDevEnvironment() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + devApp.getMappedPort(8080), String.class);
        System.out.println("Dev response: " + response.getBody());
        assertEquals("Hello from DEV environment!", response.getBody());
    }

    @Test
    void testProdEnvironment() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + prodApp.getMappedPort(8081), String.class);
        System.out.println("Prod response: " + response.getBody());
        assertEquals("Hello from PROD environment!", response.getBody());
    }

    @Test
    void contextLoads() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:" + devApp.getMappedPort(8080), String.class);
        System.out.println(forEntity.getBody());
    }
} 