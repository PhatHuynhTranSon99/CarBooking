package com.transonphat.carbooking.services;

import com.transonphat.carbooking.dao.DAO;
import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.domain.Invoice;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.search.car.CarFreeCriterion;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class BookingService {
    private DAO<Invoice> invoiceDao;
    private SearchableDAO<Car> carSearchableDAO;
    private SearchableDAO<Invoice> invoiceSearchableDAO;

    public BookingService(DAO<Invoice> invoiceDao,
                          SearchableDAO<Car> carSearchableDAO,
                          SearchableDAO<Invoice> invoiceSearchableDAO) {
        this.invoiceDao = invoiceDao;
        this.carSearchableDAO = carSearchableDAO;
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
}
