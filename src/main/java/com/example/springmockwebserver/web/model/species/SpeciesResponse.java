package com.example.springmockwebserver.web.model.species;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpeciesResponse {
    private String name;
    private String classification;
    private String designation;
}

