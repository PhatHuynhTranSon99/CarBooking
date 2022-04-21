package com.transonphat.carbooking.controllers;

import com.transonphat.carbooking.domain.Customer;
import com.transonphat.carbooking.domain.Driver;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.search.SearchCriteria;
import com.transonphat.carbooking.search.SearchCriterion;
import com.transonphat.carbooking.search.driver.DriverNameCriterion;
import com.transonphat.carbooking.search.driver.DriverPhoneCriterion;
import com.transonphat.carbooking.services.DriverService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/drivers/search")
    public PaginationResult<Driver> searchDriver(@RequestParam(required = false) String name,
                                                 @RequestParam(required = false) String phone,
                                                 @RequestParam(defaultValue = "0") int currentPage,
                                                 @RequestParam(defaultValue = "5") int pageSize) {
        List<SearchCriterion<Driver>> driverCriterionList = new ArrayList<>();

        if (name != null) {
            driverCriterionList.add(new DriverNameCriterion(name));
        }

        if (phone != null) {
            driverCriterionList.add(new DriverPhoneCriterion(phone));
        }

        //Combine into one criterion
        PaginationResult<Driver> customerPaginationResult = this.driverService.searchDriver(
                SearchCriteria.<Driver> and(driverCriterionList),
                currentPage,
                pageSize
        );

        return customerPaginationResult;
    }
}
