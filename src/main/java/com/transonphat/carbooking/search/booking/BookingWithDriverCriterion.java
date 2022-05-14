package com.transonphat.carbooking.search.booking;

import com.transonphat.carbooking.domain.Booking;
import com.transonphat.carbooking.domain.Booking_;
import com.transonphat.carbooking.domain.Driver_;
import com.transonphat.carbooking.domain.Invoice_;
import com.transonphat.carbooking.search.SearchCriterion;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Author: Tran Son Phat
 * Criterion to search all bookings made with a driver (with driverId)
 */
public class BookingWithDriverCriterion implements SearchCriterion<Booking> {
    private final long driverId;

    public BookingWithDriverCriterion(long driverId) {
        this.driverId = driverId;
    }

    @Override
    public Predicate toPredicate(Root<Booking> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(
                root.get(Booking_.invoice).get(Invoice_.driver).get(Driver_.id),
                driverId
        );
    }
}
