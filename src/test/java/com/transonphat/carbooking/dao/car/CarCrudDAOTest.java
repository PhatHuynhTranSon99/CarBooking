package com.transonphat.carbooking.dao.car;

import com.transonphat.carbooking.dao.CrudDAO;
import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.exceptions.types.CarNotFoundException;
import com.transonphat.carbooking.pagination.PaginationResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: Tran Son Phat
 * Unit tests for Crud<DAO> for car
 */
@SpringBootTest
@ActiveProfiles(profiles = { "test" })
@Transactional
class CarCrudDAOTest {
    @Autowired
    private CrudDAO<Car> carCrudDAO;

    @Test
    @Rollback
    public void shouldReturnCorrectEntity() {
        Car car = this.carCrudDAO.getOne(1L);

        assertEquals(1L, car.getId());
        assertEquals("Toyota", car.getMake());
        assertEquals("Vias", car.getModel());
        assertEquals("Green", car.getColor());
        assertTrue(car.getIsConvertible());
        assertEquals("0180-989", car.getIdentificationNumber());
        assertEquals("G1-0172", car.getLicensePlate());
        assertEquals(4.5, car.getRating());
        assertEquals(10.8, car.getRate());
    }

    @Test
    @Rollback
    public void shouldThrowExceptionWhenCarDoesNotExist() {
        CarNotFoundException exception = assertThrows(CarNotFoundException.class, () -> {
            Car car = this.carCrudDAO.getOne(100);
        });

        assertEquals("Car does not exist", exception.getMessage());
    }

    @Test
    @Rollback
    public void shouldDeleteCarSuccessfully() {
        Car car = this.carCrudDAO.delete(1L);

        assertEquals(1L, car.getId());
        assertEquals("Toyota", car.getMake());
        assertEquals("Vias", car.getModel());
        assertEquals("Green", car.getColor());
        assertTrue(car.getIsConvertible());
        assertEquals("0180-989", car.getIdentificationNumber());
        assertEquals("G1-0172", car.getLicensePlate());
        assertEquals(4.5, car.getRating());
        assertEquals(10.8, car.getRate());
    }

    @Test
    @Rollback
    public void deleteCarNotFound() {
        CarNotFoundException exception = assertThrows(CarNotFoundException.class, () -> {
            Car car = this.carCrudDAO.delete(100L);
        });

        assertEquals("Car does not exist", exception.getMessage());
    }

    @Test
    @Rollback
    public void shouldReturnAllCars() {
        PaginationResult<Car> carPaginationResult = this.carCrudDAO.getAll(0, 5);

        assertEquals(4, carPaginationResult.getTotalItems());
        assertEquals(1, carPaginationResult.getTotalPages());
    }

    @Test
    @Rollback
    public void shouldSuccessfullyAddCar() {
        //Add a car
        Car car = new Car();
        car.setRate(10.1);
        car.setLicensePlate("51H-6788");
        car.setIsConvertible(false);
        car.setMake("Toyota");
        car.setColor("Green");
        car.setIdentificationNumber("51H-6788");
        car.setModel("Vios");
        car.setRating(5.0);
        carCrudDAO.save(car);

        //Check the id and create date
        assertNotNull(car.getId());
        assertNotNull(car.getCreatedDate());

        //Check the size of pagination result
        PaginationResult<Car> carPaginationResult = carCrudDAO.getAll(0, 10);
        assertEquals(5, carPaginationResult.getTotalItems());
        assertEquals(1, carPaginationResult.getTotalPages());
    }
}