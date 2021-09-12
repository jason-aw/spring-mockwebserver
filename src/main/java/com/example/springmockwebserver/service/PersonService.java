package com.example.springmockwebserver.service;

import com.example.springmockwebserver.entity.Person;

import java.util.List;

public interface PersonService {
    List<Person> getPeople();

    Person getPersonById(Integer id);
}
