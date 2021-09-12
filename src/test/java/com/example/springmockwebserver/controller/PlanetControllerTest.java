package com.example.springmockwebserver.controller;

import com.example.springmockwebserver.SpringMockwebserverApplication;
import com.example.springmockwebserver.entity.Planet;
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
public class PlanetControllerTest {
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
                    case "/planets":
                        filename = "src/test/resources/jsonResponse/planets/planetsResponse.json";
                        break;
                    case "/planets/1":
                        filename = "src/test/resources/jsonResponse/planets/planetResponseGetById.json";
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
    public void getAllPlanets_success_shouldReturnPlanets() {
        Response<List<Planet>> response = client.get()
                .uri("/planets")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Response<List<Planet>>>() {})
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        assertTrue(response.getData().size() > 0);
        assertEquals("Tatooine", response.getData().get(0).getName());
    }

    @Test
    public void getPlanetById_success_shouldReturnPlanet() {
        Response<Planet> response = client.get()
                .uri("/planets/1")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Response<Planet>>() {
                })
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        assertEquals("Tatooine", response.getData().getName());
    }
}
