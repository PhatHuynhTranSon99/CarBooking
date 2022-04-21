package com.transonphat.carbooking.seed;

import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.domain.CarBuilder;
import com.transonphat.carbooking.domain.Customer;
import com.transonphat.carbooking.domain.Driver;
import com.transonphat.carbooking.repositories.CarRepository;
import com.transonphat.carbooking.repositories.CustomerRepository;
import com.transonphat.carbooking.repositories.DriverRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;

@Component
public class DataSeed implements CommandLineRunner {
    private final CustomerRepository customerRepository;
    private final DriverRepository driverRepository;
    private final CarRepository carRepository;

    public DataSeed(CustomerRepository customerRepository,
                    DriverRepository driverRepository,
                    CarRepository carRepository) {
        this.customerRepository = customerRepository;
        this.driverRepository = driverRepository;
        this.carRepository = carRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //Create some customers
        Customer[] customers = {
                new Customer(1L, "Adam", "Cole", "Street", "0182019222", ZonedDateTime.now()),
                new Customer(2L, "Kyle", "O'Reily", "Street", "0172172812", ZonedDateTime.now()),
                new Customer(3L, "Osborn", "Norman", "Street", "2817283385", ZonedDateTime.now()),
        };

        //Create some drivers
        Driver[] drivers = {
                new Driver(1L, "Adam", "Levine",
                        "0909111909", 4.9, ZonedDateTime.now()),
                new Driver(2L, "Mark", "Rubber",
                        "0978101929", 4.5, ZonedDateTime.now()),
                new Driver(3L, "Jay", "Langdon",
                        "0192181921", 5.0, ZonedDateTime.now()),
        };

        //Create some cars
        Car carOne = new CarBuilder()
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

        Car carTwo = new CarBuilder()
                .setId(2L)
                .setMake("Mazda")
                .setModel("CSV")
                .setColor("Blue")
                .setConvertible(false)
                .setIdentificationNumber("0111-012")
                .setLicensePlate("G1-5655")
                .setRating(3.6)
                .setRate(5.5)
                .build();

        Car carThree = new CarBuilder()
                .setId(3L)
                .setMake("Volkswagen")
                .setModel("Axel")
                .setColor("Yellow")
                .setConvertible(false)
                .setIdentificationNumber("0914-129")
                .setLicensePlate("G2-0173")
                .setRating(5.0)
                .setRate(15.4)
                .build();

        //Save
        customerRepository.saveAll(List.of(customers));
        driverRepository.saveAll(List.of(drivers));
        carRepository.saveAll(List.of(carOne, carTwo, carThree));
    }
}
