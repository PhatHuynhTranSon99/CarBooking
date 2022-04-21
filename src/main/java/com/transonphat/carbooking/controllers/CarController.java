package com.transonphat.carbooking.controllers;

import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.services.CarService;
import org.springframework.web.bind.annotation.*;

@RestController
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/cars/{carId}")
    public Car getCarById(@PathVariable long carId) {
        return this.carService.getCarById(carId);
    }

    @GetMapping("/cars")
    public PaginationResult<Car> getAllDriver(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "5") int size) {
        return this.carService.getAllCars(page, size);
    }

    @PostMapping("/cars")
    public Car createNewCar(@RequestBody Car car) {
        return this.carService.createCar(car);
    }

    @DeleteMapping("/cars/{carId}")
    public Car deleteCarById(@PathVariable long carId) {
        return this.carService.deleteCar(carId);
    }
}
