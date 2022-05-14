package com.transonphat.carbooking.search.driver;

import com.transonphat.carbooking.domain.Driver;
import com.transonphat.carbooking.search.SearchCriterion;

import javax.persistence.criteria.*;
import java.util.Locale;

/**
 * Author: Tran Son Phat
 * Criterion to search all drivers with a matching name
 */
public class DriverNameCriterion implements SearchCriterion<Driver> {
    private final String name;

    public DriverNameCriterion(String name) {
        this.name = name;
    }

    @Override
    public Predicate toPredicate(Root<Driver> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Expression<String> stringExpression = criteriaBuilder.concat(
                criteriaBuilder.lower(root.<String> get("firstName")),
                " "
        );

        stringExpression = criteriaBuilder.concat(
                stringExpression,
                criteriaBuilder.lower(root.<String> get("lastName"))
        );

        return criteriaBuilder.like(
                stringExpression,
                "%" + name.toLowerCase(Locale.ROOT) + "%"
        );
    }
}
