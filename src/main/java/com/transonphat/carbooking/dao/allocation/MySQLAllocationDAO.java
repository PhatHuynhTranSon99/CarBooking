package com.transonphat.carbooking.dao.allocation;

import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.domain.Driver;
import com.transonphat.carbooking.repositories.CarRepository;
import org.springframework.stereotype.Component;

@Component
public class MySQLAllocationDAO implements AllocationDAO {
    private final CarRepository carRepository;

    public MySQLAllocationDAO(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Car addDriverToCar(Car car, Driver driver) {
        car.setDriver(driver);
        this.carRepository.save(car);
        return car;
    }

    @Override
    public Car removeDriverFromCar(Car car) {
        car.removeDriver();
        this.carRepository.save(car);
        return car;
    }
}
