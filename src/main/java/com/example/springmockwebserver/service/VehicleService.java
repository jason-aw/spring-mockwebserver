package com.example.springmockwebserver.service;

import com.example.springmockwebserver.entity.Vehicle;

import java.util.List;

public interface VehicleService {
    List<Vehicle> getVehicles();

    Vehicle getVehicleById(Integer id);
}
