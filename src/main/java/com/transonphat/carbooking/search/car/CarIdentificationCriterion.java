package com.transonphat.carbooking.search.car;

import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.search.SearchCriterion;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Locale;

public class CarIdentificationCriterion implements SearchCriterion<Car> {
    private final String identificationNumber;

    public CarIdentificationCriterion(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    @Override
    public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        return criteriaBuilder.like(
                criteriaBuilder.lower(root.<String> get("identificationNumber")),
                "%" + identificationNumber.toLowerCase(Locale.ROOT) + "%"
        );
    }
}
