package com.transonphat.carbooking.controllers;

import com.transonphat.carbooking.domain.Booking;
import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.domain.Customer;
import com.transonphat.carbooking.domain.Invoice;
import com.transonphat.carbooking.exceptions.CarDoesNotHaveDriverException;
import com.transonphat.carbooking.exceptions.CarNotAvailableException;
import com.transonphat.carbooking.exceptions.InvalidTimePeriodException;
import com.transonphat.carbooking.exceptions.MissingPeriodException;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.search.SearchCriteria;
import com.transonphat.carbooking.search.SearchCriterion;
import com.transonphat.carbooking.search.booking.BookingDateCriterion;
import com.transonphat.carbooking.services.BookingService;
import com.transonphat.carbooking.services.CarService;
import com.transonphat.carbooking.services.CustomerService;
import com.transonphat.carbooking.services.InvoiceService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BookingController {
    private final CarService carService;
    private final CustomerService customerService;
    private final BookingService bookingService;
    private final InvoiceService invoiceService;

    public BookingController(CarService carService,
                             CustomerService customerService,
                             BookingService bookingService,
                             InvoiceService invoiceService) {
        this.carService = carService;
        this.customerService = customerService;
        this.bookingService = bookingService;
        this.invoiceService = invoiceService;
    }

    @GetMapping("/bookings/cars")
    public PaginationResult<Car> findAvailableCars(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime start,
                                                   @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime end,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "3") int size) {
        return bookingService.findAvailableCars(start, end, page, size);
    }

    @GetMapping("/bookings")
    public PaginationResult<Booking> getAllBookings(@RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime from,
                                                          @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime to,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "3") int size) {
        //Create criteria list
        List<SearchCriterion<Booking>> searchCriterionList = new ArrayList<>();

        //Add criteria if present
        if ((from != null && to == null) || (from == null && to != null)) {
            throw new MissingPeriodException("From and to must both be visible");
        }

        if (from != null) {
            searchCriterionList.add(new BookingDateCriterion(from, to));
        }

        return this.bookingService.searchBookings(
                SearchCriteria.and(searchCriterionList),
                page,
                size
        );
    }

    @DeleteMapping("/bookings/{bookingId}")
    public Booking deleteBooking(@PathVariable long bookingId) {
        return this.bookingService.deleteBooking(bookingId);
    }

    @PostMapping("/customers/{customerId}/bookings")
    public Booking makeBooking(@RequestParam String startingLocation,
                               @RequestParam String destinationLocation,
                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime startTime,
                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime endTime,
                               @RequestParam Double distance,
                               @RequestParam Long carId,
                               @PathVariable Long customerId) {
        //Get car and customer
        Car car = this.carService.getCarById(carId);
        Customer customer = this.customerService.getCustomerById(customerId);

        //Check if car is allocated
        if (!car.isAllocated())
            throw new CarDoesNotHaveDriverException("Car does not have a driver yet.");

        //Check if car is available for booking
        if (!this.bookingService.checkIfCarIsAvailable(carId, startTime, endTime))
            throw new CarNotAvailableException("Car is not available during the trip.");

        //Check if start time is less than end time
        if (startTime.isAfter(endTime))
            throw new InvalidTimePeriodException("Starting time must come before ending time");

        //Create new invoice
        Invoice invoice = this.invoiceService.createInvoice(customer, car, distance);

        //Create new booking
        return bookingService.createBooking(
                startingLocation,
                destinationLocation,
                startTime,
                endTime,
                distance,
                invoice
        );
    }
}
