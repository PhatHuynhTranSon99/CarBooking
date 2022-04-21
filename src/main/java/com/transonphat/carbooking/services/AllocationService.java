package com.transonphat.carbooking.services;

import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.dao.allocation.AllocationDAO;
import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.domain.Driver;
import com.transonphat.carbooking.exceptions.CarAllocationException;
import org.springframework.stereotype.Service;

@Service
public class AllocationService {
    private final SearchableDAO<Car> carDao;
    private final SearchableDAO<Driver> driver;
    private final AllocationDAO allocationDao;

    public AllocationService(SearchableDAO<Car> carDao,
                             SearchableDAO<Driver> driver,
                             AllocationDAO allocationDao) {
        this.carDao = carDao;
        this.driver = driver;
        this.allocationDao = allocationDao;
    }

    public boolean isCarAllocated(long carId) {
        Car car = carDao.getOne(carId);
        return car.isAllocated();
    }

    public Car allocateDriverToCar(long carId, long driverId) {
        if (isCarAllocated(carId)) {
            throw new CarAllocationException("Car is already allocated.");
        }

        //Get car and driver
        Car car = this.carDao.getOne(carId);
        Driver driver = this.driver.getOne(driverId);

        //Return updated car information
        return allocationDao.addDriverToCar(car, driver);
    }

    public Car removeDriverFromCar(long carId) {
        if (!isCarAllocated(carId)) {
            throw new CarAllocationException("Car is not allocated yet");
        }

        //Get car
        Car car = this.carDao.getOne(carId);

        return this.allocationDao.removeDriverFromCar(car);
    }
}
