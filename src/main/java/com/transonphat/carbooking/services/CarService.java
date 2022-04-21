package com.transonphat.carbooking.services;

import com.transonphat.carbooking.dao.CarDAO;
import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.pagination.PaginationResult;
import org.springframework.stereotype.Service;

@Service
public class CarService {
    private final CarDAO carDAO;

    public CarService(CarDAO carDAO) {
        this.carDAO = carDAO;
    }

    public Car createCar(Car car) {
        return this.carDAO.add(car);
    }

    public Car deleteCar(long id) {
        return this.carDAO.delete(id);
    }

    public Car getCarById(long id) {
        return this.carDAO.getOne(id);
    }

    public PaginationResult<Car> getAllCars(int currentPage, int pageSize) {
        return this.carDAO.getAll(currentPage, pageSize);
    }
}
