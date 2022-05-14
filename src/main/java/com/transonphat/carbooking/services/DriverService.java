package com.transonphat.carbooking.services;

import com.transonphat.carbooking.dao.CrudDAO;
import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Driver;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.search.SearchCriterion;
import org.springframework.stereotype.Service;

/**
 * Author: Tran Son Phat
 * DriverController contains all methods to perform driver management
 * Use in DriverController
 */
@Service
public class DriverService {
    private final SearchableDAO<Driver> driverSearchableDAO;
    private final CrudDAO<Driver> driverCrudDAO;

    public DriverService(SearchableDAO<Driver> driverSearchableDAO, CrudDAO<Driver> driverCrudDAO) {
        this.driverSearchableDAO = driverSearchableDAO;
        this.driverCrudDAO = driverCrudDAO;
    }


    public Driver saveDriver(Driver driver) {
        return this.driverCrudDAO.save(driver);
    }

    public Driver deleteDriver(long id) {
        return this.driverCrudDAO.delete(id);
    }

    public Driver getDriverById(long id) {
        return this.driverCrudDAO.getOne(id);
    }

    public PaginationResult<Driver> searchDriver(SearchCriterion<Driver> searchCriterion,
                                                 int currentPage,
                                                 int pageSize) {
        return this.driverSearchableDAO.search(searchCriterion, currentPage, pageSize);
    }
}
