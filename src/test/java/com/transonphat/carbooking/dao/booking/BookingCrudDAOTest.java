package com.transonphat.carbooking.dao.booking;

import com.transonphat.carbooking.dao.CrudDAO;
import com.transonphat.carbooking.domain.Booking;
import com.transonphat.carbooking.domain.Invoice;
import com.transonphat.carbooking.exceptions.types.BookingNotFoundException;
import com.transonphat.carbooking.exceptions.types.InvoiceNotFoundException;
import com.transonphat.carbooking.pagination.PaginationResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: Tran Son Phat
 * Unit tests for Crud<DAO> for booking
 */
@SpringBootTest
@ActiveProfiles(profiles = {"test"})
@Transactional
public class BookingCrudDAOTest {
    @Autowired
    private CrudDAO<Booking> bookingCrudDAO;

    @Autowired
    private CrudDAO<Invoice> invoiceCrudDAO;

    @Test
    public void getOneReturnCorrectResult() {
        Booking booking = bookingCrudDAO.getOne(1L);

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
                    bookingCrudDAO.getOne(100L);
                }
        );

        assertEquals("Booking does not exist", exception.getMessage());
    }

    @Test
    public void getAllReturnCorrectResult() {
        PaginationResult<Booking> bookingPaginationResult = bookingCrudDAO.getAll(0, 10);

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
        Booking booking = bookingCrudDAO.delete(1L);

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
        Booking booking = bookingCrudDAO.delete(1L);

        InvoiceNotFoundException exception = assertThrows(
                InvoiceNotFoundException.class,
                () -> {
                    invoiceCrudDAO.getOne(booking.getInvoice().getId());
                }
        );

        assertEquals("Invoice does not exist", exception.getMessage());
    }

    @Test
    public void deleteNotFound() {
        BookingNotFoundException exception = assertThrows(
                BookingNotFoundException.class,
                () -> {
                    bookingCrudDAO.delete(100L);
                }
        );

        assertEquals("Booking does not exist", exception.getMessage());
    }

    @Test
    @Rollback
    public void saveSuccessfully() {
        Booking booking = new Booking();

        booking.setStartLocation("Home");
        booking.setEndLocation("School");
        booking.setStartTime(ZonedDateTime.of(2020, 2, 1, 0, 0, 0, 0,
                ZoneId.of("Asia/Ho_Chi_Minh")));
        booking.setEndTime(ZonedDateTime.of(2020, 2, 5, 0, 0, 0, 0,
                ZoneId.of("Asia/Ho_Chi_Minh")));
        booking.setDistance(100.0);

        bookingCrudDAO.save(booking);

        assertNotNull(booking.getId());
        assertNotNull(booking.getCreatedDate());
    }
}
