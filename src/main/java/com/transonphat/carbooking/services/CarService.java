package com.transonphat.carbooking.services;

import com.transonphat.carbooking.dao.DAO;
import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.search.SearchCriterion;
import org.springframework.stereotype.Service;

@Service
public class CarService {
    private final SearchableDAO<Car> carSearchableDao;
    private final DAO<Car> carDao;

    public CarService(SearchableDAO<Car> carSearchableDao, DAO<Car> carDao) {
        this.carSearchableDao = carSearchableDao;
        this.carDao = carDao;
    }

    public Car createCar(Car car) {
        return this.carDao.save(car);
    }

    public Car deleteCar(long id) {
        return this.carDao.delete(id);
    }

    public Car getCarById(long id) {
        return this.carDao.getOne(id);
    }

    public PaginationResult<Car> searchCar(SearchCriterion<Car> carSearchCriterion, int currentPage, int pageSize) {
        return this.carSearchableDao.search(carSearchCriterion, currentPage, pageSize);
    }
}
