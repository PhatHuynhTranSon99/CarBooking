package com.transonphat.carbooking.services;

import com.transonphat.carbooking.dao.DAO;
import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Driver;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.search.SearchCriterion;
import org.springframework.stereotype.Service;

@Service
public class DriverService {
    private final SearchableDAO<Driver> driverSearchableDAO;
    private final DAO<Driver> driverDAO;

    public DriverService(SearchableDAO<Driver> driverSearchableDAO, DAO<Driver> driverDAO) {
        this.driverSearchableDAO = driverSearchableDAO;
        this.driverDAO = driverDAO;
    }


    public Driver saveDriver(Driver driver) {
        return this.driverDAO.save(driver);
    }

    public Driver deleteDriver(long id) {
        return this.driverDAO.delete(id);
    }

    public Driver getDriverById(long id) {
        return this.driverDAO.getOne(id);
    }

    public PaginationResult<Driver> searchDriver(SearchCriterion<Driver> searchCriterion,
                                                 int currentPage,
                                                 int pageSize) {
        return this.driverSearchableDAO.search(searchCriterion, currentPage, pageSize);
    }
}
