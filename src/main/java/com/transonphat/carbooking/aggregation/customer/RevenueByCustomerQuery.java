package com.transonphat.carbooking.aggregation.customer;

import com.transonphat.carbooking.aggregation.AggregationQuery;
import com.transonphat.carbooking.domain.Booking;
import com.transonphat.carbooking.domain.Booking_;
import com.transonphat.carbooking.domain.Customer_;
import com.transonphat.carbooking.domain.Invoice_;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.ZonedDateTime;

public class RevenueByCustomerQuery implements AggregationQuery<Double> {
    private final long customerId;
    private final ZonedDateTime startTime;
    private final ZonedDateTime endTime;

    public RevenueByCustomerQuery(long customerId, ZonedDateTime startTime, ZonedDateTime endTime) {
        this.customerId = customerId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public Double execute(EntityManager entityManager, CriteriaBuilder criteriaBuilder) {
        //Create booking root
        CriteriaQuery<Double> revenueSumQuery = criteriaBuilder.createQuery(Double.class);
        Root<Booking> bookingRoot = revenueSumQuery.from(Booking.class);

        //Create predicate
        Predicate datePredicate = criteriaBuilder.and(
                criteriaBuilder.equal(
                        bookingRoot
                                .get(Booking_.invoice)
                                .get(Invoice_.customer)
                                .get(Customer_.id),
                        customerId
                ),
                criteriaBuilder.greaterThanOrEqualTo(
                        bookingRoot
                                .get(Booking_.startTime),
                        startTime
                ),
                criteriaBuilder.lessThanOrEqualTo(
                        bookingRoot
                                .get(Booking_.endTime),
                        endTime
                )
        );

        //Create TypedQuery and get result
        revenueSumQuery.select(criteriaBuilder.sum(bookingRoot.get(Booking_.invoice).get(Invoice_.totalCharge)))
                .where(datePredicate);

        TypedQuery<Double> typedQuery = entityManager.createQuery(revenueSumQuery);
        return typedQuery.getSingleResult();
    }
}
