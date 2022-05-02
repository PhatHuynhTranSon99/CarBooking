package com.transonphat.carbooking.services;

import com.transonphat.carbooking.dao.DAO;
import com.transonphat.carbooking.dao.ExhaustiveSearchableDAO;
import com.transonphat.carbooking.dao.ExistenceDAO;
import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Booking;
import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.domain.Customer;
import com.transonphat.carbooking.domain.Invoice;
import com.transonphat.carbooking.exceptions.CarDoesNotHaveDriverException;
import com.transonphat.carbooking.exceptions.CarNotAvailableException;
import com.transonphat.carbooking.exceptions.InvalidTimePeriodException;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.search.SearchCriteria;
import com.transonphat.carbooking.search.SearchCriterion;
import com.transonphat.carbooking.search.booking.BookingWithCarCriterion;
import com.transonphat.carbooking.search.booking.BookingWithCustomerCriterion;
import com.transonphat.carbooking.search.booking.BookingWithDriverCriterion;
import com.transonphat.carbooking.search.car.CarBookingExistCriterion;
import com.transonphat.carbooking.search.car.CarFreeCriterion;
import com.transonphat.carbooking.search.car.CarHasDriverCriterion;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class BookingService {
    private final DAO<Booking> bookingDao;
    private final SearchableDAO<Booking> bookingSearchableDAO;
    private final ExhaustiveSearchableDAO<Booking> exhaustiveSearchableDAO;
    private final SearchableDAO<Car> carSearchableDAO;
    private final ExistenceDAO<Car> carExistenceDAO;

    //Creating a booking
    private final InvoiceService invoiceService;
    private final CarService carService;
    private final CustomerService customerService;

    public BookingService(DAO<Booking> bookingDao,
                          SearchableDAO<Booking> bookingSearchableDAO,
                          ExhaustiveSearchableDAO<Booking> exhaustiveSearchableDAO,
                          SearchableDAO<Car> carSearchableDAO,
                          ExistenceDAO<Car> carExistenceDAO,
                          InvoiceService invoiceService,
                          CarService carService,
                          CustomerService customerService) {
        this.bookingDao = bookingDao;
        this.bookingSearchableDAO = bookingSearchableDAO;
        this.exhaustiveSearchableDAO = exhaustiveSearchableDAO;
        this.carSearchableDAO = carSearchableDAO;
        this.carExistenceDAO = carExistenceDAO;
        this.invoiceService = invoiceService;
        this.carService = carService;
        this.customerService = customerService;
    }

    public PaginationResult<Car> findAvailableCars(ZonedDateTime startTime,
                                                   ZonedDateTime endTime,
                                                   int currentPage,
                                                   int pageSize) {
        return this.carSearchableDAO.search(
                SearchCriteria.and(
                        List.of(
                            new CarFreeCriterion(startTime, endTime),
                            new CarHasDriverCriterion()
                        )
                ),
                currentPage,
                pageSize
        );
    }

    public boolean checkIfCarIsAvailable(long carId, ZonedDateTime startTime, ZonedDateTime endTime) {
        return !this.carExistenceDAO.exists(new CarBookingExistCriterion(carId, startTime, endTime));
    }

    public Booking createBooking(String startingLocation,
                                 String destinationLocation,
                                 ZonedDateTime startTime,
                                 ZonedDateTime endTime,
                                 double distance,
                                 long carId,
                                 long customerId) {
        //Get car and customer
        Car car = this.carService.getCarById(carId);
        Customer customer = this.customerService.getCustomerById(customerId);

        //Check if car is allocated
        if (!car.isAllocated())
            throw new CarDoesNotHaveDriverException("Car does not have a driver yet.");

        //Check if car is available for booking
        if (!checkIfCarIsAvailable(carId, startTime, endTime))
            throw new CarNotAvailableException("Car is not available during the trip.");

        //Check if start time is less than end time
        if (startTime.isAfter(endTime))
            throw new InvalidTimePeriodException("Starting time must come before ending time");

        //Create new invoice
        Invoice invoice = this.invoiceService.createInvoice(customer, car, distance);

        //Create new booking
        Booking booking = new Booking();

        booking.setStartLocation(startingLocation);
        booking.setEndLocation(destinationLocation);
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        booking.setDistance(distance);
        booking.setInvoice(invoice);

        return this.bookingDao.save(booking);
    }

    public PaginationResult<Booking> searchBookings(SearchCriterion<Booking> criterion, int currentPage, int pageSize) {
        return this.bookingSearchableDAO.search(criterion, currentPage, pageSize);
    }

    public Booking deleteBooking(long bookingId) {
        return this.bookingDao.delete(bookingId);
    }

    public void deleteRelatedBookingsWithCar(long carId) {
        //Retrieve and delete all bookings that are related to a car
        Iterable<Booking> relevantBookings = this.exhaustiveSearchableDAO.search(
                new BookingWithCarCriterion(carId)
        );

        //Remove all
        for (Booking booking : relevantBookings) {
            this.bookingDao.delete(booking.getId());
        }
    }

    public void deleteRelatedBookingsWithDriver(long driverId) {
        //Retrieve and delete all bookings that are related to a driver
        Iterable<Booking> relevantBookings = this.exhaustiveSearchableDAO.search(
                new BookingWithDriverCriterion(driverId)
        );

        //Remove all
        for (Booking booking : relevantBookings) {
            this.bookingDao.delete(booking.getId());
        }
    }

    public void deleteRelatedBookingsWithCustomer(long customerId) {
        //Retrieve and delete all bookings that are related to a driver
        Iterable<Booking> relevantBookings = this.exhaustiveSearchableDAO.search(
                new BookingWithCustomerCriterion(customerId)
        );

        //Remove all
        for (Booking booking : relevantBookings) {
            this.bookingDao.delete(booking.getId());
        }
    }
}
