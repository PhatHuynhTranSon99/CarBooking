package com.transonphat.carbooking.search.booking;

import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Booking;
import com.transonphat.carbooking.pagination.PaginationResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles(profiles = {"test"})
public class BookingSearchableCrudDAOTest {
    @Autowired
    private SearchableDAO<Booking> bookingSearchableDAO;

    @Test
    public void searchBookingWithinARange() {
        //3 match
        PaginationResult<Booking> firstPaginationResult = bookingSearchableDAO.search(
                new BookingDateCriterion(
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
        PaginationResult<Booking> secondPaginationResult = bookingSearchableDAO.search(
                new BookingDateCriterion(
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
        PaginationResult<Booking> thirdPaginationResult = bookingSearchableDAO.search(
                new BookingDateCriterion(
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
    public void searchBookingWithCar() {
        //Two match
        PaginationResult<Booking> firstPaginationResult = bookingSearchableDAO.search(
                new BookingWithCarCriterion(1L),
                0,
                10
        );
        assertEquals(2, firstPaginationResult.getTotalItems());
        assertThat(
                firstPaginationResult.getItems(),
                contains(
                        hasProperty("id", is(1L)),
                        hasProperty("id", is(2L))
                )
        );

        //One match
        PaginationResult<Booking> secondPaginationResult = bookingSearchableDAO.search(
                new BookingWithCarCriterion(2L),
                0,
                10
        );
        assertEquals(1, secondPaginationResult.getTotalItems());
        assertThat(
                secondPaginationResult.getItems(),
                contains(
                        hasProperty("id", is(3L))
                )
        );

        //Zero match
        PaginationResult<Booking> thirdPaginationResult = bookingSearchableDAO.search(
                new BookingWithCarCriterion(10L),
                0,
                10
        );
        assertEquals(0, thirdPaginationResult.getTotalItems());
    }

    @Test
    public void searchBookingWithCustomer() {
        //One match
        PaginationResult<Booking> firstPaginationResult = bookingSearchableDAO.search(
                new BookingWithCustomerCriterion(2L),
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
        PaginationResult<Booking> secondPaginationResult = bookingSearchableDAO.search(
                new BookingWithCustomerCriterion(1L),
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
        PaginationResult<Booking> thirdPaginationResult = bookingSearchableDAO.search(
                new BookingWithCustomerCriterion(100L),
                0,
                10
        );
        assertEquals(0, thirdPaginationResult.getTotalItems());
    }

    @Test
    public void searchBookingWithDriver() {
        //Two match
        PaginationResult<Booking> firstPaginationResult = bookingSearchableDAO.search(
                new BookingWithDriverCriterion(1L),
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
        PaginationResult<Booking> secondPaginationResult = bookingSearchableDAO.search(
                new BookingWithDriverCriterion(2L),
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
        PaginationResult<Booking> thirdPaginationResult = bookingSearchableDAO.search(
                new BookingWithDriverCriterion(100L),
                0,
                10
        );
        assertEquals(0, thirdPaginationResult.getTotalItems());
    }
}
