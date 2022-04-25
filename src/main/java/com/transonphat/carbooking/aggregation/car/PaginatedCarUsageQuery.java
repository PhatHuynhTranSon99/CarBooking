package com.transonphat.carbooking.aggregation.car;

import com.transonphat.carbooking.aggregation.AggregationQuery;
import com.transonphat.carbooking.dao.aggregation.Usage;
import com.transonphat.carbooking.domain.*;
import com.transonphat.carbooking.pagination.PaginationResult;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class PaginatedCarUsageQuery implements AggregationQuery<PaginationResult<Usage>> {
    private final int month;
    private final int year;
    private final int currentPage;
    private final int pageSize;

    public PaginatedCarUsageQuery(int month, int year, int currentPage, int pageSize) {
        this.month = month;
        this.year = year;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    private Long getTotalCars(EntityManager entityManager, CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<Long> carCount = criteriaBuilder.createQuery(Long.class);
        Root<Booking> bookingRoot = carCount.from(Booking.class);

        carCount.select(
                criteriaBuilder.countDistinct(
                        bookingRoot.get(Booking_.invoice)
                            .get(Invoice_.driver)
                            .get(Driver_.car)
                            .get(Car_.id)
                )
        );

        return entityManager.createQuery(carCount).getSingleResult();
    }

    @Override
    public PaginationResult<Usage> execute(EntityManager entityManager, CriteriaBuilder criteriaBuilder) {
        //Create booking root
        CriteriaQuery<Tuple> dateSumQuery = criteriaBuilder.createQuery(Tuple.class);
        Root<Booking> bookingRoot = dateSumQuery.from(Booking.class);

        //Start date is in month
        Predicate startDateWithinRange = criteriaBuilder.and(
                criteriaBuilder.equal(
                        criteriaBuilder.function(
                                "MONTH",
                                Integer.class,
                                bookingRoot.get(Booking_.startTime)
                        ),
                        month
                ),
                criteriaBuilder.equal(
                        criteriaBuilder.function(
                                "YEAR",
                                Integer.class,
                                bookingRoot.get(Booking_.startTime)
                        ),
                        year
                )
        );

        //End date is in month
        Predicate endDateWithinRange = criteriaBuilder.and(
                criteriaBuilder.equal(
                        criteriaBuilder.function(
                                "MONTH",
                                Integer.class,
                                bookingRoot.get(Booking_.endTime)
                        ),
                        month
                ),
                criteriaBuilder.equal(
                        criteriaBuilder.function(
                                "YEAR",
                                Integer.class,
                                bookingRoot.get(Booking_.endTime)
                        ),
                        year
                )
        );

        //Start and end date contain month
        Predicate containMonth = criteriaBuilder.and(
                criteriaBuilder.lessThanOrEqualTo(
                       criteriaBuilder.function(
                               "MONTH",
                               Integer.class,
                               bookingRoot.get(Booking_.startTime)
                       ),
                       month
                ),
                criteriaBuilder.lessThanOrEqualTo(
                        criteriaBuilder.function(
                                "YEAR",
                                Integer.class,
                                bookingRoot.get(Booking_.startTime)
                        ),
                        year
                ),
                criteriaBuilder.greaterThanOrEqualTo(
                        criteriaBuilder.function(
                                "MONTH",
                                Integer.class,
                                bookingRoot.get(Booking_.endTime)
                        ),
                        month
                ),
                criteriaBuilder.greaterThanOrEqualTo(
                        criteriaBuilder.function(
                                "YEAR",
                                Integer.class,
                                bookingRoot.get(Booking_.endTime)
                        ),
                        year
                )
        );

        //Match the date
        Predicate matchDate = criteriaBuilder.or(
                criteriaBuilder.and(
                        startDateWithinRange,
                        endDateWithinRange
                ),
                containMonth
        );

        //Create sum expression
        Expression<Integer> dateSum = criteriaBuilder.sum(
                criteriaBuilder.function(
                        "DATEDIFF",
                        Integer.class,
                        criteriaBuilder.function(
                                "LEAST",
                                ZonedDateTime.class,
                                bookingRoot.get(Booking_.endTime),
                                criteriaBuilder.function(
                                        "LAST_DAY",
                                        ZonedDateTime.class,
                                        criteriaBuilder.function(
                                                "STR_TO_DATE",
                                                ZonedDateTime.class,
                                                criteriaBuilder.literal("01/" + month + "/" + year),
                                                criteriaBuilder.literal("%d/%m/%y")
                                        )
                                )
                        ),
                        criteriaBuilder.function(
                                "GREATEST",
                                ZonedDateTime.class,
                                bookingRoot.get(Booking_.startTime),
                                criteriaBuilder.function(
                                        "STR_TO_DATE",
                                        ZonedDateTime.class,
                                        criteriaBuilder.literal("01/" + month + "/" + year),
                                        criteriaBuilder.literal("%d/%m/%y")
                                )
                        )
                )
        );

        //Calculate
        dateSumQuery.multiselect(bookingRoot.get(Booking_.invoice).get(Invoice_.driver).get(Driver_.id), dateSum)
                .where(matchDate)
                .groupBy(bookingRoot.get(Booking_.invoice).get(Invoice_.driver).get(Driver_.id));

        //Map result
        TypedQuery<Tuple> typedQuery = entityManager.createQuery(dateSumQuery);

        //Set first result and max result for pagination
        typedQuery.setFirstResult(currentPage * pageSize);
        typedQuery.setMaxResults(pageSize);

        //Get total number of records and total pages
        int totalNumber = getTotalCars(entityManager, criteriaBuilder).intValue();
        int totalPages = (int) Math.round(Math.ceil(((double) totalNumber) / pageSize));

        //Get usage list
        List<Usage> usageList =  typedQuery
                .getResultList()
                .stream()
                .map(
                    tuple -> new Usage((Long) tuple.get(0), (Long) tuple.get(1))
                )
                .collect(Collectors.toList());

        return new PaginationResult<>(
                totalNumber,
                usageList,
                currentPage,
                totalPages
        );
    }
}
