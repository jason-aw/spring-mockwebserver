package com.example.springmockwebserver.service;

import com.example.springmockwebserver.entity.Film;

import java.util.List;

public interface FilmService {
    List<Film> getFilms();

    Film getFilmById(Integer id);
}
