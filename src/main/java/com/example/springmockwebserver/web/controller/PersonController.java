package com.example.springmockwebserver.web.controller;

import com.example.springmockwebserver.entity.Person;
import com.example.springmockwebserver.service.PersonService;
import com.example.springmockwebserver.web.model.Response;
import com.example.springmockwebserver.web.model.person.PersonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Api
@RestController
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @ApiOperation("Get all people")
    @GetMapping(path = "/people",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<PersonResponse>> getPeople() {
        List<Person> people = personService.getPeople();
        List<PersonResponse> personResponses = people.stream().map(this::toResponse)
                .collect(Collectors.toList());

        return Response.<List<PersonResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(personResponses)
                .build();
    }

    @ApiOperation("Get person by id")
    @GetMapping(path = "/people/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<PersonResponse> getPersonById(@PathVariable Integer id) {
        Person person = personService.getPersonById(id);
        return Response.<PersonResponse>builder()
                .status(HttpStatus.OK.value())
                .data(toResponse(person))
                .build();
    }

    public PersonResponse toResponse(Person person) {
        PersonResponse personResponse = new PersonResponse();
        BeanUtils.copyProperties(person, personResponse);
        return personResponse;
    }
}
