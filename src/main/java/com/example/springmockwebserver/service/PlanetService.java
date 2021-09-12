package com.example.springmockwebserver.service;

import com.example.springmockwebserver.entity.Planet;

import java.util.List;

public interface PlanetService {
    List<Planet> getPlanets();

    Planet getPlanetById(Integer id);
}
