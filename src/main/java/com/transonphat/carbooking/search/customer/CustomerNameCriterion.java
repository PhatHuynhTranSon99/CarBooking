package com.transonphat.carbooking.search.customer;

import com.transonphat.carbooking.domain.Customer;
import com.transonphat.carbooking.search.SearchCriterion;

import javax.persistence.criteria.*;
import java.util.Locale;

public class CustomerNameCriterion implements SearchCriterion<Customer> {
    private final String name;

    public CustomerNameCriterion(String name) {
        this.name = name;
    }

    @Override
    public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
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
