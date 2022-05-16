package com.transonphat.carbooking.services;

import com.transonphat.carbooking.dao.CrudDAO;
import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Driver;
import com.transonphat.carbooking.search.SearchCriterion;
import com.transonphat.carbooking.search.driver.DriverNameCriterion;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

/**
 * Author: Tran Son Phat
 * Unit tests for driver service
 */
@SpringBootTest(classes = {DriverService.class})
@ActiveProfiles(profiles = {"test"})
public class DriverServiceTest {
    @Autowired
    private DriverService driverService;

    @MockBean
    private CrudDAO<Driver> driverCrudDAO;

    @MockBean
    private SearchableDAO<Driver> driverSearchableDAO;

    @Test
    public void saveDriverShouldCallDAOMethod() {
        Driver newDriver = new Driver();
        driverService.saveDriver(newDriver);
        Mockito.verify(driverCrudDAO).save(newDriver);
    }

    @Test
    public void deleteDriverShouldCallDAOMethod() {
        driverService.deleteDriver(1L);
        Mockito.verify(driverCrudDAO).delete(1L);
    }

    @Test
    public void getDriverByIdShouldCallDAOMethod() {
        driverService.getDriverById(100L);
        Mockito.verify(driverCrudDAO).getOne(100L);
    }

    @Test
    public void searchDriverShouldCallDAOMethod() {
        SearchCriterion<Driver> driverSearchCriterion = new DriverNameCriterion("");
        driverService.searchDriver(driverSearchCriterion, 0, 10);
        Mockito.verify(driverSearchableDAO).search(driverSearchCriterion, 0, 10);
    }
}
