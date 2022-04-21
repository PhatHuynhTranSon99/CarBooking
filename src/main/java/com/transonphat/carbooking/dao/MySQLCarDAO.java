package com.transonphat.carbooking.dao;

import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.exceptions.CarNotFoundException;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.repositories.CarRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MySQLCarDAO implements CarDAO {
    private final CarRepository carRepository;

    public MySQLCarDAO(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Car add(Car car) {
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
        PaginationResult<Car> carPaginationResult = new PaginationResult<>(
                carPage.getTotalElements(),
                carPage.get().collect(Collectors.toList()),
                carPage.getNumber(),
                carPage.getTotalPages()
        );

        return carPaginationResult;
    }

    @Override
    public Car getOne(long id) {
        return this.carRepository.findById(id).orElseThrow(CarNotFoundException::new);
    }
}
