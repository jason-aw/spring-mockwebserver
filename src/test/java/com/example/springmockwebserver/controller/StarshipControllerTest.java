package com.example.springmockwebserver.controller;

import com.example.springmockwebserver.SpringMockwebserverApplication;
import com.example.springmockwebserver.entity.Starship;
import com.example.springmockwebserver.web.model.Response;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpringMockwebserverApplication.class)
@AutoConfigureWebTestClient
public class StarshipControllerTest {
    @Autowired
    WebTestClient client;

    static MockWebServer mockWebServer;

    @BeforeAll
    static void beforeAll() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start(10001);

        mockWebServer.setDispatcher(new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest recordedRequest) throws InterruptedException {
                MockResponse mockResponse = new MockResponse();
                mockResponse.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                mockResponse.setResponseCode(200);

                String filename = "";

                switch (recordedRequest.getPath()) {
                    case "/starships":
                        filename = "src/test/resources/jsonResponse/starships/starshipsResponse.json";
                        break;
                    case "/starships/1":
                        filename = "src/test/resources/jsonResponse/starships/starshipResponseGetById.json";
                        break;
                    default:
                        mockResponse.setResponseCode(404);
                        return mockResponse;
                }

                try {
                    FileInputStream fileInputStream = new FileInputStream(filename);
                    String content = IOUtils.toString(fileInputStream, StandardCharsets.UTF_8.name());
                    mockResponse.setBody(content);
                } catch (Exception e) {
                    System.out.println("error" + e.getMessage());
                }
                return mockResponse;
            }
        });
    }

    @AfterAll
    static void afterAll() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void getAllStarships_success_shouldReturnStarships() {
        Response<List<Starship>> response = client.get()
                .uri("/starships")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Response<List<Starship>>>() {})
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        assertTrue(response.getData().size() > 0);
        assertEquals("CR90 corvette", response.getData().get(0).getName());
    }

    @Test
    public void getStarshipById_success_shouldReturnStarship() {
        Response<Starship> response = client.get()
                .uri("/starships/1")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Response<Starship>>() {
                })
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        assertEquals("CR90 corvette", response.getData().getName());
    }
}
