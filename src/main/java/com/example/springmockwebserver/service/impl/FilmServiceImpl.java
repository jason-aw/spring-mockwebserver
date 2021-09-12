package com.example.springmockwebserver.service.impl;

import com.example.springmockwebserver.entity.Film;
import com.example.springmockwebserver.service.FilmService;
import com.example.springmockwebserver.web.model.film.FilmResponse;
import com.example.springmockwebserver.web.model.swapi.SwapiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class FilmServiceImpl implements FilmService {

    @Autowired
    @Qualifier("swapiWebClient")
    private WebClient swapiClient;

    @Override
    public List<Film> getFilms() {
        SwapiResponse<Film> response = swapiClient.get()
                .uri("/films")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<SwapiResponse<Film>>() {})
                .block();

        return response.getResults();
    }

    @Override
    public Film getFilmById(Integer id) {
        Film response = swapiClient.get()
                .uri("/films/" + id.toString())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Film>() {})
                .block();

        return response;
    }


}
