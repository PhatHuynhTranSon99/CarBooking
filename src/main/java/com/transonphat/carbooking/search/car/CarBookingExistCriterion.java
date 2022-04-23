package com.transonphat.carbooking.search.car;

import com.transonphat.carbooking.domain.*;
import com.transonphat.carbooking.search.SearchCriterion;

import javax.persistence.criteria.*;
import java.time.ZonedDateTime;

public class CarBookingExistCriterion implements SearchCriterion<Car> {
    private final Long carId;
    private final ZonedDateTime startTime;
    private final ZonedDateTime endTime;

    public CarBookingExistCriterion(Long carId, ZonedDateTime startTime, ZonedDateTime endTime) {
        this.carId = carId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Subquery<Long> carIdQuery = query.subquery(Long.class);
        Root<Booking> bookingRoot = carIdQuery.from(Booking.class);

        //Query booking
        Predicate predicate = criteriaBuilder.and(
                criteriaBuilder.lessThan(bookingRoot.get(Booking_.startTime), endTime),
                criteriaBuilder.greaterThan(bookingRoot.get(Booking_.endTime), startTime),
                criteriaBuilder.equal(bookingRoot.get(Booking_.invoice).get(Invoice_.driver).get(Car_.id), carId)
        );
        carIdQuery
                .select(
                        bookingRoot.get(Booking_.invoice)
                                .get(Invoice_.driver)
                                .get(Driver_.car)
                                .get(Car_.id)
                )
                .where(predicate);

        return root.get(Car_.id).in(carIdQuery);
    }
}
