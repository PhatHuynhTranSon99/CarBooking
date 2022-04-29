package com.transonphat.carbooking.seed;

import com.transonphat.carbooking.domain.*;
import com.transonphat.carbooking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Component
@Profile("!test")
public class DataSeed implements CommandLineRunner {
    private final CustomerRepository customerRepository;
    private final DriverRepository driverRepository;
    private final CarRepository carRepository;
    private final BookingRepository bookingRepository;
    private final InvoiceRepository invoiceRepository;

    public DataSeed(CustomerRepository customerRepository,
                    DriverRepository driverRepository,
                    CarRepository carRepository, BookingRepository bookingRepository, InvoiceRepository invoiceRepository) {
        this.customerRepository = customerRepository;
        this.driverRepository = driverRepository;
        this.carRepository = carRepository;
        this.bookingRepository = bookingRepository;
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //Create some customers
        Customer[] customers = {
                new Customer(1L, "Adam", "Cole", "Street", "0182019222", ZonedDateTime.now()),
                new Customer(2L, "Kyle", "O'Reily", "Street", "0172172812", ZonedDateTime.now()),
                new Customer(3L, "Osborn", "Norman", "Street", "2817283385", ZonedDateTime.now()),
        };

        //Create some cars
        Car carOne = new CarBuilder()
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

        Car carTwo = new CarBuilder()
                .setId(2L)
                .setMake("Mazda")
                .setModel("CSV")
                .setColor("Blue")
                .setConvertible(false)
                .setIdentificationNumber("0111-012")
                .setLicensePlate("G1-5655")
                .setRating(3.6)
                .setRate(5.5)
                .build();

        Car carThree = new CarBuilder()
                .setId(3L)
                .setMake("Volkswagen")
                .setModel("Axel")
                .setColor("Yellow")
                .setConvertible(false)
                .setIdentificationNumber("0914-129")
                .setLicensePlate("G2-0173")
                .setRating(5.0)
                .setRate(15.4)
                .build();

        carRepository.saveAll(List.of(carOne, carTwo, carThree));

        //Create some drivers
        Driver driverOne = new Driver(1L, "Adam", "Levine",
                "0909111909", 4.9, ZonedDateTime.now());
        Driver driverTwo = new Driver(2L, "Mark", "Rubber",
                "0978101929", 4.5, ZonedDateTime.now());
        Driver driverThree = new Driver(3L, "Jay", "Langdon",
                "0192181921", 5.0, ZonedDateTime.now());

        driverOne.setCar(carOne);
        driverTwo.setCar(carTwo);
        driverThree.setCar(carThree);

        driverRepository.saveAll(List.of(driverOne, driverTwo, driverThree));

        //Create some invoices and bookings
        Invoice invoiceOne = new Invoice();
        invoiceOne.setId(1L);
        invoiceOne.setDriver(driverOne);
        invoiceOne.setCustomer(customers[0]);
        invoiceOne.setTotalCharges(100.0);
        invoiceOne.setCreatedDate(ZonedDateTime.now());

        Booking bookingOne = new Booking();
        bookingOne.setId(1L);
        bookingOne.setDistance(100.0);
        bookingOne.setStartLocation("House");
        bookingOne.setEndLocation("School");
        bookingOne.setStartTime(ZonedDateTime.of(2020, 1, 1, 0, 0, 0, 0,
                ZoneId.of("Asia/Ho_Chi_Minh")));
        bookingOne.setEndTime(ZonedDateTime.of(2020, 1, 5, 0, 0, 0, 0,
                ZoneId.of("Asia/Ho_Chi_Minh")));
        bookingOne.setInvoice(invoiceOne);
        bookingOne.setCreatedDate(ZonedDateTime.now());

        Invoice invoiceTwo = new Invoice();
        invoiceTwo.setId(2L);
        invoiceTwo.setDriver(driverOne);
        invoiceTwo.setCustomer(customers[1]);
        invoiceTwo.setTotalCharges(150.0);
        invoiceTwo.setCreatedDate(ZonedDateTime.now());

        Booking bookingTwo = new Booking();
        bookingTwo.setId(2L);
        bookingTwo.setDistance(100.0);
        bookingTwo.setStartLocation("House");
        bookingTwo.setEndLocation("School");
        bookingTwo.setStartTime(ZonedDateTime.of(2020, 1, 20, 0, 0, 0, 0,
                ZoneId.of("Asia/Ho_Chi_Minh")));
        bookingTwo.setEndTime(ZonedDateTime.of(2020, 3, 1, 0, 0, 0, 0,
                ZoneId.of("Asia/Ho_Chi_Minh")));
        bookingTwo.setInvoice(invoiceTwo);
        bookingTwo.setCreatedDate(ZonedDateTime.now());

        Invoice invoiceThree = new Invoice();
        invoiceThree.setId(3L);
        invoiceThree.setDriver(driverTwo);
        invoiceThree.setCustomer(customers[1]);
        invoiceThree.setTotalCharges(100.0);
        invoiceThree.setCreatedDate(ZonedDateTime.now());

        Booking bookingThree = new Booking();
        bookingThree.setId(3L);
        bookingThree.setDistance(100.0);
        bookingThree.setStartLocation("House");
        bookingThree.setEndLocation("School");
        bookingThree.setStartTime(ZonedDateTime.of(2020, 1, 1, 0, 0, 0, 0,
                ZoneId.of("Asia/Ho_Chi_Minh")));
        bookingThree.setEndTime(ZonedDateTime.of(2020, 1, 5, 0, 0, 0, 0,
                ZoneId.of("Asia/Ho_Chi_Minh")));
        bookingThree.setInvoice(invoiceThree);
        bookingThree.setCreatedDate(ZonedDateTime.now());

        Invoice invoiceFour = new Invoice();
        invoiceFour.setId(4L);
        invoiceFour.setDriver(driverThree);
        invoiceFour.setCustomer(customers[2]);
        invoiceFour.setTotalCharges(100.1);
        invoiceFour.setCreatedDate(ZonedDateTime.now());

        Booking bookingFour = new Booking();
        bookingFour.setId(4L);
        bookingFour.setDistance(100.0);
        bookingFour.setStartLocation("House");
        bookingFour.setEndLocation("School");
        bookingFour.setStartTime(ZonedDateTime.of(2020, 1, 1, 0, 0, 0, 0,
                ZoneId.of("Asia/Ho_Chi_Minh")));
        bookingFour.setEndTime(ZonedDateTime.of(2020, 1, 5, 0, 0, 0, 0,
                ZoneId.of("Asia/Ho_Chi_Minh")));
        bookingFour.setInvoice(invoiceFour);
        bookingFour.setCreatedDate(ZonedDateTime.now());

        //Save
        customerRepository.saveAll(List.of(customers));
        invoiceRepository.saveAll(List.of(invoiceOne, invoiceTwo, invoiceThree, invoiceFour));
        bookingRepository.saveAll(List.of(bookingOne, bookingTwo, bookingThree, bookingFour));
    }
}
