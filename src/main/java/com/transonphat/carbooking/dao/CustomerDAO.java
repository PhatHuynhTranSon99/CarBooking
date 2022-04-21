package com.transonphat.carbooking.dao;

import com.transonphat.carbooking.domain.Customer;
import com.transonphat.carbooking.pagination.PaginationResult;

public interface CustomerDAO {
    Customer add(Customer customer);
    Customer delete(long id);
    PaginationResult<Customer> getAll(int currentPage, int pageSize);
    Customer getOne(long id);
}
