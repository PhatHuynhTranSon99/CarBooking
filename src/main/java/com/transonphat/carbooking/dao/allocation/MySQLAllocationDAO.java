package com.transonphat.carbooking.dao.allocation;

import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.domain.Driver;
import com.transonphat.carbooking.repositories.CarRepository;
import com.transonphat.carbooking.repositories.DriverRepository;
import org.springframework.stereotype.Component;

@Component
public class MySQLAllocationDAO implements AllocationDAO {
    private final DriverRepository driverRepository;

    public MySQLAllocationDAO(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public Driver addCarToDriver(Car car, Driver driver) {
        driver.setCar(car);
        driver = this.driverRepository.save(driver);
        return driver;
    }

    @Override
    public Driver removeCarFromDriver(Driver driver) {
        driver.removeCar();
        this.driverRepository.save(driver);
        return driver;
    }
}
