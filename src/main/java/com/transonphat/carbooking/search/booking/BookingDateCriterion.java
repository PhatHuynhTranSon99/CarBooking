package com.transonphat.carbooking.search.booking;

import com.transonphat.carbooking.domain.Booking;
import com.transonphat.carbooking.domain.Booking_;
import com.transonphat.carbooking.search.SearchCriterion;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.ZonedDateTime;

/**
 * Author: Tran Son Phat
 * Criterion to search all bookings made within a period (from fromDate to toDate)
 */
public class BookingDateCriterion implements SearchCriterion<Booking> {
    private final ZonedDateTime fromDate;
    private final ZonedDateTime toDate;

    public BookingDateCriterion(ZonedDateTime fromDate, ZonedDateTime toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    @Override
    public Predicate toPredicate(Root<Booking> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        return criteriaBuilder.and(
                criteriaBuilder.greaterThanOrEqualTo(root.get(Booking_.startTime), fromDate),
                criteriaBuilder.lessThanOrEqualTo(root.get(Booking_.endTime), toDate)
        );
    }
}
