package com.transonphat.carbooking.services;

import com.transonphat.carbooking.dao.DAO;
import com.transonphat.carbooking.dao.ExistenceDAO;
import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.domain.Driver;
import com.transonphat.carbooking.domain.Invoice;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.search.car.CarFreeCriterion;
import com.transonphat.carbooking.search.driver.DriverBookingExists;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class BookingService {
    private final DAO<Invoice> invoiceDao;
    private final SearchableDAO<Car> carSearchableDAO;
    private final ExistenceDAO<Driver> driverExistenceDAO;
    private final SearchableDAO<Invoice> invoiceSearchableDAO;

    public BookingService(DAO<Invoice> invoiceDao,
                          SearchableDAO<Car> carSearchableDAO,
                          ExistenceDAO<Driver> driverExistenceDAO,
                          SearchableDAO<Invoice> invoiceSearchableDAO) {
        this.invoiceDao = invoiceDao;
        this.carSearchableDAO = carSearchableDAO;
        this.driverExistenceDAO = driverExistenceDAO;
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

    public boolean checkIfDriverIsAvailable(long driverId, ZonedDateTime startTime, ZonedDateTime endTime) {
        return !this.driverExistenceDAO.exists(new DriverBookingExists(driverId, startTime, endTime));
    }
}
