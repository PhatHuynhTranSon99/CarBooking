package com.transonphat.carbooking.services;

import com.transonphat.carbooking.charge.ChargeCalculator;
import com.transonphat.carbooking.dao.DAO;
import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.domain.Customer;
import com.transonphat.carbooking.domain.Invoice;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {
    private final DAO<Invoice> invoiceDAO;
    private final ChargeCalculator chargeCalculator;

    public InvoiceService(DAO<Invoice> invoiceDAO, ChargeCalculator chargeCalculator) {
        this.invoiceDAO = invoiceDAO;
        this.chargeCalculator = chargeCalculator;
    }

    public Invoice createInvoice(Customer customer, Car car, double distance) {
        Invoice invoice = new Invoice();

        invoice.setCustomer(customer);
        invoice.setDriver(car.getDriver());
        invoice.setTotalCharges(chargeCalculator.calculateTotalCharge(distance, car.getRate()));

        return this.invoiceDAO.add(invoice);
    }
}
