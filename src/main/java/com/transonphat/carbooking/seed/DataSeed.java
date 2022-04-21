package com.transonphat.carbooking.seed;

import com.transonphat.carbooking.domain.Customer;
import com.transonphat.carbooking.domain.Driver;
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

    public DataSeed(CustomerRepository customerRepository, DriverRepository driverRepository) {
        this.customerRepository = customerRepository;
        this.driverRepository = driverRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //Create some customers
        Customer[] customers = {
                new Customer(1L, "Adam", "Cole", "Street", ZonedDateTime.now()),
                new Customer(2L, "Kyle", "O'Reily", "Street", ZonedDateTime.now()),
                new Customer(3L, "Osborn", "Norman", "Street", ZonedDateTime.now()),
        };

        //Create some drivers
        Driver[] drivers = {
                new Driver(1L, "H12-189", "0909111909", 4.9, ZonedDateTime.now()),
                new Driver(2L, "H17-789", "0978101929", 4.5, ZonedDateTime.now()),
                new Driver(3L, "H15-503", "0192181921", 5.0, ZonedDateTime.now()),
        };

        //Save
        customerRepository.saveAll(List.of(customers));
        driverRepository.saveAll(List.of(drivers));
    }
}
