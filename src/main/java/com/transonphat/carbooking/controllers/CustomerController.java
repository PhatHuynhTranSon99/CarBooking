package com.transonphat.carbooking.controllers;

import com.transonphat.carbooking.domain.Customer;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.search.SearchCriteria;
import com.transonphat.carbooking.search.SearchCriterion;
import com.transonphat.carbooking.search.customer.CustomerAddressCriterion;
import com.transonphat.carbooking.search.customer.CustomerNameCriterion;
import com.transonphat.carbooking.search.customer.CustomerPhoneCriterion;
import com.transonphat.carbooking.services.BookingService;
import com.transonphat.carbooking.services.CustomerService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Tran Son Phat
 * CustomerController contains all endpoints to perform customer management
 */
@RestController
public class CustomerController {
    private final CustomerService customerService;
    private final BookingService bookingService;

    public CustomerController(CustomerService customerService, BookingService bookingService) {
        this.customerService = customerService;
        this.bookingService = bookingService;
    }

    @GetMapping("/customers/{customerId}")
    public Customer getCustomerById(@PathVariable long customerId) {
        return customerService.getCustomerById(customerId);
    }

    @PostMapping("/customers")
    public Customer createNewCustomer(@Valid @RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @PutMapping("/customers/{customerId}")
    public Customer updateCustomer(@PathVariable long customerId,
                                   @RequestParam(required = false) String firstName,
                                   @RequestParam(required = false) String lastName,
                                   @RequestParam(required = false) String phone,
                                   @RequestParam(required = false) String address) {
        //Find customer with id
        Customer customer = this.customerService.getCustomerById(customerId);

        //Update attributes
        if (firstName != null) {
            customer.setFirstName(firstName);
        }

        if (lastName != null) {
            customer.setLastName(lastName);
        }

        if (phone != null) {
            customer.setPhoneNumber(phone);
        }

        if (address != null) {
            customer.setAddress(address);
        }

        //Update entity and returns
        return this.customerService.saveCustomer(customer);
    }

    @DeleteMapping("/customers/{customerId}")
    public Customer deleteCustomerById(@PathVariable long customerId) {
        this.bookingService.deleteRelatedBookingsWithCustomer(customerId);
        return customerService.deleteCustomer(customerId);
    }

    @GetMapping("/customers")
    public PaginationResult<Customer> getAllCustomers(@RequestParam(required = false) String name,
                                                      @RequestParam(required = false) String phone,
                                                      @RequestParam(required = false) String address,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "3") int size) {
        //Set list of criterion
        List<SearchCriterion<Customer>> searchCriterionList = new ArrayList<>();

        if (name != null) {
            searchCriterionList.add(new CustomerNameCriterion(name));
        }
        if (phone != null) {
            searchCriterionList.add(new CustomerPhoneCriterion(phone));
        }
        if (address != null) {
            searchCriterionList.add(new CustomerAddressCriterion(address));
        }

        //Combine into one criterion
        return this.customerService.searchCustomer(
                SearchCriteria.and(searchCriterionList),
                page,
                size
        );
    }
}
