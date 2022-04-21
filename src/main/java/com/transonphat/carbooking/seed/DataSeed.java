package com.transonphat.carbooking.seed;

import com.transonphat.carbooking.domain.Customer;
import com.transonphat.carbooking.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;

@Component
public class DataSeed implements CommandLineRunner {
    private final CustomerRepository customerRepository;

    public DataSeed(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //Create some customers
        Customer[] customers = {
                new Customer(1L, "Adam", "Cole", "Street", ZonedDateTime.now()),
                new Customer(2L, "Kyle", "O'Reily", "Street", ZonedDateTime.now()),
                new Customer(3L, "Osborn", "Norman", "Street", ZonedDateTime.now()),
        };

        //Save
        customerRepository.saveAll(List.of(customers));
    }
}
