package com.transonphat.carbooking.search.car;

import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.domain.Car_;
import com.transonphat.carbooking.domain.Driver_;
import com.transonphat.carbooking.search.SearchCriterion;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class CarHasDriverCriterion implements SearchCriterion<Car> {
    @Override
    public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.isNotNull(root.get(Car_.driver).get(Driver_.id));
    }
}
