package com.transonphat.carbooking.services;

import com.transonphat.carbooking.charge.ChargeCalculator;
import com.transonphat.carbooking.dao.CrudDAO;
import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.domain.CarBuilder;
import com.transonphat.carbooking.domain.Customer;
import com.transonphat.carbooking.domain.Invoice;
import com.transonphat.carbooking.search.SearchCriterion;
import com.transonphat.carbooking.search.invoice.InvoiceByCustomerCriterion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.ZonedDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = {InvoiceService.class})
@ActiveProfiles(profiles = {"test"})
public class InvoiceServiceTest {
    @Autowired
    private InvoiceService invoiceService;

    @MockBean
    private CrudDAO<Invoice> invoiceCrudDAO;

    @MockBean
    private SearchableDAO<Invoice> invoiceSearchableDAO;

    @MockBean
    private ChargeCalculator chargeCalculator;

    @Test
    public void createInvoiceShouldCallDAOMethod() {
        //Create random customer and car
        Customer customer = new Customer(1L, "Adam", "Cole", "11 Street",
                "0182019222", ZonedDateTime.now());

        Car car = new CarBuilder()
                .setId(1L)
                .setMake("Toyota")
                .setModel("Vias")
                .setColor("Green")
                .setConvertible(true)
                .setIdentificationNumber("0180-989")
                .setLicensePlate("G1-0172")
                .setRating(4.5)
                .setRate(10.8)
                .build();

        double distance = 100.0;

        //Create an invoice
        invoiceService.createInvoice(customer, car, distance);

        //Assert method calls
        verify(chargeCalculator).calculateTotalCharge(distance, car.getRate());
        verify(invoiceCrudDAO).save(any());
    }

    @Test
    public void searchInvoiceShouldCallDAOMethod() {
        //Create search criterion
        SearchCriterion<Invoice> invoiceSearchCriterion = new InvoiceByCustomerCriterion(1L);

        //Assert call
        invoiceService.searchInvoice(invoiceSearchCriterion, 0, 10);
        verify(invoiceSearchableDAO).search(invoiceSearchCriterion, 0, 10);
    }
}
