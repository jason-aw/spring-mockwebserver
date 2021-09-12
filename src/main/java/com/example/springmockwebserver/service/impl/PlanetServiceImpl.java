package com.example.springmockwebserver.service.impl;

import com.example.springmockwebserver.entity.Planet;
import com.example.springmockwebserver.service.PlanetService;
import com.example.springmockwebserver.web.model.swapi.SwapiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class PlanetServiceImpl implements PlanetService {

    @Autowired
    @Qualifier("swapiWebClient")
    private WebClient swapiClient;

    @Override
    public List<Planet> getPlanets() {
        SwapiResponse<Planet> response = swapiClient.get()
                .uri("/planets")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<SwapiResponse<Planet>>() {})
                .block();

        return response.getResults();
    }

    @Override
    public Planet getPlanetById(Integer id) {
        Planet response = swapiClient.get()
                .uri("/planets/" + id.toString())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Planet>() {})
                .block();

        return response;
    }
}
