package com.transonphat.carbooking.search.car;

import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.search.SearchCriterion;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Locale;

/**
 * Author: Tran Son Phat
 * Criterion to search all cars that have a make
 */
public class CarMakeCriterion implements SearchCriterion<Car> {
    private final String make;

    public CarMakeCriterion(String make) {
        this.make = make;
    }

    @Override
    public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        return criteriaBuilder.like(
                criteriaBuilder.lower(root.<String> get("make")),
                "%" + make.toLowerCase(Locale.ROOT) + "%"
        );
    }
}
