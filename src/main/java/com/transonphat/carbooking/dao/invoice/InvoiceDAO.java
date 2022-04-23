package com.transonphat.carbooking.dao.invoice;

import com.transonphat.carbooking.dao.DAO;
import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Invoice;

public interface InvoiceDAO extends DAO<Invoice>, SearchableDAO<Invoice> {
}