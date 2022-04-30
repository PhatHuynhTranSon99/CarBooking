package com.transonphat.carbooking.dao.booking;

import com.transonphat.carbooking.dao.DAO;
import com.transonphat.carbooking.dao.ExhaustiveSearchableDAO;
import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Booking;
import com.transonphat.carbooking.exceptions.BookingNotFoundException;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.repositories.BookingRepository;
import com.transonphat.carbooking.search.SearchCriterion;
import com.transonphat.carbooking.search.SearchSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MySQLBookingDAO implements DAO<Booking>, SearchableDAO<Booking>, ExhaustiveSearchableDAO<Booking> {
    private final BookingRepository bookingRepository;

    public MySQLBookingDAO(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Booking save(Booking booking) {
        return this.bookingRepository.save(booking);
    }

    @Override
    public Booking delete(long id) {
        Booking booking = this.getOne(id);
        this.bookingRepository.delete(booking);
        return booking;
    }

    @Override
    public PaginationResult<Booking> getAll(int currentPage, int pageSize) {
        //Get page request
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        //Get all bookings from page
        Page<Booking> bookingPage = this.bookingRepository.findAll(pageable);

        //Map to PaginationResult

        return new PaginationResult<>(
                bookingPage.getTotalElements(),
                bookingPage.get().collect(Collectors.toList()),
                bookingPage.getNumber(),
                bookingPage.getTotalPages()
        );
    }

    @Override
    public Booking getOne(long id) {
        return this.bookingRepository.findById(id).orElseThrow(BookingNotFoundException::new);
    }

    @Override
    public PaginationResult<Booking> search(SearchCriterion<Booking> criteria, int currentPage, int pageSize) {
        //Get page request
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        //Get all bookings from page
        Page<Booking> bookingPage = this.bookingRepository.findAll(
                new SearchSpecification<>(criteria),
                pageable
        );

        //Map to PaginationResult

        return new PaginationResult<>(
                bookingPage.getTotalElements(),
                bookingPage.get().collect(Collectors.toList()),
                bookingPage.getNumber(),
                bookingPage.getTotalPages()
        );
    }

    @Override
    public Iterable<Booking> search(SearchCriterion<Booking> criterion) {
        return this.bookingRepository.findAll(new SearchSpecification<>(criterion));
    }
}
