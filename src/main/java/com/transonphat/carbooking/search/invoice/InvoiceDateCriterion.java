package com.transonphat.carbooking.search.invoice;

import com.transonphat.carbooking.domain.Booking_;
import com.transonphat.carbooking.domain.Invoice;
import com.transonphat.carbooking.domain.Invoice_;
import com.transonphat.carbooking.search.SearchCriterion;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.ZonedDateTime;

public class InvoiceDateCriterion implements SearchCriterion<Invoice> {
    private final ZonedDateTime fromDate;
    private final ZonedDateTime toDate;

    public InvoiceDateCriterion(ZonedDateTime fromDate, ZonedDateTime toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    @Override
    public Predicate toPredicate(Root<Invoice> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.and(
                criteriaBuilder.greaterThanOrEqualTo(root.get(Invoice_.booking).get(Booking_.startTime), fromDate),
                criteriaBuilder.lessThanOrEqualTo(root.get(Invoice_.booking).get(Booking_.endTime), toDate)
        );
    }
}
