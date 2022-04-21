package com.transonphat.carbooking.search.car;

import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.search.SearchCriterion;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Locale;

public class CarColorCriterion implements SearchCriterion<Car> {
    private final String color;

    public CarColorCriterion(String color) {
        this.color = color;
    }

    @Override
    public Predicate toPredicate(Root<Car> root, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.like(
                criteriaBuilder.lower(root.<String> get("color")),
                "%" + color.toLowerCase(Locale.ROOT) + "%"
        );

        return predicate;
    }
}
