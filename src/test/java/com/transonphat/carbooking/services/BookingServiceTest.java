package com.transonphat.carbooking.services;

import com.transonphat.carbooking.dao.DAO;
import com.transonphat.carbooking.dao.ExhaustiveSearchableDAO;
import com.transonphat.carbooking.dao.ExistenceDAO;
import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Booking;
import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.search.SearchCriterion;
import com.transonphat.carbooking.search.booking.BookingWithCarCriterion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {BookingService.class})
@ActiveProfiles(profiles = {"test"})
public class BookingServiceTest {
    @Autowired
    private BookingService bookingService;

    @MockBean
    private DAO<Booking> bookingDao;

    @MockBean
    private SearchableDAO<Booking> bookingSearchableDAO;

    @MockBean
    private ExhaustiveSearchableDAO<Booking> exhaustiveSearchableDAO;

    @MockBean
    private SearchableDAO<Car> carSearchableDAO;

    @MockBean
    private ExistenceDAO<Car> carExistenceDAO;

    @Test
    public void searchAvailableCarsShouldCallDAOMethod() {
        //Establish parameter
        ZonedDateTime startTime = ZonedDateTime.of(2020, 2, 1, 0, 0, 0, 0,
                ZoneId.of("Asia/Ho_Chi_Minh"));
        ZonedDateTime endTime = ZonedDateTime.of(2020, 3, 2, 0, 0, 0, 0,
                ZoneId.of("Asia/Ho_Chi_Minh"));
        int currentPage = 0;
        int pageSize = 10;

        //Call method
        bookingService.findAvailableCars(startTime, endTime, currentPage, pageSize);

        //Assert calls
        verify(carSearchableDAO).search(any(), eq(currentPage), eq(pageSize));
    }

    @Test
    public void checkIfCarAvailableShouldCallMethod() {
        //Establish parameter
        ZonedDateTime startTime = ZonedDateTime.of(2020, 2, 1, 0, 0, 0, 0,
                ZoneId.of("Asia/Ho_Chi_Minh"));
        ZonedDateTime endTime = ZonedDateTime.of(2020, 3, 2, 0, 0, 0, 0,
                ZoneId.of("Asia/Ho_Chi_Minh"));
        long carId = 1;

        //Call method
        bookingService.checkIfCarIsAvailable(carId, startTime, endTime);

        //Assert method call
        verify(carExistenceDAO).exists(any());
    }

    @Test
    public void searchBookingShouldCallDAOMethod() {
        //Create criterion
        SearchCriterion<Booking> bookingSearchCriterion = new BookingWithCarCriterion(1L);

        //Call search
        bookingService.searchBookings(bookingSearchCriterion, 0, 10);

        //Assert call
        verify(bookingSearchableDAO).search(bookingSearchCriterion, 0, 10);
    }

    @Test
    public void deleteBookingShouldCallDAOMethod() {
        //Establish parameter
        long bookingId = 1L;

        //Call method
        bookingService.deleteBooking(bookingId);

        //Assert method call
        verify(bookingDao).delete(bookingId);
    }

    @Test
    public void deleteRelatedBookingsWithCarShouldCallMethod() {
        Booking bookingOne = new Booking();
        bookingOne.setId(1L);

        Booking bookingTwo = new Booking();
        bookingTwo.setId(2L);

        Booking bookingThree = new Booking();
        bookingThree.setId(3L);

        when(exhaustiveSearchableDAO.search(any())).thenReturn(
                List.of(
                        bookingOne,
                        bookingTwo,
                        bookingThree
                )
        );

        //Call method
        bookingService.deleteRelatedBookingsWithCar(1L);

        //Assert method call
        verify(exhaustiveSearchableDAO).search(any());
        verify(bookingDao, times(3)).delete(anyLong());
    }

    @Test
    public void deleteRelatedBookingsWithDriverShouldCallMethod() {
        Booking bookingOne = new Booking();
        bookingOne.setId(1L);

        Booking bookingTwo = new Booking();
        bookingTwo.setId(2L);

        Booking bookingThree = new Booking();
        bookingThree.setId(3L);

        when(exhaustiveSearchableDAO.search(any())).thenReturn(
                List.of(
                        bookingOne,
                        bookingTwo,
                        bookingThree
                )
        );

        //Call method
        bookingService.deleteRelatedBookingsWithDriver(1L);

        //Assert method call
        verify(exhaustiveSearchableDAO).search(any());
        verify(bookingDao, times(3)).delete(anyLong());
    }

    @Test
    public void deleteRelatedBookingsWithCustomerShouldCallMethod() {
        Booking bookingOne = new Booking();
        bookingOne.setId(1L);

        Booking bookingTwo = new Booking();
        bookingTwo.setId(2L);

        Booking bookingThree = new Booking();
        bookingThree.setId(3L);

        when(exhaustiveSearchableDAO.search(any())).thenReturn(
                List.of(
                        bookingOne,
                        bookingTwo,
                        bookingThree
                )
        );

        //Call method
        bookingService.deleteRelatedBookingsWithCustomer(1L);

        //Assert method call
        verify(exhaustiveSearchableDAO).search(any());
        verify(bookingDao, times(3)).delete(anyLong());
    }
}
