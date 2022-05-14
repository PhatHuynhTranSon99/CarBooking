package com.transonphat.carbooking.aggregation;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Author: Tran Son Phat
 * Execute the MySQL query and get the result using an EntityManager
 */
@Component
@Transactional
public class AggregationExecutor {
    @PersistenceContext
    private EntityManager entityManager;

    public <T> T execute(AggregationQuery<T> query) {
        return query.execute(entityManager, entityManager.getCriteriaBuilder());
    }
}
