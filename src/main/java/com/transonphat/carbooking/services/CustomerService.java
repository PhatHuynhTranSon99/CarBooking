package com.transonphat.carbooking.services;

import com.transonphat.carbooking.dao.CrudDAO;
import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Customer;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.search.SearchCriterion;
import org.springframework.stereotype.Service;

/**
 * Author: Tran Son Phat
 * CustomerController contains all methods to perform customer management
 * Use in CustomerController
 */
@Service
public class CustomerService {
    private final SearchableDAO<Customer> customerSearchableDAO;
    private final CrudDAO<Customer> customerCrudDAO;

    public CustomerService(SearchableDAO<Customer> customerSearchableDAO, CrudDAO<Customer> customerCrudDAO) {
        this.customerSearchableDAO = customerSearchableDAO;
        this.customerCrudDAO = customerCrudDAO;
    }

    public Customer saveCustomer(Customer customer) {
        return customerCrudDAO.save(customer);
    }

    public Customer deleteCustomer(long id) {
        return customerCrudDAO.delete(id);
    }

    public Customer getCustomerById(long id) {
        return customerCrudDAO.getOne(id);
    }

    public PaginationResult<Customer> searchCustomer(SearchCriterion<Customer> customerSearchCriterion,
                                                     int currentPage,
                                                     int pageSize) {
        return customerSearchableDAO.search(customerSearchCriterion, currentPage, pageSize);
    }

}
