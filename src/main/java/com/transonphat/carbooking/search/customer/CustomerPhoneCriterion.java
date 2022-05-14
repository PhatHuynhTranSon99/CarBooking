package com.transonphat.carbooking.search.customer;

import com.transonphat.carbooking.domain.Customer;
import com.transonphat.carbooking.search.SearchCriterion;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Author: Tran Son Phat
 * Criterion to search all customers that has a matching phone number
 */
public class CustomerPhoneCriterion implements SearchCriterion<Customer> {
    private final String phoneNumber;

    public CustomerPhoneCriterion(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        return criteriaBuilder.like(
                root.<String> get("phoneNumber"),
                "%" + phoneNumber + "%"
        );
    }
}
