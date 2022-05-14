package com.transonphat.carbooking.search;

import com.transonphat.carbooking.domain.Model;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Author: Tran Son Phat
 * Generic interface representing a search criterion (WHERE clause in MySQL)
 * @param <T>: The type of the entity (must extend the Model base class)
 */
public interface SearchCriterion<T extends Model> {
    Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder);
}
