package com.example.springmockwebserver.service.impl;

import com.example.springmockwebserver.entity.Species;
import com.example.springmockwebserver.service.SpeciesService;
import com.example.springmockwebserver.web.model.swapi.SwapiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class SpeciesServiceImpl implements SpeciesService {

    @Autowired
    @Qualifier("swapiWebClient")
    private WebClient swapiClient;

    @Override
    public List<Species> getSpecies() {
        SwapiResponse<Species> response = swapiClient.get()
                .uri("/species")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<SwapiResponse<Species>>() {})
                .block();

        return response.getResults();
    }

    @Override
    public Species getSpeciesById(Integer id) {
        Species response = swapiClient.get()
                .uri("/species/" + id.toString())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Species>() {})
                .block();

        return response;
    }
}
