package com.transonphat.carbooking.dao.booking;

import com.transonphat.carbooking.dao.CrudDAO;
import com.transonphat.carbooking.dao.ExhaustiveSearchableDAO;
import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Booking;

/**
 * Author: Tran Son Phat
 * Data Access Object interface to manage booking
 * Has functionalities such as CRUD, search by attributes (paginated and un-paginated)
 */
public interface BookingDAO extends CrudDAO<Booking>, SearchableDAO<Booking>, ExhaustiveSearchableDAO<Booking> {
}
