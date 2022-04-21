package com.transonphat.carbooking.dao;

import com.transonphat.carbooking.domain.Model;
import com.transonphat.carbooking.pagination.PaginationResult;

public interface DAO<T extends Model> {
    T add(T customer);
    T delete(long id);
    PaginationResult<T> getAll(int currentPage, int pageSize);
    T getOne(long id);
}
