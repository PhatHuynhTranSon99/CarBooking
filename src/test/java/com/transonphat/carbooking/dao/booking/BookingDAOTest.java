package com.transonphat.carbooking.dao.booking;

import com.transonphat.carbooking.dao.DAO;
import com.transonphat.carbooking.domain.Booking;
import com.transonphat.carbooking.domain.Invoice;
import com.transonphat.carbooking.exceptions.BookingNotFoundException;
import com.transonphat.carbooking.exceptions.InvoiceNotFoundException;
import com.transonphat.carbooking.pagination.PaginationResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles(profiles = {"test"})
@Transactional
public class BookingDAOTest {
    @Autowired
    private DAO<Booking> bookingDAO;

    @Autowired
    private DAO<Invoice> invoiceDAO;

    @Test
    public void getOneReturnCorrectResult() {
        Booking booking = bookingDAO.getOne(1L);

        assertEquals(1, booking.getId());
        assertEquals(100.0, booking.getDistance());
        assertEquals("House", booking.getStartLocation());
        assertEquals("School", booking.getEndLocation());
        assertNotNull(booking.getInvoice());
    }

    @Test
    public void getOneNotFound() {
        BookingNotFoundException exception = assertThrows(
                BookingNotFoundException.class,
                () -> {
                    bookingDAO.getOne(100L);
                }
        );

        assertEquals("Booking does not exist", exception.getMessage());
    }

    @Test
    public void getAllReturnCorrectResult() {
        PaginationResult<Booking> bookingPaginationResult = bookingDAO.getAll(0, 10);

        assertEquals(4, bookingPaginationResult.getTotalItems());
        assertThat(
                bookingPaginationResult.getItems(),
                contains(
                        hasProperty("id", is(1L)),
                        hasProperty("id", is(2L)),
                        hasProperty("id", is(3L)),
                        hasProperty("id", is(4L))
                )
        );
    }

    @Test
    @Rollback
    public void deleteSuccessfully() {
        Booking booking = bookingDAO.delete(1L);

        assertEquals(1, booking.getId());
        assertEquals(100.0, booking.getDistance());
        assertEquals("House", booking.getStartLocation());
        assertEquals("School", booking.getEndLocation());
        assertNotNull(booking.getInvoice());
    }

    @Test
    @Rollback
    public void deleteBookingShouldDeleteInvoice() {
        //Deleted booking -> Invoice  not found
        Booking booking = bookingDAO.delete(1L);

        InvoiceNotFoundException exception = assertThrows(
                InvoiceNotFoundException.class,
                () -> {
                    invoiceDAO.getOne(booking.getInvoice().getId());
                }
        );

        assertEquals("Invoice does not exist", exception.getMessage());
    }

    @Test
    public void deleteNotFound() {
        BookingNotFoundException exception = assertThrows(
                BookingNotFoundException.class,
                () -> {
                    bookingDAO.delete(100L);
                }
        );

        assertEquals("Booking does not exist", exception.getMessage());
    }
}
