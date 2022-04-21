package com.transonphat.carbooking.search.customer;

import com.transonphat.carbooking.domain.Customer;
import com.transonphat.carbooking.search.SearchCriterion;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Locale;

public class CustomerNameCriterion implements SearchCriterion<Customer> {
    private final String name;

    public CustomerNameCriterion(String name) {
        this.name = name;
    }

    @Override
    public Predicate toPredicate(Root<Customer> root, CriteriaBuilder criteriaBuilder) {
        Predicate firstNameLike = criteriaBuilder.like(
                criteriaBuilder.lower(root.<String> get("firstName")),
                "%" + name.toLowerCase(Locale.ROOT) + "%"
        );
        Predicate lastNameLike = criteriaBuilder.like(
                criteriaBuilder.lower(root.<String> get("lastName")),
                "%" + name.toLowerCase(Locale.ROOT) + "%s"
        );
        return criteriaBuilder.or(firstNameLike, lastNameLike);
    }
}
