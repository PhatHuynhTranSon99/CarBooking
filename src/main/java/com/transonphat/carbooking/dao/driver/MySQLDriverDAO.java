package com.transonphat.carbooking.dao.driver;

import com.transonphat.carbooking.dao.DAO;
import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Driver;
import com.transonphat.carbooking.exceptions.DriverNotFoundException;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.repositories.DriverRepository;
import com.transonphat.carbooking.search.SearchCriterion;
import com.transonphat.carbooking.search.SearchSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MySQLDriverDAO implements DAO<Driver>, SearchableDAO<Driver> {
    private final DriverRepository driverRepository;

    public MySQLDriverDAO(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public Driver save(Driver driver) {
        return this.driverRepository.save(driver);
    }

    @Override
    public Driver delete(long id) {
        Driver driver = this.getOne(id);
        this.driverRepository.delete(driver);
        return driver;
    }

    @Override
    public PaginationResult<Driver> getAll(int currentPage, int pageSize) {
        //Create page request
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        //Get result
        Page<Driver> driverPage = this.driverRepository.findAll(pageable);

        //Map to driver pagination

        return new PaginationResult<>(
                driverPage.getTotalElements(),
                driverPage.get().collect(Collectors.toList()),
                driverPage.getNumber(),
                driverPage.getTotalPages()
        );
    }

    @Override
    public Driver getOne(long id) {
        return this.driverRepository.findById(id).orElseThrow(() -> new DriverNotFoundException("Driver does not exist"));
    }

    @Override
    public PaginationResult<Driver> search(SearchCriterion<Driver> criterion, int currentPage, int pageSize) {
        //Create page request
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        //Get result
        Page<Driver> driverPage = this.driverRepository.findAll(
                new SearchSpecification<>(criterion),
                pageable
        );

        //Map to driver pagination

        return new PaginationResult<>(
                driverPage.getTotalElements(),
                driverPage.get().collect(Collectors.toList()),
                driverPage.getNumber(),
                driverPage.getTotalPages()
        );
    }
}
