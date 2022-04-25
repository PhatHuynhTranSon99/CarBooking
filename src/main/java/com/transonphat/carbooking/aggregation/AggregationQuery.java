package com.transonphat.carbooking.aggregation;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;

public interface AggregationQuery<T> {
    T execute(EntityManager entityManager, CriteriaBuilder criteriaBuilder);
}
