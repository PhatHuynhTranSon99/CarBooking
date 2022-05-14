package com.transonphat.carbooking.dao.customer;

import com.transonphat.carbooking.dao.CrudDAO;
import com.transonphat.carbooking.dao.ExistenceDAO;
import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Customer;

/**
 * Author: Tran Son Phat
 * Data Access Object interface to manage customer
 * Has functionalities such as CRUD, search by attribute (paginated) and
 * check if a customer exists.
 */
public interface CustomerDAO extends CrudDAO<Customer>, SearchableDAO<Customer>, ExistenceDAO<Customer> {
}
