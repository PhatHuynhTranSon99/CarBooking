package com.transonphat.carbooking.dao.driver;

import com.transonphat.carbooking.dao.CrudDAO;
import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Driver;

/**
 * Author: Tran Son Phat
 * Data Access Object interface to manage driver
 * Has functionalities such as CRUD, search by attribute (paginated)
 */
public interface DriverDAO extends CrudDAO<Driver>, SearchableDAO<Driver> {
}
