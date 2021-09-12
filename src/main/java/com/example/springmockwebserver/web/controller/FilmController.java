package com.example.springmockwebserver.web.controller;

import com.example.springmockwebserver.entity.Film;
import com.example.springmockwebserver.service.FilmService;
import com.example.springmockwebserver.web.model.Response;
import com.example.springmockwebserver.web.model.film.FilmResponse;
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
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @ApiOperation("Get all films")
    @GetMapping(path = "/films",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<FilmResponse>> getFilms() {
        List<Film> films = filmService.getFilms();
        List<FilmResponse> filmResponses = films.stream().map(this::toResponse)
                .collect(Collectors.toList());

        return Response.<List<FilmResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(filmResponses)
                .build();
    }

    @ApiOperation("Get film by id")
    @GetMapping(path = "/films/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<FilmResponse> getFilmById(@PathVariable Integer id) {
        Film film = filmService.getFilmById(id);
        return Response.<FilmResponse>builder()
                .status(HttpStatus.OK.value())
                .data(toResponse(film))
                .build();
    }

    public FilmResponse toResponse(Film film) {
        FilmResponse filmResponse = new FilmResponse();
        BeanUtils.copyProperties(film, filmResponse);
        return filmResponse;
    }
}
