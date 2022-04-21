package com.transonphat.carbooking.dao;

import com.transonphat.carbooking.domain.Model;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.search.SearchCriterion;

public interface SearchableDAO<T extends Model> extends DAO<T> {
    PaginationResult<T> search(SearchCriterion<T> criteria, int page, int pageSize);
}
