package com.transonphat.carbooking.dao.allocation;

import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.domain.Driver;

/**
 * Author: Tran Son Phat
 * Data Access Object interface to manage car allocation
 */
public interface AllocationDAO {
    Driver addCarToDriver(Car car, Driver driver);
    Driver removeCarFromDriver(Driver driver);
}
