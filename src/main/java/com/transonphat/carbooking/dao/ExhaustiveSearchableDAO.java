package com.transonphat.carbooking.dao;

import com.transonphat.carbooking.domain.Model;
import com.transonphat.carbooking.search.SearchCriterion;

public interface ExhaustiveSearchableDAO<T extends Model> {
    Iterable<T> search(SearchCriterion<T> criterion);
}
