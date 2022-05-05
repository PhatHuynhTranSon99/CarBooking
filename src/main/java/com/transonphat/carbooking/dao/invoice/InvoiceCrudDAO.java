package com.transonphat.carbooking.dao.invoice;

import com.transonphat.carbooking.dao.CrudDAO;
import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Invoice;

public interface InvoiceCrudDAO extends CrudDAO<Invoice>, SearchableDAO<Invoice> {
}
