package com.example.springmockwebserver.service;

import com.example.springmockwebserver.entity.Species;

import java.util.List;

public interface SpeciesService {
    List<Species> getSpecies();

    Species getSpeciesById(Integer id);
}
