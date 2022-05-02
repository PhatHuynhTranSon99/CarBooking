package com.transonphat.carbooking.dao.allocation;

import com.transonphat.carbooking.dao.DAO;
import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.domain.Driver;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ActiveProfiles(profiles = {"test"})
@Transactional
public class AllocationDAOTest {
    @Autowired
    private AllocationDAO allocationDAO;

    @Autowired
    private DAO<Car> carDAO;

    @Autowired
    private DAO<Driver> driverDAO;

    @Test
    @Rollback
    public void shouldAllocateCarToDriverSuccessfully() {
        //Add car to driver
        Driver driver = driverDAO.getOne(4L);
        Car car = carDAO.getOne(4L);
        allocationDAO.addCarToDriver(car, driver);

        //Check driver and car has changed
        driver = driverDAO.getOne(4L);
        assertEquals(car, driver.getCar());
    }

    @Test
    public void shouldRemoveCarFromDriverSuccessfully() {
        Driver driver = driverDAO.getOne(4L);
        allocationDAO.removeCarFromDriver(driver);

        //Check driver and car has changed
        driver = driverDAO.getOne(4L);
        assertNull(driver.getCar());
    }
}
