package com.transonphat.carbooking.services;

import com.transonphat.carbooking.dao.DAO;
import com.transonphat.carbooking.dao.ExistenceDAO;
import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Booking;
import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.domain.Invoice;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.search.car.CarBookingExistCriterion;
import com.transonphat.carbooking.search.car.CarFreeCriterion;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class BookingService {
    private final DAO<Invoice> invoiceDao;
    private final DAO<Booking> bookingDao;
    private final SearchableDAO<Car> carSearchableDAO;
    private final ExistenceDAO<Car> carExistenceDAO;
    private final SearchableDAO<Invoice> invoiceSearchableDAO;

    public BookingService(DAO<Invoice> invoiceDao,
                          DAO<Booking> bookingDao, SearchableDAO<Car> carSearchableDAO,
                          ExistenceDAO<Car> carExistenceDAO, SearchableDAO<Invoice> invoiceSearchableDAO) {
        this.invoiceDao = invoiceDao;
        this.bookingDao = bookingDao;
        this.carSearchableDAO = carSearchableDAO;
        this.carExistenceDAO = carExistenceDAO;
        this.invoiceSearchableDAO = invoiceSearchableDAO;
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

        return this.bookingDao.add(booking);
    }
}
