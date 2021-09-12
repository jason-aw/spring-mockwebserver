package com.example.springmockwebserver.web.controller;

import com.example.springmockwebserver.entity.Species;
import com.example.springmockwebserver.service.SpeciesService;
import com.example.springmockwebserver.web.model.Response;
import com.example.springmockwebserver.web.model.species.SpeciesResponse;
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
public class SpeciesController {

    private final SpeciesService speciesService;

    public SpeciesController(SpeciesService speciesService) {
        this.speciesService = speciesService;
    }

    @ApiOperation("Get all species")
    @GetMapping(path = "/species",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<SpeciesResponse>> getSpecies() {
        List<Species> species = speciesService.getSpecies();
        List<SpeciesResponse> filmResponses = species.stream().map(this::toResponse)
                .collect(Collectors.toList());

        return Response.<List<SpeciesResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(filmResponses)
                .build();
    }

    @ApiOperation("Get species by id")
    @GetMapping(path = "/species/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<SpeciesResponse> getSpeciesById(@PathVariable Integer id) {
        Species species = speciesService.getSpeciesById(id);
        return Response.<SpeciesResponse>builder()
                .status(HttpStatus.OK.value())
                .data(toResponse(species))
                .build();
    }

    public SpeciesResponse toResponse(Species species) {
        SpeciesResponse speciesResponse = new SpeciesResponse();
        BeanUtils.copyProperties(species, speciesResponse);
        return speciesResponse;
    }
}
