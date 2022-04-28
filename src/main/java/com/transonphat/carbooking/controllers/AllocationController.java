package com.transonphat.carbooking.controllers;

import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.domain.Driver;
import com.transonphat.carbooking.services.AllocationService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AllocationController {
    private final AllocationService allocationService;

    public AllocationController(AllocationService allocationService) {
        this.allocationService = allocationService;
    }

    @PostMapping("/cars/{carId}/driver/{driverId}")
    public Driver allocateDriverToCar(@PathVariable long carId, @PathVariable long driverId) {
        return allocationService.allocateDriverToCar(carId, driverId);
    }

    @DeleteMapping("/cars/{carId}/driver")
    public Driver removeDriverFromCar(@PathVariable long carId) {
        return allocationService.removeDriverFromCar(carId);
    }
}
