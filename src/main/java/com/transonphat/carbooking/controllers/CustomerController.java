package com.transonphat.carbooking.controllers;

import com.transonphat.carbooking.domain.Customer;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.services.CustomerService;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers/{customerId}")
    public Customer getCustomerById(@PathVariable long customerId) {
        return customerService.getCustomerById(customerId);
    }

    @GetMapping("/customers")
    public PaginationResult<Customer> getAllCustomers(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "5") int size) {
        return customerService.getAllCustomers(page, size);
    }

    @PostMapping("/customers")
    public Customer createNewCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @DeleteMapping("/customers/{customerId}")
    public Customer deleteCustomerById(@PathVariable long customerId) {
        return customerService.deleteCustomer(customerId);
    }
}
