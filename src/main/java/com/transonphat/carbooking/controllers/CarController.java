package com.transonphat.carbooking.controllers;

import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.search.SearchCriteria;
import com.transonphat.carbooking.search.SearchCriterion;
import com.transonphat.carbooking.search.car.*;
import com.transonphat.carbooking.services.CarService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @PostMapping("/cars")
    public Car createNewCar(@RequestBody Car car) {
        return this.carService.saveCar(car);
    }

    @PutMapping("/cars/{carId}")
    public Car updateCar(@PathVariable long carId,
                         @RequestParam(required = false) String identification,
                         @RequestParam(required = false) String color,
                         @RequestParam(required = false) String make,
                         @RequestParam(required = false) String model,
                         @RequestParam(required = false) Boolean convertible) {
        //Find matching car
        Car car = this.carService.getCarById(carId);

        //Update parameters if exists
        if (identification != null) {
            car.setIdentificationNumber(identification);
        }

        if (color != null) {
            car.setColor(color);
        }

        if (make != null) {
            car.setMake(make);
        }

        if (model != null) {
            car.setModel(model);
        }

        if (convertible != null) {
            car.setConvertible(convertible);
        }

        //Save and return
        return this.carService.saveCar(car);
    }

    @DeleteMapping("/cars/{carId}")
    public Car deleteCarById(@PathVariable long carId) {
        return this.carService.deleteCar(carId);
    }

    @GetMapping("/cars")
    public PaginationResult<Car> getAllCars(@RequestParam(required = false) String identification,
                                            @RequestParam(required = false) String color,
                                            @RequestParam(required = false) String make,
                                            @RequestParam(required = false) String model,
                                            @RequestParam(required = false) Boolean convertible,
                                            @RequestParam(defaultValue = "0") int currentPage,
                                            @RequestParam(defaultValue = "3") int pageSize) {
        //List of criteria
        List<SearchCriterion<Car>> searchCriterionList = new ArrayList<>();

        if (identification != null) {
            searchCriterionList.add(new CarIdentificationCriterion(identification));
        }
        if (color != null) {
            searchCriterionList.add(new CarColorCriterion(color));
        }
        if (make != null) {
            searchCriterionList.add(new CarMakeCriterion(make));
        }
        if (model != null) {
            searchCriterionList.add(new CarModelCriterion(model));
        }
        if (convertible != null) {
            searchCriterionList.add(new CarConvertibleCriterion(convertible));
        }

        //Perform search
        return this.carService.searchCar(
                SearchCriteria.and(searchCriterionList),
                currentPage,
                pageSize
        );
    }

}
