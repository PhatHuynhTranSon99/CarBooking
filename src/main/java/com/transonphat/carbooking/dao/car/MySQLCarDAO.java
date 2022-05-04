package com.transonphat.carbooking.dao.car;

import com.transonphat.carbooking.dao.DAO;
import com.transonphat.carbooking.dao.ExistenceDAO;
import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.exceptions.types.CarNotFoundException;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.repositories.CarRepository;
import com.transonphat.carbooking.search.SearchCriterion;
import com.transonphat.carbooking.search.SearchSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MySQLCarDAO implements DAO<Car>, SearchableDAO<Car>, ExistenceDAO<Car> {
    private final CarRepository carRepository;

    public MySQLCarDAO(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Car save(Car car) {
        return this.carRepository.save(car);
    }

    @Override
    public Car delete(long id) {
        Car car = this.getOne(id);
        this.carRepository.delete(car);
        return car;
    }

    @Override
    public PaginationResult<Car> getAll(int currentPage, int pageSize) {
        //Create page request
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        //Get result
        Page<Car> carPage = this.carRepository.findAll(pageable);

        //Map page to pagination result

        return new PaginationResult<>(
                carPage.getTotalElements(),
                carPage.get().collect(Collectors.toList()),
                carPage.getNumber(),
                carPage.getTotalPages()
        );
    }

    @Override
    public Car getOne(long id) {
        return this.carRepository.findById(id).orElseThrow(() -> new CarNotFoundException("Car does not exist"));
    }

    @Override
    public PaginationResult<Car> search(SearchCriterion<Car> criteria, int currentPage, int pageSize) {
        //Create page request
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        //Get result
        Page<Car> carPage = this.carRepository.findAll(
                new SearchSpecification<>(criteria),
                pageable
        );

        //Map page to pagination result
        return new PaginationResult<>(
                carPage.getTotalElements(),
                carPage.get().collect(Collectors.toList()),
                carPage.getNumber(),
                carPage.getTotalPages()
        );
    }

    @Override
    public boolean exists(SearchCriterion<Car> criterion) {
        long count = this.carRepository.count(new SearchSpecification<>(criterion));
        return count > 0;
    }
}
