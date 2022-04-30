package com.transonphat.carbooking.search.booking;

import com.transonphat.carbooking.domain.Booking;
import com.transonphat.carbooking.domain.Booking_;
import com.transonphat.carbooking.domain.Customer_;
import com.transonphat.carbooking.domain.Invoice_;
import com.transonphat.carbooking.search.SearchCriterion;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class BookingWithCustomerCriterion implements SearchCriterion<Booking> {
    private final long customerId;

    public BookingWithCustomerCriterion(long customerId) {
        this.customerId = customerId;
    }

    @Override
    public Predicate toPredicate(Root<Booking> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(
                root.get(Booking_.invoice)
                        .get(Invoice_.customer)
                        .get(Customer_.id),
                customerId
        );
    }
}
