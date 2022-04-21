package com.transonphat.carbooking.dao.allocation;

import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.domain.Driver;

public interface AllocationDAO {
    Car addDriverToCar(Car car, Driver driver);
    Car removeDriverFromCar(Car car);
}
