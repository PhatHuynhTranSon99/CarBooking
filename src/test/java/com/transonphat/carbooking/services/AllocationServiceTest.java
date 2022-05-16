package com.transonphat.carbooking.services;

import com.transonphat.carbooking.dao.CrudDAO;
import com.transonphat.carbooking.dao.allocation.AllocationDAO;
import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.domain.CarBuilder;
import com.transonphat.carbooking.domain.Driver;
import com.transonphat.carbooking.exceptions.types.CarAllocationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Author: Tran Son Phat
 * Unit tests for allocation service
 */
@SpringBootTest(classes = {AllocationService.class})
@ActiveProfiles(profiles = {"test"})
public class AllocationServiceTest {
    @Autowired
    private AllocationService allocationService;

    @MockBean
    private CrudDAO<Car> carCrudDAO;

    @MockBean
    private CrudDAO<Driver> driverCrudDAO;

    @MockBean
    private AllocationDAO allocationDAO;

    @Test
    public void shouldAllocateCarSuccessfully() {
        //Create mock driver and car
        Car car = new CarBuilder()
                .setId(1L)
                .setMake("Toyota")
                .setModel("Vias")
                .setColor("Green")
                .setConvertible(true)
                .setIdentificationNumber("0180-989")
                .setLicensePlate("G1-0172")
                .setRating(4.5)
                .setRate(10.8)
                .build();

        Driver driver = new Driver(
                1L,
                "Adam",
                "Levine",
                "0909111909",
                4.9,
                ZonedDateTime.now()
        );

        car.setDriver(null);
        driver.setCar(null);

        when(carCrudDAO.getOne(1L)).thenReturn(car);
        when(driverCrudDAO.getOne(1L)).thenReturn(driver);

        //Call allocate service
        allocationService.allocateDriverToCar(1L, 1L);

        //One for check car allocated, one for get one
        verify(carCrudDAO).getOne(1L);
        verify(driverCrudDAO).getOne(1L);
        verify(allocationDAO).addCarToDriver(car, driver);
    }

    @Test
    public void shouldThrowExceptionWhenCarIsAllocated() {
        //Create mock driver and car
        Car car = new CarBuilder()
                .setId(1L)
                .setMake("Toyota")
                .setModel("Vias")
                .setColor("Green")
                .setConvertible(true)
                .setIdentificationNumber("0180-989")
                .setLicensePlate("G1-0172")
                .setRating(4.5)
                .setRate(10.8)
                .build();

        Driver driver = new Driver(
                1L,
                "Adam",
                "Levine",
                "0909111909",
                4.9,
                ZonedDateTime.now()
        );

        car.setDriver(new Driver());
        driver.setCar(null);

        when(carCrudDAO.getOne(1L)).thenReturn(car);
        when(driverCrudDAO.getOne(1L)).thenReturn(driver);

        //Call allocate service
        CarAllocationException exception = assertThrows(
                CarAllocationException.class,
                () -> {
                    allocationService.allocateDriverToCar(1L, 1L);
                }
        );

        assertEquals("Car is already allocated", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenDriverIsAllocated() {
        //Create mock driver and car
        Car car = new CarBuilder()
                .setId(1L)
                .setMake("Toyota")
                .setModel("Vias")
                .setColor("Green")
                .setConvertible(true)
                .setIdentificationNumber("0180-989")
                .setLicensePlate("G1-0172")
                .setRating(4.5)
                .setRate(10.8)
                .build();

        Driver driver = new Driver(
                1L,
                "Adam",
                "Levine",
                "0909111909",
                4.9,
                ZonedDateTime.now()
        );

        car.setDriver(null);
        driver.setCar(new Car());

        when(carCrudDAO.getOne(1L)).thenReturn(car);
        when(driverCrudDAO.getOne(1L)).thenReturn(driver);

        //Call allocate service
        CarAllocationException exception = assertThrows(
                CarAllocationException.class,
                () -> {
                    allocationService.allocateDriverToCar(1L, 1L);
                }
        );

        assertEquals("Driver is already allocated to a car", exception.getMessage());
    }

    @Test
    public void shouldDeallocatedSuccessfully() {
        //Create mock driver and car
        Car car = new CarBuilder()
                .setId(1L)
                .setMake("Toyota")
                .setModel("Vias")
                .setColor("Green")
                .setConvertible(true)
                .setIdentificationNumber("0180-989")
                .setLicensePlate("G1-0172")
                .setRating(4.5)
                .setRate(10.8)
                .build();
        car.setDriver(new Driver());

        when(carCrudDAO.getOne(1L)).thenReturn(car);

        allocationService.removeDriverFromCar(1L);
        verify(carCrudDAO, atLeast(1)).getOne(1L);
        verify(allocationDAO).removeCarFromDriver(car.getDriver());
    }

    @Test
    public void shouldThrowExceptionWhenCarIsNotAllocated() {
        //Create mock driver and car
        Car car = new CarBuilder()
                .setId(1L)
                .setMake("Toyota")
                .setModel("Vias")
                .setColor("Green")
                .setConvertible(true)
                .setIdentificationNumber("0180-989")
                .setLicensePlate("G1-0172")
                .setRating(4.5)
                .setRate(10.8)
                .build();
        car.setDriver(null);

        when(carCrudDAO.getOne(1L)).thenReturn(car);

        CarAllocationException exception = assertThrows(
                CarAllocationException.class,
                () -> {
                    allocationService.removeDriverFromCar(1L);
                }
        );
        assertEquals("Car is not allocated yet", exception.getMessage());
    }
}
