package com.transonphat.carbooking.dao.car;

import com.transonphat.carbooking.dao.CrudDAO;
import com.transonphat.carbooking.dao.ExistenceDAO;
import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Car;

/**
 * Author: Tran Son Phat
 * Data Access Object interface to manage car
 * Has functionalities such as CRUD, search by attribute (paginated) and
 * check if a car exists.
 */
public interface CarDAO extends CrudDAO<Car>, SearchableDAO<Car>, ExistenceDAO<Car> {
}
