package com.transonphat.carbooking.controllers;

import com.transonphat.carbooking.domain.Driver;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.search.SearchCriteria;
import com.transonphat.carbooking.search.SearchCriterion;
import com.transonphat.carbooking.search.driver.DriverNameCriterion;
import com.transonphat.carbooking.search.driver.DriverPhoneCriterion;
import com.transonphat.carbooking.services.BookingService;
import com.transonphat.carbooking.services.DriverService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Tran Son Phat
 * DriverController contains all endpoints to perform driver management
 */
@RestController
public class DriverController {
    private final DriverService driverService;
    private final BookingService bookingService;

    public DriverController(DriverService driverService, BookingService bookingService) {
        this.driverService = driverService;
        this.bookingService = bookingService;
    }

    @GetMapping("/drivers/{driverId}")
    public Driver getDriverById(@PathVariable long driverId) {
        return this.driverService.getDriverById(driverId);
    }

    @PostMapping("/drivers")
    public Driver createNewDriver(@Valid @RequestBody Driver driver) {
        return this.driverService.saveDriver(driver);
    }

    @PutMapping("/drivers/{driverId}")
    public Driver updateDriver(@PathVariable long driverId,
                               @RequestParam(required = false) String firstName,
                               @RequestParam(required = false) String lastName,
                               @RequestParam(required = false) String phone,
                               @RequestParam(required = false) Double ratings) {
        //Find the driver
        Driver driver = this.driverService.getDriverById(driverId);

        //Update parameters
        if (firstName != null) {
            driver.setFirstName(firstName);
        }

        if (lastName != null) {
            driver.setLastName(lastName);
        }

        if (phone != null) {
            driver.setPhoneNumber(phone);
        }

        if (ratings != null) {
            driver.setRatings(ratings);
        }

        //Update entity and returns
        return this.driverService.saveDriver(driver);
    }

    @DeleteMapping("/drivers/{driverId}")
    public Driver deleteDriverById(@PathVariable long driverId) {
        this.bookingService.deleteRelatedBookingsWithDriver(driverId);
        return this.driverService.deleteDriver(driverId);
    }

    @GetMapping("/drivers")
    public PaginationResult<Driver> getAllDrivers(@RequestParam(required = false) String name,
                                                  @RequestParam(required = false) String phone,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "5") int size) {
        List<SearchCriterion<Driver>> driverCriterionList = new ArrayList<>();

        if (name != null) {
            driverCriterionList.add(new DriverNameCriterion(name));
        }

        if (phone != null) {
            driverCriterionList.add(new DriverPhoneCriterion(phone));
        }

        //Combine into one criterion
        return this.driverService.searchDriver(
                SearchCriteria.and(driverCriterionList),
                page,
                size
        );
    }
}
