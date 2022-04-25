package com.transonphat.carbooking.search.invoice;

import com.transonphat.carbooking.domain.Driver_;
import com.transonphat.carbooking.domain.Invoice;
import com.transonphat.carbooking.domain.Invoice_;
import com.transonphat.carbooking.search.SearchCriterion;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class InvoiceByDriverCriterion implements SearchCriterion<Invoice> {
    private final long driverId;

    public InvoiceByDriverCriterion(long driverId) {
        this.driverId = driverId;
    }

    @Override
    public Predicate toPredicate(Root<Invoice> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get(Invoice_.driver).get(Driver_.id), driverId);
    }
}
