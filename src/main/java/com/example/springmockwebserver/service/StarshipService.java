package com.example.springmockwebserver.service;

import com.example.springmockwebserver.entity.Starship;

import java.util.List;

public interface StarshipService {
    List<Starship> getStarships();

    Starship getStarshipById(Integer id);
}
