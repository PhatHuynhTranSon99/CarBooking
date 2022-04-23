package com.transonphat.carbooking.search.car;

import com.transonphat.carbooking.domain.*;
import com.transonphat.carbooking.search.SearchCriterion;

import javax.persistence.criteria.*;
import java.time.ZonedDateTime;

public class CarFreeCriterion implements SearchCriterion<Car> {
    private ZonedDateTime startTime, endTime;

    public CarFreeCriterion(ZonedDateTime startTime, ZonedDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {//Join booking
        Subquery<Long> carIdQuery = query.subquery(Long.class);
        Root<Booking> bookingRoot = carIdQuery.from(Booking.class);

        //Query booking
        Predicate predicate = criteriaBuilder.and(
                criteriaBuilder.lessThan(bookingRoot.get(Booking_.startTime), endTime),
                criteriaBuilder.greaterThan(bookingRoot.get(Booking_.endTime), startTime)
        );
        carIdQuery
                .select(
                    bookingRoot.get(Booking_.invoice)
                        .get(Invoice_.driver)
                        .get(Driver_.car)
                        .get(Car_.id)
                )
                .where(predicate);

        return root.get(Car_.id).in(carIdQuery).not();
    }
}
