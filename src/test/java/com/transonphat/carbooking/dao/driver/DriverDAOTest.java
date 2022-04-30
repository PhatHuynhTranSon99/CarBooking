package com.transonphat.carbooking.dao.driver;

import com.transonphat.carbooking.dao.DAO;
import com.transonphat.carbooking.domain.Driver;
import com.transonphat.carbooking.exceptions.DriverNotFoundException;
import com.transonphat.carbooking.pagination.PaginationResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles(profiles = {"test"})
@Transactional
public class DriverDAOTest {
    @Autowired
    private DAO<Driver> driverDAO;

    @Test
    public void getOneReturnCorrectResult() {
        Driver driver = driverDAO.getOne(1L);

        assertEquals("Adam", driver.getFirstName());
        assertEquals("Levine", driver.getLastName());
        assertEquals("0909111909", driver.getPhoneNumber());
        assertEquals(4.9, driver.getRatings());
    }

    @Test
    public void getOneNotFound() {
        DriverNotFoundException exception = assertThrows(
                DriverNotFoundException.class,
                () -> {
                    driverDAO.getOne(100L);
                }
        );

        assertEquals("Driver does not exist", exception.getMessage());
    }

    @Test
    @Transactional
    public void deleteDriverSuccessfully() {
        Driver driver = driverDAO.delete(1L);

        assertEquals("Adam", driver.getFirstName());
        assertEquals("Levine", driver.getLastName());
        assertEquals("0909111909", driver.getPhoneNumber());
        assertEquals(4.9, driver.getRatings());
    }

    @Test
    public void deleteDriverNotFound() {
        DriverNotFoundException exception = assertThrows(
                DriverNotFoundException.class,
                () -> {
                    driverDAO.getOne(200L);
                }
        );

        assertEquals("Driver does not exist", exception.getMessage());
    }

    @Test
    public void findAllDriversSuccessfully() {
        PaginationResult<Driver> driverPaginationResult = driverDAO.getAll(0, 10);

        assertEquals(3, driverPaginationResult.getTotalItems());
        assertThat(
                driverPaginationResult.getItems(),
                contains(
                        hasProperty("id", is(1L)),
                        hasProperty("id", is(2L)),
                        hasProperty("id", is(3L))
                )
        );
    }

    @Test
    @Transactional
    public void addDriverSuccessfully() {
        Driver driver = new Driver();
        driver.setFirstName("Man");
        driver.setLastName("Kind");
        driver.setPhoneNumber("0909191919");
        driver.setRatings(5.0);
        driver = driverDAO.save(driver);

        assertNotNull(driver.getId());
        assertEquals("Man", driver.getFirstName());
        assertEquals("Kind", driver.getLastName());
        assertEquals("0909191919", driver.getPhoneNumber());
        assertEquals(5.0, driver.getRatings());
    }
}
