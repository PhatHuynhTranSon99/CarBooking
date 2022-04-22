package com.transonphat.carbooking.search.driver;

import com.transonphat.carbooking.domain.Driver;
import com.transonphat.carbooking.search.SearchCriterion;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Locale;

public class DriverNameCriterion implements SearchCriterion<Driver> {
    private final String name;

    public DriverNameCriterion(String name) {
        this.name = name;
    }

    @Override
    public Predicate toPredicate(Root<Driver> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate firstNameLike = criteriaBuilder.like(
                criteriaBuilder.lower(root.<String> get("firstName")),
                "%" + name.toLowerCase(Locale.ROOT) + "%"
        );

        Predicate lastNameLike = criteriaBuilder.like(
                criteriaBuilder.lower(root.<String> get("lastName")),
                "%" + name.toLowerCase(Locale.ROOT) + "%"
        );

        return criteriaBuilder.or(firstNameLike, lastNameLike);
    }
}
