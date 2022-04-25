package com.transonphat.carbooking.search.car;

import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.search.SearchCriterion;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class CarConvertibleCriterion implements SearchCriterion<Car> {
    private final boolean isConvertible;

    public CarConvertibleCriterion(boolean isConvertible) {
        this.isConvertible = isConvertible;
    }

    @Override
    public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(
                root.<Boolean> get("isConvertible"),
                isConvertible
        );
    }
}
