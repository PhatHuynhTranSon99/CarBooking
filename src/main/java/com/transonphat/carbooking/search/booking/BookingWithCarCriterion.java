package com.transonphat.carbooking.search.booking;

import com.transonphat.carbooking.domain.*;
import com.transonphat.carbooking.search.SearchCriterion;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class BookingWithCarCriterion implements SearchCriterion<Booking> {
    private final long carId;

    public BookingWithCarCriterion(long carId) {
        this.carId = carId;
    }

    @Override
    public Predicate toPredicate(Root<Booking> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(
                root.get(Booking_.invoice)
                        .get(Invoice_.driver)
                        .get(Driver_.car)
                        .get(Car_.id),
                carId
        );
    }
}
