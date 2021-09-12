package com.example.springmockwebserver.web.controller;

import com.example.springmockwebserver.entity.Starship;
import com.example.springmockwebserver.service.StarshipService;
import com.example.springmockwebserver.web.model.Response;
import com.example.springmockwebserver.web.model.starship.StarshipResponse;
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
public class StarshipController {

    private final StarshipService starshipService;

    public StarshipController(StarshipService starshipService) {
        this.starshipService = starshipService;
    }

    @ApiOperation("Get all starships")
    @GetMapping(path = "/starships",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<StarshipResponse>> getStarships() {
        List<Starship> starships = starshipService.getStarships();
        List<StarshipResponse> starshipResponses = starships.stream().map(this::toResponse)
                .collect(Collectors.toList());

        return Response.<List<StarshipResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(starshipResponses)
                .build();
    }

    @ApiOperation("Get starship by id")
    @GetMapping(path = "/starships/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<StarshipResponse> getStarshipById(@PathVariable Integer id) {
        Starship starship = starshipService.getStarshipById(id);
        return Response.<StarshipResponse>builder()
                .status(HttpStatus.OK.value())
                .data(toResponse(starship))
                .build();
    }

    public StarshipResponse toResponse(Starship starship) {
        StarshipResponse starshipResponse = new StarshipResponse();
        BeanUtils.copyProperties(starship, starshipResponse);
        return starshipResponse;
    }
}
