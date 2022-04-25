package com.transonphat.carbooking.search.driver;

import com.transonphat.carbooking.domain.Driver;
import com.transonphat.carbooking.search.SearchCriterion;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class DriverPhoneCriterion implements SearchCriterion<Driver> {
    private final String phoneNumber;

    public DriverPhoneCriterion(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public Predicate toPredicate(Root<Driver> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        return criteriaBuilder.like(
                root.<String> get("phoneNumber"),
                "%" + phoneNumber + "%"
        );
    }
}
