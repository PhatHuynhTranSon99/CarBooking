package com.transonphat.carbooking.search;

import com.transonphat.carbooking.domain.Model;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Author: Tran Son Phat
 * Specification class to wrap our criterion to Spring JPA Specification
 * (Like an adapter pattern)
 * @param <T>: The type of the entity (must extend the Model base class)
 */
public class SearchSpecification<T extends Model> implements Specification<T> {
    private final SearchCriterion<T> searchCriterion;

    public SearchSpecification(SearchCriterion<T> searchCriterion) {
        this.searchCriterion = searchCriterion;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return this.searchCriterion.toPredicate(root, query, criteriaBuilder);
    }
}
