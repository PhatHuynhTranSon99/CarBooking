package com.transonphat.carbooking.controllers;

import com.transonphat.carbooking.domain.Driver;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.services.DriverService;
import org.springframework.web.bind.annotation.*;

@RestController
public class DriverController {
    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping("/drivers/{driverId}")
    public Driver getDriverById(@PathVariable long driverId) {
        return this.driverService.getDriverById(driverId);
    }

    @GetMapping("/drivers")
    public PaginationResult<Driver> getAllDriver(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "5") int size) {
        return this.driverService.getAllDrivers(page, size);
    }

    @PostMapping("/drivers")
    public Driver createNewDriver(@RequestBody Driver driver) {
        return this.driverService.createDriver(driver);
    }

    @DeleteMapping("/drivers/{driverId}")
    public Driver deleteDriverById(@PathVariable long driverId) {
        return this.driverService.deleteDriver(driverId);
    }
}
