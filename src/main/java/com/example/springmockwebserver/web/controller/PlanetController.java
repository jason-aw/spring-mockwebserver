package com.example.springmockwebserver.web.controller;

import com.example.springmockwebserver.entity.Planet;
import com.example.springmockwebserver.service.PlanetService;
import com.example.springmockwebserver.web.model.Response;
import com.example.springmockwebserver.web.model.planet.PlanetResponse;
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
public class PlanetController {

    private final PlanetService planetService;

    public PlanetController(PlanetService planetService) {
        this.planetService = planetService;
    }

    @ApiOperation("Get all planets")
    @GetMapping(path = "/planets",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<PlanetResponse>> getPlanets() {
        List<Planet> planets = planetService.getPlanets();
        List<PlanetResponse> planetResponses = planets.stream().map(this::toResponse)
                .collect(Collectors.toList());

        return Response.<List<PlanetResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(planetResponses)
                .build();
    }

    @ApiOperation("Get planet by id")
    @GetMapping(path = "/planets/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<PlanetResponse> getPlanetById(@PathVariable Integer id) {
        Planet planet = planetService.getPlanetById(id);
        return Response.<PlanetResponse>builder()
                .status(HttpStatus.OK.value())
                .data(toResponse(planet))
                .build();
    }

    public PlanetResponse toResponse(Planet planet) {
        PlanetResponse planetResponse = new PlanetResponse();
        BeanUtils.copyProperties(planet, planetResponse);
        return planetResponse;
    }
}
