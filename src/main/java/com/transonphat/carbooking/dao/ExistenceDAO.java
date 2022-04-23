package com.transonphat.carbooking.dao;

import com.transonphat.carbooking.domain.Model;
import com.transonphat.carbooking.search.SearchCriterion;

public interface ExistenceDAO<T extends Model> {
    boolean exists(SearchCriterion<T> criterion);
}
