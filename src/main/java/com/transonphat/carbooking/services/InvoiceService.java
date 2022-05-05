package com.transonphat.carbooking.services;

import com.transonphat.carbooking.charge.ChargeCalculator;
import com.transonphat.carbooking.dao.CrudDAO;
import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.domain.Customer;
import com.transonphat.carbooking.domain.Invoice;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.search.SearchCriterion;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {
    private final CrudDAO<Invoice> invoiceCrudDAO;
    private final SearchableDAO<Invoice> invoiceSearchableDAO;
    private final ChargeCalculator chargeCalculator;

    public InvoiceService(CrudDAO<Invoice> invoiceCrudDAO,
                          SearchableDAO<Invoice> invoiceSearchableDAO,
                          ChargeCalculator chargeCalculator) {
        this.invoiceCrudDAO = invoiceCrudDAO;
        this.invoiceSearchableDAO = invoiceSearchableDAO;
        this.chargeCalculator = chargeCalculator;
    }

    public Invoice createInvoice(Customer customer, Car car, double distance) {
        Invoice invoice = new Invoice();

        invoice.setCustomer(customer);
        invoice.setDriver(car.getDriver());
        invoice.setTotalCharges(chargeCalculator.calculateTotalCharge(distance, car.getRate()));

        return this.invoiceCrudDAO.save(invoice);
    }

    public PaginationResult<Invoice> searchInvoice(SearchCriterion<Invoice> invoiceSearchCriterion,
                                                   int currentPage,
                                                   int pageSize) {
        return invoiceSearchableDAO.search(invoiceSearchCriterion, currentPage, pageSize);
    }

    public Invoice deleteInvoice(long invoiceId) {
        return this.invoiceCrudDAO.delete(invoiceId);
    }
}
