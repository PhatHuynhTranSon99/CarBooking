package com.transonphat.carbooking.services;

import com.transonphat.carbooking.dao.DAO;
import com.transonphat.carbooking.dao.ExhaustiveSearchableDAO;
import com.transonphat.carbooking.dao.ExistenceDAO;
import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Booking;
import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.domain.Invoice;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.search.SearchCriterion;
import com.transonphat.carbooking.search.booking.BookingWithCarCriterion;
import com.transonphat.carbooking.search.booking.BookingWithCustomerCriterion;
import com.transonphat.carbooking.search.booking.BookingWithDriverCriterion;
import com.transonphat.carbooking.search.car.CarBookingExistCriterion;
import com.transonphat.carbooking.search.car.CarFreeCriterion;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class BookingService {
    private final DAO<Booking> bookingDao;
    private final SearchableDAO<Booking> bookingSearchableDAO;
    private final ExhaustiveSearchableDAO<Booking> exhaustiveSearchableDAO;
    private final SearchableDAO<Car> carSearchableDAO;
    private final ExistenceDAO<Car> carExistenceDAO;

    public BookingService(DAO<Booking> bookingDao,
                          SearchableDAO<Booking> bookingSearchableDAO,
                          ExhaustiveSearchableDAO<Booking> exhaustiveSearchableDAO,
                          SearchableDAO<Car> carSearchableDAO,
                          ExistenceDAO<Car> carExistenceDAO) {
        this.bookingDao = bookingDao;
        this.bookingSearchableDAO = bookingSearchableDAO;
        this.exhaustiveSearchableDAO = exhaustiveSearchableDAO;
        this.carSearchableDAO = carSearchableDAO;
        this.carExistenceDAO = carExistenceDAO;
    }

    public PaginationResult<Car> findAvailableCars(ZonedDateTime startTime,
                                                   ZonedDateTime endTime,
                                                   int currentPage,
                                                   int pageSize) {
        return this.carSearchableDAO.search(
                new CarFreeCriterion(startTime, endTime),
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
                                 Invoice invoice) {
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
