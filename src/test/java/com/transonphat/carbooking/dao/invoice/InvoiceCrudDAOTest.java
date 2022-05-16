package com.transonphat.carbooking.dao.invoice;

import com.transonphat.carbooking.dao.CrudDAO;
import com.transonphat.carbooking.domain.Invoice;
import com.transonphat.carbooking.exceptions.types.InvoiceNotFoundException;
import com.transonphat.carbooking.pagination.PaginationResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: Tran Son Phat
 * Unit tests for Crud<DAO> for invoice
 */
@SpringBootTest
@ActiveProfiles(profiles = {"test"})
@Transactional
public class InvoiceCrudDAOTest {
    @Autowired
    private CrudDAO<Invoice> invoiceCrudDAO;

    @Test
    public void getOneReturnCorrectResult() {
        Invoice invoice = invoiceCrudDAO.getOne(1L);

        assertEquals(1, invoice.getId());
        assertEquals(100.0, invoice.getTotalCharges());
        assertNotNull(invoice.getCustomer());
        assertNotNull(invoice.getDriver());
        assertNotNull(invoice.getCreatedDate());
    }

    @Test
    public void getOneNotFound() {
        InvoiceNotFoundException exception = assertThrows(
                InvoiceNotFoundException.class,
                () -> {
                    invoiceCrudDAO.getOne(100L);
                }
        );

        assertEquals("Invoice does not exist", exception.getMessage());
    }

    @Test
    public void getAllReturnCorrectResult() {
        PaginationResult<Invoice> invoicePaginationResult = invoiceCrudDAO.getAll(0, 10);
        assertEquals(4, invoicePaginationResult.getTotalItems());
        assertThat(
                invoicePaginationResult.getItems(),
                contains(
                        hasProperty("id", is(1L)),
                        hasProperty("id", is(2L)),
                        hasProperty("id", is(3L)),
                        hasProperty("id", is(4L))
                )
        );
    }

    @Test
    public void deleteSuccessfully() {
        Invoice invoice = invoiceCrudDAO.delete(1L);

        assertEquals(1, invoice.getId());
        assertEquals(100.0, invoice.getTotalCharges());
        assertNotNull(invoice.getCustomer());
        assertNotNull(invoice.getDriver());
        assertNotNull(invoice.getCreatedDate());
    }

    @Test
    public void deleteNotFound() {
        InvoiceNotFoundException exception = assertThrows(
                InvoiceNotFoundException.class,
                () -> {
                    invoiceCrudDAO.delete(100L);
                }
        );

        assertEquals("Invoice does not exist", exception.getMessage());
    }

    @Test
    public void saveSuccessfully() {
        Invoice invoice = new Invoice();
        invoice.setTotalCharges(100.0);

        invoiceCrudDAO.save(invoice);

        assertNotNull(invoice.getId());
        assertNotNull(invoice.getCreatedDate());
        assertEquals(100.0, invoice.getTotalCharges());
    }
}
