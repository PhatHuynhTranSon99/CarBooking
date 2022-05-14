package com.transonphat.carbooking.dao.invoice;

import com.transonphat.carbooking.dao.CrudDAO;
import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Invoice;

/**
 * Author: Tran Son Phat
 * Data Access Object interface to manage invoice
 * Has functionalities such as CRUD, search by attribute (paginated)
 */
public interface InvoiceDAO extends CrudDAO<Invoice>, SearchableDAO<Invoice> {
}
