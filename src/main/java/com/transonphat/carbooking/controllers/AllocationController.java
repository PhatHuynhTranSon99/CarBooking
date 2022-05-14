package com.transonphat.carbooking.controllers;

import com.transonphat.carbooking.domain.Driver;
import com.transonphat.carbooking.services.AllocationService;
import com.transonphat.carbooking.services.BookingService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Tran Son Phat
 * AllocationController contains all endpoints to perform car allocation
 */
@RestController
public class AllocationController {
    private final AllocationService allocationService;
    private final BookingService bookingService;

    public AllocationController(AllocationService allocationService, BookingService bookingService) {
        this.allocationService = allocationService;
        this.bookingService = bookingService;
    }

    @PostMapping("/cars/{carId}/driver/{driverId}")
    public Driver allocateDriverToCar(@PathVariable long carId, @PathVariable long driverId) {
        return allocationService.allocateDriverToCar(carId, driverId);
    }

    @DeleteMapping("/cars/{carId}/driver")
    public Driver removeDriverFromCar(@PathVariable long carId) {
        Driver driver = allocationService.removeDriverFromCar(carId);
        //Remove all bookings related to driver
        this.bookingService.deleteRelatedBookingsWithDriver(driver.getId());
        return driver;
    }
}
