package com.transonphat.carbooking.services;

import com.transonphat.carbooking.dao.DAO;
import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Customer;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.search.SearchCriterion;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final SearchableDAO<Customer> customerSearchableDAO;
    private final DAO<Customer> customerDAO;

    public CustomerService(SearchableDAO<Customer> customerSearchableDAO, DAO<Customer> customerDAO) {
        this.customerSearchableDAO = customerSearchableDAO;
        this.customerDAO = customerDAO;
    }

    public Customer saveCustomer(Customer customer) {
        return customerDAO.save(customer);
    }

    public Customer deleteCustomer(long id) {
        return customerDAO.delete(id);
    }

    public Customer getCustomerById(long id) {
        return customerDAO.getOne(id);
    }

    public PaginationResult<Customer> searchCustomer(SearchCriterion<Customer> customerSearchCriterion,
                                                     int currentPage,
                                                     int pageSize) {
        return customerSearchableDAO.search(customerSearchCriterion, currentPage, pageSize);
    }

}
