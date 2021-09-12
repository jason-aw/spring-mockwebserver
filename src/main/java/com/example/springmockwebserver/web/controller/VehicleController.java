package com.example.springmockwebserver.web.controller;

import com.example.springmockwebserver.entity.Vehicle;
import com.example.springmockwebserver.service.VehicleService;
import com.example.springmockwebserver.web.model.Response;
import com.example.springmockwebserver.web.model.vehicle.VehicleResponse;
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
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @ApiOperation("Get all vehicles")
    @GetMapping(path = "/vehicles",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<VehicleResponse>> getVehicle() {
        List<Vehicle> vehicles = vehicleService.getVehicles();
        List<VehicleResponse> vehicleResponses = vehicles.stream().map(this::toResponse)
                .collect(Collectors.toList());

        return Response.<List<VehicleResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(vehicleResponses)
                .build();
    }

    @ApiOperation("Get vehicle by id")
    @GetMapping(path = "/vehicles/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<VehicleResponse> getVehicleById(@PathVariable Integer id) {
        Vehicle vehicle = vehicleService.getVehicleById(id);
        return Response.<VehicleResponse>builder()
                .status(HttpStatus.OK.value())
                .data(toResponse(vehicle))
                .build();
    }

    public VehicleResponse toResponse(Vehicle vehicle) {
        VehicleResponse vehicleResponse = new VehicleResponse();
        BeanUtils.copyProperties(vehicle, vehicleResponse);
        return vehicleResponse;
    }
}
