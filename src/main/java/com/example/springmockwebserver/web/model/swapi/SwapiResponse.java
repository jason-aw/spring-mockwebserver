package com.example.springmockwebserver.web.model.swapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SwapiResponse<T> {
    private List<T> results;
}
