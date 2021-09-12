package com.example.springmockwebserver.service.impl;

import com.example.springmockwebserver.entity.Starship;
import com.example.springmockwebserver.service.StarshipService;
import com.example.springmockwebserver.web.model.swapi.SwapiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class StarshipServiceImpl implements StarshipService {

    @Autowired
    @Qualifier("swapiWebClient")
    private WebClient swapiClient;

    @Override
    public List<Starship> getStarships() {
        SwapiResponse<Starship> response = swapiClient.get()
                .uri("/starships")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<SwapiResponse<Starship>>() {})
                .block();

        return response.getResults();
    }

    @Override
    public Starship getStarshipById(Integer id) {
        Starship response = swapiClient.get()
                .uri("/starships/" + id.toString())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Starship>() {})
                .block();

        return response;
    }
}
