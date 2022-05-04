package com.transonphat.carbooking.services;

import com.transonphat.carbooking.aggregation.AggregationExecutor;
import com.transonphat.carbooking.aggregation.car.PaginatedCarUsageQuery;
import com.transonphat.carbooking.aggregation.customer.RevenueByCustomerQuery;
import com.transonphat.carbooking.aggregation.driver.RevenueByDriverQuery;
import com.transonphat.carbooking.domain.Revenue;
import com.transonphat.carbooking.domain.Usage;
import com.transonphat.carbooking.exceptions.types.InvalidTimePeriodException;
import com.transonphat.carbooking.pagination.PaginationResult;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class StatisticService {
    private final AggregationExecutor aggregationExecutor;

    public StatisticService(AggregationExecutor aggregationExecutor) {
        this.aggregationExecutor = aggregationExecutor;
    }

    public Revenue getRevenueByDriver(ZonedDateTime from, ZonedDateTime to, long driverId) {
        //Throw exception when from is after to
        if (from.isAfter(to))
            throw new InvalidTimePeriodException("FromDate must come before ToDate");

        //Get the revenue
        Double revenue = aggregationExecutor.execute(
                new RevenueByDriverQuery(
                        driverId,
                        from,
                        to
                )
        );

        return Revenue.from(revenue);
    }

    public Revenue getRevenueByCustomer(ZonedDateTime from, ZonedDateTime to, long customerId) {
        //Throw exception when from is before to
        if (from.isAfter(to))
            throw new InvalidTimePeriodException("FromDate must come before ToDate");

        //Get the revenue
        Double revenue = aggregationExecutor.execute(
                new RevenueByCustomerQuery(
                        customerId,
                        from,
                        to
                )
        );

        return Revenue.from(revenue);
    }

    public PaginationResult<Usage> getCarUsage(int month, int year, int page, int size) {
        //Throw exception when month or year is negative
        if (year <= 0)
            throw new InvalidTimePeriodException("Year must be greater than zero");

        //Throw exception when month is out of range
        if (month < 1 || month > 12)
            throw new InvalidTimePeriodException("Month must be in the range of 1-12");

        //Get the usage
        return aggregationExecutor.execute(
                new PaginatedCarUsageQuery(month, year, page, size)
        );
    }
}
