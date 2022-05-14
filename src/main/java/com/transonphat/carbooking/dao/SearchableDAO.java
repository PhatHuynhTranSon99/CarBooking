package com.transonphat.carbooking.dao;

import com.transonphat.carbooking.domain.Model;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.search.SearchCriterion;

/**
 * Author: Tran Son Phat
 * Generic search interface to get paginated entity result (Iterable)
 * @param <T>: Type of the model (Must extend Model base class)
 */
public interface SearchableDAO<T extends Model> {
    PaginationResult<T> search(SearchCriterion<T> criteria, int page, int pageSize);
}
