package com.example.springmockwebserver.service.impl;

import com.example.springmockwebserver.entity.Person;
import com.example.springmockwebserver.service.PersonService;
import com.example.springmockwebserver.web.model.swapi.SwapiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    @Qualifier("swapiWebClient")
    private WebClient swapiClient;

    @Override
    public List<Person> getPeople() {
        SwapiResponse<Person> response = swapiClient.get()
                .uri("/people")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<SwapiResponse<Person>>() {})
                .block();

        return response.getResults();
    }

    @Override
    public Person getPersonById(Integer id) {
        Person response = swapiClient.get()
                .uri("/people/" + id.toString())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Person>() {})
                .block();

        return response;
    }
}
