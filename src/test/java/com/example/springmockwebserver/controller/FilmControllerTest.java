package com.example.springmockwebserver.controller;

import com.example.springmockwebserver.SpringMockwebserverApplication;
import com.example.springmockwebserver.entity.Film;
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
public class FilmControllerTest {
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
                    case "/films":
                        filename = "src/test/resources/jsonResponse/films/filmsResponse.json";
                        break;
                    case "/films/1":
                        filename = "src/test/resources/jsonResponse/films/filmResponseGetById.json";
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
    public void getAllFilms_success_shouldReturnFilms() {
        Response<List<Film>> response = client.get()
                .uri("/films")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Response<List<Film>>>() {
                })
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        assertTrue(response.getData().size() > 1);
        assertEquals("A New Hope", response.getData().get(0).getTitle());
    }

    @Test
    public void getFilmById_success_shouldReturnFilm() {
        Response<Film> response = client.get()
                .uri("/films/1")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Response<Film>>() {
                })
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        assertEquals("A New Hope", response.getData().getTitle());
    }
}
