package com.transonphat.carbooking.services;

import com.transonphat.carbooking.dao.CrudDAO;
import com.transonphat.carbooking.dao.ExhaustiveSearchableDAO;
import com.transonphat.carbooking.dao.ExistenceDAO;
import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.*;
import com.transonphat.carbooking.exceptions.types.*;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {BookingService.class})
@ActiveProfiles(profiles = {"test"})
public class BookingServiceTest {
    @Autowired
    private BookingService bookingService;

    @MockBean
    private CrudDAO<Booking> bookingCrudDao;

    @MockBean
    private SearchableDAO<Booking> bookingSearchableDAO;

    @MockBean
    private ExhaustiveSearchableDAO<Booking> exhaustiveSearchableDAO;

    @MockBean
    private SearchableDAO<Car> carSearchableDAO;

    @MockBean
    private ExistenceDAO<Car> carExistenceDAO;

    @MockBean
    private InvoiceService invoiceService;

    @MockBean
    private CarService carService;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private ExistenceDAO<Customer> customerExistenceDAO;

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
        verify(bookingCrudDao).delete(bookingId);
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
        verify(bookingCrudDao, times(3)).delete(anyLong());
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
        verify(bookingCrudDao, times(3)).delete(anyLong());
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
        verify(bookingCrudDao, times(3)).delete(anyLong());
    }

    @Test
    public void shouldCreateBookingSuccessfully() {
        //Establish parameters
        String startLocation = "Home", endLocation = "House";
        ZonedDateTime startTime = ZonedDateTime.of(2020, 2, 1, 0, 0, 0, 0,
                ZoneId.of("Asia/Ho_Chi_Minh"));
        ZonedDateTime endTime = ZonedDateTime.of(2020, 2, 5, 0, 0, 0, 0,
                ZoneId.of("Asia/Ho_Chi_Minh"));
        double distance = 100.0;
        long carId = 1L;
        long customerId = 1L;

        //Set car and customer
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
        car.setDriver(new Driver());

        //Customer
        Customer customer = new Customer(1L, "Adam", "Cole", "11 Street",
                "0182019222", ZonedDateTime.now());

        when(customerService.getCustomerById(1L)).thenReturn(customer);
        when(carService.getCarById(1L)).thenReturn(car);

        //Call
        bookingService.createBooking(
                startLocation,
                endLocation,
                startTime,
                endTime,
                distance,
                carId,
                customerId
        );

        //Assert calls
        verify(invoiceService).createInvoice(customer, car, distance);
        verify(bookingCrudDao).save(any());
    }

    @Test
    public void shouldThrowExceptionWhenCarDoesNotHaveDriver() {
        //Establish parameters
        String startLocation = "Home", endLocation = "House";
        ZonedDateTime startTime = ZonedDateTime.of(2020, 2, 1, 0, 0, 0, 0,
                ZoneId.of("Asia/Ho_Chi_Minh"));
        ZonedDateTime endTime = ZonedDateTime.of(2020, 2, 5, 0, 0, 0, 0,
                ZoneId.of("Asia/Ho_Chi_Minh"));
        double distance = 100.0;
        long carId = 1L;
        long customerId = 1L;

        //Set car and customer
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
        car.setDriver(null); //No driver

        //Customer
        Customer customer = new Customer(1L, "Adam", "Cole", "11 Street",
                "0182019222", ZonedDateTime.now());

        when(customerService.getCustomerById(1L)).thenReturn(customer);
        when(carService.getCarById(1L)).thenReturn(car);

        //Call
        CarDoesNotHaveDriverException exception = assertThrows(
                CarDoesNotHaveDriverException.class,
                () -> {
                    bookingService.createBooking(
                            startLocation,
                            endLocation,
                            startTime,
                            endTime,
                            distance,
                            carId,
                            customerId
                    );
                }
        );

        assertEquals("Car does not have a driver yet", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenCarIsNotAvailable() {
        //Establish parameters
        String startLocation = "Home", endLocation = "House";
        ZonedDateTime startTime = ZonedDateTime.of(2020, 2, 1, 0, 0, 0, 0,
                ZoneId.of("Asia/Ho_Chi_Minh"));
        ZonedDateTime endTime = ZonedDateTime.of(2020, 2, 5, 0, 0, 0, 0,
                ZoneId.of("Asia/Ho_Chi_Minh"));
        double distance = 100.0;
        long carId = 1L;
        long customerId = 1L;

        //Set car and customer
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
        car.setDriver(new Driver());

        //Customer
        Customer customer = new Customer(1L, "Adam", "Cole", "11 Street",
                "0182019222", ZonedDateTime.now());

        when(customerService.getCustomerById(1L)).thenReturn(customer);
        when(carService.getCarById(1L)).thenReturn(car);
        when(carExistenceDAO.exists(any())).thenReturn(true); //This will set car to not be available

        //Call
        CarNotAvailableException exception = assertThrows(
                CarNotAvailableException.class,
                () -> {
                    bookingService.createBooking(
                            startLocation,
                            endLocation,
                            startTime,
                            endTime,
                            distance,
                            carId,
                            customerId
                    );
                }
        );

        //Assert exception message
        assertEquals("Car is not available during the trip", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenCustomerIsNotAvailable() {
        //Establish parameters
        String startLocation = "Home", endLocation = "House";
        ZonedDateTime startTime = ZonedDateTime.of(2020, 2, 1, 0, 0, 0, 0,
                ZoneId.of("Asia/Ho_Chi_Minh"));
        ZonedDateTime endTime = ZonedDateTime.of(2020, 2, 5, 0, 0, 0, 0,
                ZoneId.of("Asia/Ho_Chi_Minh"));
        double distance = 100.0;
        long carId = 1L;
        long customerId = 1L;

        //Set car and customer
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
        car.setDriver(new Driver());

        //Customer
        Customer customer = new Customer(1L, "Adam", "Cole", "11 Street",
                "0182019222", ZonedDateTime.now());

        when(customerService.getCustomerById(1L)).thenReturn(customer);
        when(carService.getCarById(1L)).thenReturn(car);
        when(carExistenceDAO.exists(any())).thenReturn(false);
        when(customerExistenceDAO.exists(any())).thenReturn(true); //Set customer to be unavailable

        //Call
        CustomerNotAvailableException exception = assertThrows(
                CustomerNotAvailableException.class,
                () -> {
                    bookingService.createBooking(
                            startLocation,
                            endLocation,
                            startTime,
                            endTime,
                            distance,
                            carId,
                            customerId
                    );
                }
        );

        //Assert exception message
        assertEquals("Customer has made another booking that overlap this booking", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenStartTimeIsGreaterThanEndTime() {
        //Establish parameters
        String startLocation = "Home", endLocation = "House";

        //Start time comes after end time
        ZonedDateTime startTime = ZonedDateTime.of(2020, 3, 1, 0, 0, 0, 0,
                ZoneId.of("Asia/Ho_Chi_Minh"));
        ZonedDateTime endTime = ZonedDateTime.of(2020, 2, 5, 0, 0, 0, 0,
                ZoneId.of("Asia/Ho_Chi_Minh"));
        double distance = 100.0;
        long carId = 1L;
        long customerId = 1L;

        //Set car and customer
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
        car.setDriver(new Driver());

        //Customer
        Customer customer = new Customer(1L, "Adam", "Cole", "11 Street",
                "0182019222", ZonedDateTime.now());

        when(customerService.getCustomerById(1L)).thenReturn(customer);
        when(carService.getCarById(1L)).thenReturn(car);

        //Call
        InvalidTimePeriodException exception = assertThrows(
                InvalidTimePeriodException.class,
                () -> {
                    bookingService.createBooking(
                            startLocation,
                            endLocation,
                            startTime,
                            endTime,
                            distance,
                            carId,
                            customerId
                    );
                }
        );

        //Assert exception message
        assertEquals("Starting time must come before ending time", exception.getMessage());
    }

    @Test
    public void shouldUpdateBookingSuccessfully() {
        //Establish parameters
        long bookingId = 1L;
        String startLocation = "New Home", endLocation = "New School";
        ZonedDateTime startTime = ZonedDateTime.of(2020, 3, 1, 0, 0, 0, 0,
                ZoneId.of("Asia/Ho_Chi_Minh"));
        ZonedDateTime endTime = ZonedDateTime.of(2020, 3, 9, 0, 0, 0, 0,
                ZoneId.of("Asia/Ho_Chi_Minh"));
        double distance = 300.0;

        //Set car and customer
        Driver driver = new Driver(1L, "Adam", "Levine",
                "0909111909", 4.9, ZonedDateTime.now());

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

        car.setDriver(driver);
        driver.setCar(car);

        //Customer
        Customer customer = new Customer(1L, "Adam", "Cole", "11 Street",
                "0182019222", ZonedDateTime.now());

        //Invoice and booking
        Invoice invoice = new Invoice();
        invoice.setId(1L);
        invoice.setDriver(driver);
        invoice.setCustomer(customer);
        invoice.setTotalCharges(100.0);
        invoice.setCreatedDate(ZonedDateTime.now());

        Invoice newInvoice = new Invoice();
        newInvoice.setId(1L);
        newInvoice.setDriver(driver);
        newInvoice.setCustomer(customer);
        newInvoice.setTotalCharges(100.0);
        newInvoice.setCreatedDate(ZonedDateTime.now());

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setDistance(100.0);
        booking.setStartLocation("House");
        booking.setEndLocation("School");
        booking.setStartTime(ZonedDateTime.of(2020, 1, 1, 0, 0, 0, 0,
                ZoneId.of("Asia/Ho_Chi_Minh")));
        booking.setEndTime(ZonedDateTime.of(2020, 1, 5, 0, 0, 0, 0,
                ZoneId.of("Asia/Ho_Chi_Minh")));
        booking.setInvoice(invoice);
        booking.setCreatedDate(ZonedDateTime.now());

        Booking newBooking = new Booking();
        newBooking.setId(1L);
        newBooking.setDistance(distance);
        newBooking.setStartLocation(startLocation);
        newBooking.setEndLocation(endLocation);
        newBooking.setStartTime(startTime);
        newBooking.setEndTime(endTime);
        newBooking.setInvoice(newInvoice);
        newBooking.setCreatedDate(ZonedDateTime.now());

        when(invoiceService.deleteInvoice(1L)).thenReturn(invoice);
        when(invoiceService.createInvoice(any(), any(), anyDouble())).thenReturn(newInvoice);
        when(bookingCrudDao.getOne(1L)).thenReturn(booking);
        when(bookingCrudDao.save(any())).thenReturn(newBooking);

        //Call
        bookingService.updateBooking(
                bookingId,
                startLocation,
                endLocation,
                startTime,
                endTime,
                distance
        );

        //Assert calls
        verify(invoiceService).deleteInvoice(1L);
        verify(invoiceService).createInvoice(customer, car, distance);
        verify(bookingCrudDao).save(any());
    }

    @Test
    public void updateBookingShouldThrowExceptionWhenStartTimeIsMissing() {
        //Establish parameters
        long bookingId = 1L;
        String startLocation = "New Home", endLocation = "New School";
        ZonedDateTime startTime = ZonedDateTime.of(2020, 3, 1, 0, 0, 0, 0,
                ZoneId.of("Asia/Ho_Chi_Minh"));
        double distance = 300.0;

        //Assert exception
        MissingPeriodException exception = assertThrows(
                MissingPeriodException.class,
                () -> {
                    bookingService.updateBooking(
                            bookingId,
                            startLocation,
                            endLocation,
                            startTime,
                            null,
                            distance
                    );
                }
        );

        //Assert exception message
        assertEquals("Start and end time must both be present", exception.getMessage());
    }

    @Test
    public void updateBookingShouldThrowExceptionWhenStartTimeIsAfterEndTime() {
        //Establish parameters
        long bookingId = 1L;
        String startLocation = "New Home", endLocation = "New School";
        ZonedDateTime startTime = ZonedDateTime.of(2020, 3, 1, 0, 0, 0, 0,
                ZoneId.of("Asia/Ho_Chi_Minh"));
        ZonedDateTime endTime = ZonedDateTime.of(2019, 3, 9, 0, 0, 0, 0,
                ZoneId.of("Asia/Ho_Chi_Minh"));
        double distance = 300.0;

        //Assert exception
        InvalidTimePeriodException exception = assertThrows(
                InvalidTimePeriodException.class,
                () -> {
                    bookingService.updateBooking(
                            bookingId,
                            startLocation,
                            endLocation,
                            startTime,
                            endTime,
                            distance
                    );
                }
        );

        //Assert exception message
        assertEquals("Starting time must come before ending time", exception.getMessage());
    }
}
