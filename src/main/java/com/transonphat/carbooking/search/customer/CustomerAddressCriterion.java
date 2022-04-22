package com.transonphat.carbooking.search.customer;

import com.transonphat.carbooking.domain.Customer;
import com.transonphat.carbooking.search.SearchCriterion;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Locale;

public class CustomerAddressCriterion implements SearchCriterion<Customer> {
    private final String address;

    public CustomerAddressCriterion(String address) {
        this.address = address;
    }

    @Override
    public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate addressLike = criteriaBuilder.like(
                criteriaBuilder.lower(root.<String> get("address")),
                "%" + address.toLowerCase(Locale.ROOT) + "%"
        );

        return addressLike;
    }
}
