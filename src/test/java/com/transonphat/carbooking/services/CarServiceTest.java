package com.transonphat.carbooking.services;

import com.transonphat.carbooking.dao.CrudDAO;
import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.search.SearchCriterion;
import com.transonphat.carbooking.search.car.CarMakeCriterion;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

/**
 * Author: Tran Son Phat
 * Unit tests for car service
 */
@SpringBootTest(classes = {CarService.class})
@ActiveProfiles(profiles = {"test"})
public class CarServiceTest {
    @Autowired
    private CarService carService;

    @MockBean
    private CrudDAO<Car> carCrudDAO;

    @MockBean
    private SearchableDAO<Car> carSearchableDAO;

    @Test
    public void saveCarShouldCallDAOMethod() {
        //Assert carDAO save method is called
        Car newCar = new Car();
        carService.saveCar(newCar);
        Mockito.verify(carCrudDAO).save(newCar);
    }

    @Test
    public void deleteCarShouldCallDAOMethod() {
        //Assert carDAO delete method is called
        carService.deleteCar(1);
        Mockito.verify(carCrudDAO).delete(1);
    }

    @Test
    public void getCarByIdShouldCallDAOMethod() {
        carService.getCarById(1L);
        Mockito.verify(carCrudDAO).getOne(1L);
    }

    @Test
    public void searchCarShouldCallDAOMethod() {
        //Create random criterion
        SearchCriterion<Car> carSearchCriterion = new CarMakeCriterion("Toyota");
        carService.searchCar(carSearchCriterion, 0, 10);
        Mockito.verify(carSearchableDAO).search(carSearchCriterion, 0, 10);
    }
}
