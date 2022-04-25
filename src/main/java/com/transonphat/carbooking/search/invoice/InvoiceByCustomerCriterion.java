package com.transonphat.carbooking.search.invoice;

import com.transonphat.carbooking.domain.Customer_;
import com.transonphat.carbooking.domain.Invoice;
import com.transonphat.carbooking.domain.Invoice_;
import com.transonphat.carbooking.search.SearchCriterion;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class InvoiceByCustomerCriterion implements SearchCriterion<Invoice> {
    private final long customerId;

    public InvoiceByCustomerCriterion(long customerId) {
        this.customerId = customerId;
    }

    @Override
    public Predicate toPredicate(Root<Invoice> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get(Invoice_.customer).get(Customer_.id), customerId);
    }
}
