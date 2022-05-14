package com.transonphat.carbooking.aggregation;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;

/**
 * Author: Tran Son Phat
 * Interface for creating a MySQL query
 * @param <T>: The return type of the query
 */
public interface AggregationQuery<T> {
    T execute(EntityManager entityManager, CriteriaBuilder criteriaBuilder);
}
