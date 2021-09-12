package com.example.springmockwebserver.service.impl;

import com.example.springmockwebserver.entity.Vehicle;
import com.example.springmockwebserver.service.VehicleService;
import com.example.springmockwebserver.web.model.swapi.SwapiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    @Qualifier("swapiWebClient")
    private WebClient swapiClient;

    @Override
    public List<Vehicle> getVehicles() {
        SwapiResponse<Vehicle> response = swapiClient.get()
                .uri("/vehicles")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<SwapiResponse<Vehicle>>() {})
                .block();

        return response.getResults();
    }

    @Override
    public Vehicle getVehicleById(Integer id) {
        Vehicle response = swapiClient.get()
                .uri("/vehicles/" + id.toString())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Vehicle>() {})
                .block();

        return response;
    }

}
