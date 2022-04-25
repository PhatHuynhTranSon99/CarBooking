package com.transonphat.carbooking.search;

import com.transonphat.carbooking.domain.Model;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;

public class SearchCriteria {
    public static <T extends Model> SearchCriterion<T> and(List<SearchCriterion<T>> criteria) {
        return (root, query, criteriaBuilder) -> {
            Predicate[] predicates = criteria.stream().map(item -> item.toPredicate(root, query, criteriaBuilder))
                                                            .toArray(Predicate[]::new);
            return criteriaBuilder.and(predicates);
        };
    }
}
