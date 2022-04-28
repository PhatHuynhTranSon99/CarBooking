package com.transonphat.carbooking.dao.allocation;

import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.domain.Driver;

public interface AllocationDAO {
    Driver addCarToDriver(Car car, Driver driver);
    Driver removeCarFromDriver(Driver driver);
}
