package com.transonphat.carbooking.dao.car;

import com.transonphat.carbooking.dao.DAO;
import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.exceptions.CarNotFoundException;
import com.transonphat.carbooking.pagination.PaginationResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles(profiles = { "test" })
@Transactional
class CarDAOTest {
    @Autowired
    private DAO<Car> carDAO;

    @Test
    @Rollback
    public void shouldReturnCorrectEntity() {
        Car car = this.carDAO.getOne(1L);

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
            Car car = this.carDAO.getOne(100);
        });

        assertEquals("Car does not exist", exception.getMessage());
    }

    @Test
    @Rollback
    public void shouldDeleteCarSuccessfully() {
        Car car = this.carDAO.delete(1L);

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
            Car car = this.carDAO.delete(100L);
        });

        assertEquals("Car does not exist", exception.getMessage());
    }

    @Test
    @Rollback
    public void shouldReturnAllCars() {
        PaginationResult<Car> carPaginationResult = this.carDAO.getAll(0, 5);

        assertEquals(3, carPaginationResult.getTotalItems());
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
        carDAO.save(car);

        //Check the id and create date
        assertNotNull(car.getId());
        assertNotNull(car.getCreatedDate());

        //Check the size of pagination result
        PaginationResult<Car> carPaginationResult = carDAO.getAll(0, 10);
        assertEquals(4, carPaginationResult.getTotalItems());
        assertEquals(1, carPaginationResult.getTotalPages());
    }
}