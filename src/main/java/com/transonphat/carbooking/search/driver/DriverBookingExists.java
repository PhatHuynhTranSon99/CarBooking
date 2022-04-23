package com.transonphat.carbooking.search.driver;

import com.transonphat.carbooking.domain.*;
import com.transonphat.carbooking.search.SearchCriterion;

import javax.persistence.criteria.*;
import java.time.ZonedDateTime;

public class DriverBookingExists implements SearchCriterion<Driver> {
    private final Long id;
    private final ZonedDateTime startTime;
    private final ZonedDateTime endTime;

    public DriverBookingExists(Long id, ZonedDateTime startTime, ZonedDateTime endTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public Predicate toPredicate(Root<Driver> driverRoot, CriteriaQuery<?> driverQuery, CriteriaBuilder criteriaBuilder) {
        //Create Booking query
        Subquery<Long> driverIdSubQuery = driverQuery.subquery(Long.class);
        Root<Booking> bookingRoot = driverIdSubQuery.from(Booking.class);

        //Check predicate
        Predicate predicate = criteriaBuilder.and(
                criteriaBuilder.lessThan(bookingRoot.get(Booking_.startTime), endTime),
                criteriaBuilder.greaterThan(bookingRoot.get(Booking_.endTime), startTime),
                criteriaBuilder.equal(bookingRoot.get(Booking_.invoice).get(Invoice_.driver), id)
        );
        driverIdSubQuery
                .select(
                        bookingRoot.get(Booking_.invoice)
                                .get(Invoice_.driver)
                                .get(Driver_.id)
                )
                .where(predicate);

        return driverRoot.get(Driver_.id).in(driverIdSubQuery);
    }
}
