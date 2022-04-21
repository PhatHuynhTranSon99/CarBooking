package com.transonphat.carbooking.services;

import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Driver;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.search.SearchCriterion;
import org.springframework.stereotype.Service;

@Service
public class DriverService {
    private final SearchableDAO<Driver> driverDAO;

    public DriverService(SearchableDAO<Driver> driverDAO) {
        this.driverDAO = driverDAO;
    }

    public Driver createDriver(Driver driver) {
        return this.driverDAO.add(driver);
    }

    public Driver deleteDriver(long id) {
        return this.driverDAO.delete(id);
    }

    public Driver getDriverById(long id) {
        return this.driverDAO.getOne(id);
    }

    public PaginationResult<Driver> getAllDrivers(int currentPage, int pageSize) {
        return this.driverDAO.getAll(currentPage, pageSize);
    }

    public PaginationResult<Driver> searchDriver(SearchCriterion<Driver> searchCriterion,
                                                 int currentPage,
                                                 int pageSize) {
        return this.driverDAO.search(searchCriterion, currentPage, pageSize);
    }
}
