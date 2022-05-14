package com.transonphat.carbooking.dao;

import com.transonphat.carbooking.domain.Model;
import com.transonphat.carbooking.search.SearchCriterion;

/**
 * Author: Tran Son Phat
 * Generic search interface to check if an entity exist
 * @param <T>: Type of the model (Must extend Model base class)
 */
public interface ExistenceDAO<T extends Model> {
    boolean exists(SearchCriterion<T> criterion);
}
