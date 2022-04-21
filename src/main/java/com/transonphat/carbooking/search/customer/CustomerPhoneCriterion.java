package com.transonphat.carbooking.search.customer;

import com.transonphat.carbooking.domain.Customer;
import com.transonphat.carbooking.search.SearchCriterion;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class CustomerPhoneCriterion implements SearchCriterion<Customer> {
    private final String phoneNumber;

    public CustomerPhoneCriterion(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public Predicate toPredicate(Root<Customer> root, CriteriaBuilder criteriaBuilder) {
        Predicate phoneLike = criteriaBuilder.like(
                root.<String> get("phoneNumber"),
                "%" + phoneNumber + "%"
        );

        return phoneLike;
    }
}
