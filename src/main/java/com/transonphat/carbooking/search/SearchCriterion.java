package com.transonphat.carbooking.search;

import com.transonphat.carbooking.domain.Model;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface SearchCriterion<T extends Model> {
    Predicate toPredicate(Root<T> root, CriteriaBuilder criteriaBuilder);
}
