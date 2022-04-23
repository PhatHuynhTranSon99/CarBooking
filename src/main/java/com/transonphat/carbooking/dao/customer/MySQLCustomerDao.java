package com.transonphat.carbooking.dao.customer;

import com.transonphat.carbooking.dao.DAO;
import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Customer;
import com.transonphat.carbooking.exceptions.CustomerNotFoundException;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.repositories.CustomerRepository;
import com.transonphat.carbooking.search.SearchCriterion;
import com.transonphat.carbooking.search.SearchSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class MySQLCustomerDao implements DAO<Customer>, SearchableDAO<Customer> {
    private final CustomerRepository customerRepository;

    public MySQLCustomerDao(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer add(Customer customer) {
        return this.customerRepository.save(customer);
    }

    @Override
    public Customer delete(long id) {
        Customer customer = this.getOne(id);
        this.customerRepository.delete(customer);
        return customer;
    }

    @Override
    public PaginationResult<Customer> getAll(int currentPage, int pageSize) {
        //Create pagination request
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        //Get the page result
        Page<Customer> customerPage = this.customerRepository.findAll(pageable);

        //Parse to pagination

        return new PaginationResult<>(
                customerPage.getTotalElements(),
                customerPage.get().collect(Collectors.toList()),
                customerPage.getNumber(),
                customerPage.getTotalPages()
        );
    }

    @Override
    public Customer getOne(long id) {
        return this.customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
    }

    @Override
    public PaginationResult<Customer> search(SearchCriterion<Customer> criteria, int currentPage, int pageSize) {
        //Create pagination request
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Customer> customerPage = this.customerRepository.findAll(
                new SearchSpecification<>(criteria),
                pageable
        );

        return new PaginationResult<>(
                customerPage.getTotalElements(),
                customerPage.get().collect(Collectors.toList()),
                customerPage.getNumber(),
                customerPage.getTotalPages()
        );
    }
}
