package com.transonphat.carbooking.services;

import com.transonphat.carbooking.dao.CarDAO;
import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.search.SearchCriterion;
import org.springframework.stereotype.Service;

@Service
public class CarService {
    private final SearchableDAO<Car> carDao;

    public CarService(SearchableDAO<Car> carDao) {
        this.carDao = carDao;
    }

    public Car createCar(Car car) {
        return this.carDao.add(car);
    }

    public Car deleteCar(long id) {
        return this.carDao.delete(id);
    }

    public Car getCarById(long id) {
        return this.carDao.getOne(id);
    }

    public PaginationResult<Car> getAllCars(int currentPage, int pageSize) {
        return this.carDao.getAll(currentPage, pageSize);
    }

    public PaginationResult<Car> searchCar(SearchCriterion<Car> carSearchCriterion, int currentPage, int pageSize) {
        return this.carDao.search(carSearchCriterion, currentPage, pageSize);
    }
}
