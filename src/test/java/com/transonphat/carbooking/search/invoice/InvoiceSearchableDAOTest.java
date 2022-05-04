package com.transonphat.carbooking.search.invoice;

import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Invoice;
import com.transonphat.carbooking.pagination.PaginationResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles(profiles = {"test"})
public class InvoiceSearchableDAOTest {
    @Autowired
    private SearchableDAO<Invoice> invoiceSearchableDAO;

    @Test
    public void searchInvoiceWithinARange() {
        //3 match
        PaginationResult<Invoice> firstPaginationResult = invoiceSearchableDAO.search(
                new InvoiceDateCriterion(
                        ZonedDateTime.of(2020, 1, 1, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh")),
                        ZonedDateTime.of(2020, 1, 5, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh"))
                ),
                0,
                10
        );
        assertEquals(3, firstPaginationResult.getTotalItems());
        assertThat(
                firstPaginationResult.getItems(),
                contains(
                        hasProperty("id", is(1L)),
                        hasProperty("id", is(3L)),
                        hasProperty("id", is(4L))
                )
        );

        //One match
        PaginationResult<Invoice> secondPaginationResult = invoiceSearchableDAO.search(
                new InvoiceDateCriterion(
                        ZonedDateTime.of(2020, 1, 20, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh")),
                        ZonedDateTime.of(2020, 3, 2, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh"))
                ),
                0,
                10
        );
        assertEquals(1, secondPaginationResult.getTotalItems());
        assertThat(
                secondPaginationResult.getItems(),
                contains(
                        hasProperty("id", is(2L))
                )
        );

        //No match
        PaginationResult<Invoice> thirdPaginationResult = invoiceSearchableDAO.search(
                new InvoiceDateCriterion(
                        ZonedDateTime.of(2020, 2, 1, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh")),
                        ZonedDateTime.of(2020, 3, 2, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh"))
                ),
                0,
                10
        );
        assertEquals(0, thirdPaginationResult.getTotalItems());
    }

    @Test
    public void searchInvoiceByCustomer() {
        //One match
        PaginationResult<Invoice> firstPaginationResult = invoiceSearchableDAO.search(
                new InvoiceByCustomerCriterion(2L),
                0,
                10
        );
        assertEquals(2L, firstPaginationResult.getTotalItems());
        assertThat(
                firstPaginationResult.getItems(),
                contains(
                        hasProperty("id", is(2L)),
                        hasProperty("id", is(3L))
                )
        );

        //One match
        PaginationResult<Invoice> secondPaginationResult = invoiceSearchableDAO.search(
                new InvoiceByCustomerCriterion(1L),
                0,
                10
        );
        assertEquals(1L, secondPaginationResult.getTotalItems());
        assertThat(
                secondPaginationResult.getItems(),
                contains(
                        hasProperty("id", is(1L))
                )
        );

        //Zero match
        PaginationResult<Invoice> thirdPaginationResult = invoiceSearchableDAO.search(
                new InvoiceByCustomerCriterion(100L),
                0,
                10
        );
        assertEquals(0, thirdPaginationResult.getTotalItems());
    }

    @Test
    public void searchInvoiceByDriver() {
        //Two match
        PaginationResult<Invoice> firstPaginationResult = invoiceSearchableDAO.search(
                new InvoiceByDriverCriterion(1L),
                0,
                10
        );
        assertEquals(2L, firstPaginationResult.getTotalItems());
        assertThat(
                firstPaginationResult.getItems(),
                contains(
                        hasProperty("id", is(1L)),
                        hasProperty("id", is(2L))

                )
        );

        //One match
        PaginationResult<Invoice> secondPaginationResult = invoiceSearchableDAO.search(
                new InvoiceByDriverCriterion(2L),
                0,
                10
        );
        assertEquals(1L, secondPaginationResult.getTotalItems());
        assertThat(
                secondPaginationResult.getItems(),
                contains(
                        hasProperty("id", is(3L))
                )
        );

        //Zero match
        PaginationResult<Invoice> thirdPaginationResult = invoiceSearchableDAO.search(
                new InvoiceByDriverCriterion(100L),
                0,
                10
        );
        assertEquals(0, thirdPaginationResult.getTotalItems());
    }

    @Test
    public void searchInvoiceByDate() {
        //TODO: Search invoice by date
    }
}
