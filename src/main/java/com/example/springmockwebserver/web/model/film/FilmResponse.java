package com.example.springmockwebserver.web.model.film;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilmResponse {
    private String title;
    private Integer episode_id;
}