package com.transonphat.carbooking.dao;

import com.transonphat.carbooking.domain.Model;
import com.transonphat.carbooking.pagination.PaginationResult;

/**
 * Author: Tran Son Phat
 * Generic interface for CRUD operation
 * @param <T>: Type of the model (Must extend Model base class)
 */
public interface CrudDAO<T extends Model> {
    T save(T entity);
    T delete(long id);
    PaginationResult<T> getAll(int currentPage, int pageSize);
    T getOne(long id);
}
