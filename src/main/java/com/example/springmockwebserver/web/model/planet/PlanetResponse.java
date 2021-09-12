package com.example.springmockwebserver.web.model.planet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanetResponse {
    private String name;
    private String climate;
    private String population;
}