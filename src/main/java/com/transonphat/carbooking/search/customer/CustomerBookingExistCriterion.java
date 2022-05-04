package com.transonphat.carbooking.search.customer;

import com.transonphat.carbooking.domain.*;
import com.transonphat.carbooking.search.SearchCriterion;

import javax.persistence.criteria.*;
import java.time.ZonedDateTime;

public class CustomerBookingExistCriterion implements SearchCriterion<Customer> {
    private final long customerId;
    private final ZonedDateTime startTime;
    private final ZonedDateTime endTime;

    public CustomerBookingExistCriterion(long customerId, ZonedDateTime startTime, ZonedDateTime endTime) {
        this.customerId = customerId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Subquery<Long> customerIdQuery = query.subquery(Long.class);
        Root<Booking> bookingRoot = customerIdQuery.from(Booking.class);

        //Query booking
        Predicate predicate = criteriaBuilder.and(
                criteriaBuilder.lessThan(bookingRoot.get(Booking_.startTime), endTime),
                criteriaBuilder.greaterThan(bookingRoot.get(Booking_.endTime), startTime),
                criteriaBuilder.equal(
                        bookingRoot.get(Booking_.invoice)
                            .get(Invoice_.customer)
                            .get(Customer_.id),
                        customerId
                )
        );
        customerIdQuery
                .select(
                        bookingRoot.get(Booking_.invoice)
                                .get(Invoice_.customer)
                                .get(Customer_.id)
                )
                .where(predicate);

        return root.get(Car_.id).in(customerIdQuery);
    }
}
