package com.transonphat.carbooking.aggregation.driver;

import com.transonphat.carbooking.aggregation.AggregationQuery;
import com.transonphat.carbooking.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.ZonedDateTime;

public class RevenueByDriverQuery implements AggregationQuery<Double> {
    private final long driverId;
    private final ZonedDateTime startTime;
    private final ZonedDateTime endTime;

    public RevenueByDriverQuery(long driverId, ZonedDateTime startTime, ZonedDateTime endTime) {
        this.driverId = driverId;
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
                                .get(Invoice_.driver)
                                .get(Driver_.id),
                        driverId
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
