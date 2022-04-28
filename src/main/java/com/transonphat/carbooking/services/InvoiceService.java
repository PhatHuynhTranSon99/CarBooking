package com.transonphat.carbooking.services;

import com.transonphat.carbooking.charge.ChargeCalculator;
import com.transonphat.carbooking.dao.DAO;
import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.domain.Customer;
import com.transonphat.carbooking.domain.Invoice;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.search.SearchCriterion;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {
    private final DAO<Invoice> invoiceDAO;
    private final SearchableDAO<Invoice> invoiceSearchableDAO;
    private final ChargeCalculator chargeCalculator;

    public InvoiceService(DAO<Invoice> invoiceDAO,
                          SearchableDAO<Invoice> invoiceSearchableDAO,
                          ChargeCalculator chargeCalculator) {
        this.invoiceDAO = invoiceDAO;
        this.invoiceSearchableDAO = invoiceSearchableDAO;
        this.chargeCalculator = chargeCalculator;
    }

    public Invoice createInvoice(Customer customer, Car car, double distance) {
        Invoice invoice = new Invoice();

        invoice.setCustomer(customer);
        invoice.setDriver(car.getDriver());
        invoice.setTotalCharges(chargeCalculator.calculateTotalCharge(distance, car.getRate()));

        return this.invoiceDAO.save(invoice);
    }

    public PaginationResult<Invoice> searchInvoice(SearchCriterion<Invoice> invoiceSearchCriterion,
                                                   int currentPage,
                                                   int pageSize) {
        return invoiceSearchableDAO.search(invoiceSearchCriterion, currentPage, pageSize);
    }
}
