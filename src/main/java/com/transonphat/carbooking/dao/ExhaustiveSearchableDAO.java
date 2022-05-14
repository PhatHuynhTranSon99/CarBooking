package com.transonphat.carbooking.dao;

import com.transonphat.carbooking.domain.Model;
import com.transonphat.carbooking.search.SearchCriterion;

/**
 * Author: Tran Son Phat
 * Generic search interface to get all entities (Iterable)
 * @param <T>: Type of the model (Must extend Model base class)
 */
public interface ExhaustiveSearchableDAO<T extends Model> {
    Iterable<T> search(SearchCriterion<T> criterion);
}
