package com.example.springmockwebserver.web.model.starship;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StarshipResponse {
    private String name;
    private String model;
    private String manufacturer;
}
