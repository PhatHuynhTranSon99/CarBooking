package com.transonphat.carbooking.controllers;

import com.transonphat.carbooking.domain.Customer;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.search.SearchCriteria;
import com.transonphat.carbooking.search.SearchCriterion;
import com.transonphat.carbooking.search.customer.CustomerAddressCriterion;
import com.transonphat.carbooking.search.customer.CustomerNameCriterion;
import com.transonphat.carbooking.search.customer.CustomerPhoneCriterion;
import com.transonphat.carbooking.services.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @PostMapping("/customers")
    public Customer createNewCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @DeleteMapping("/customers/{customerId}")
    public Customer deleteCustomerById(@PathVariable long customerId) {
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
                SearchCriteria.<Customer> and(searchCriterionList),
                page,
                size
        );
    }
}
