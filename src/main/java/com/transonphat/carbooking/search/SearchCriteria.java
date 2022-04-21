package com.transonphat.carbooking.search;

import com.transonphat.carbooking.domain.Model;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;

public class SearchCriteria {
    public static <T extends Model> SearchCriterion<T> and(List<SearchCriterion<T>> criteria) {
        return new SearchCriterion<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaBuilder criteriaBuilder) {
                Predicate[] predicates = criteria.stream().map(item -> item.toPredicate(root, criteriaBuilder))
                                                                .toArray(Predicate[]::new);
                return criteriaBuilder.and(predicates);
            }
        };
    }
}
