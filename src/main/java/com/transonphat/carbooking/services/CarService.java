package com.transonphat.carbooking.services;

import com.transonphat.carbooking.dao.CrudDAO;
import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.search.SearchCriterion;
import org.springframework.stereotype.Service;

@Service
public class CarService {
    private final SearchableDAO<Car> carSearchableDao;
    private final CrudDAO<Car> carCrudDao;

    public CarService(SearchableDAO<Car> carSearchableDao, CrudDAO<Car> carCrudDao) {
        this.carSearchableDao = carSearchableDao;
        this.carCrudDao = carCrudDao;
    }

    public Car saveCar(Car car) {
        return this.carCrudDao.save(car);
    }

    public Car deleteCar(long id) {
        return this.carCrudDao.delete(id);
    }

    public Car getCarById(long id) {
        return this.carCrudDao.getOne(id);
    }

    public PaginationResult<Car> searchCar(SearchCriterion<Car> carSearchCriterion, int currentPage, int pageSize) {
        return this.carSearchableDao.search(carSearchCriterion, currentPage, pageSize);
    }
}
