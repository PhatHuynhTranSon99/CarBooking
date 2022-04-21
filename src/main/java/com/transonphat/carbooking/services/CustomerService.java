package com.transonphat.carbooking.services;

import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Customer;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.search.SearchCriterion;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final SearchableDAO<Customer> customerDAO;

    public CustomerService(SearchableDAO<Customer> customerDAO) {
        this.customerDAO = customerDAO;
    }

    public Customer createCustomer(Customer customer) {
        return customerDAO.add(customer);
    }

    public Customer deleteCustomer(long id) {
        return customerDAO.delete(id);
    }

    public Customer getCustomerById(long id) {
        return customerDAO.getOne(id);
    }

    public PaginationResult<Customer> getAllCustomers(int currentPage, int pageSize) {
        return customerDAO.getAll(currentPage, pageSize);
    }

    public PaginationResult<Customer> searchCustomer(SearchCriterion<Customer> customerSearchCriterion,
                                                     int currentPage,
                                                     int pageSize) {
        return customerDAO.search(customerSearchCriterion, currentPage, pageSize);
    }

}
