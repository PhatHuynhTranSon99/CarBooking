package com.transonphat.carbooking.services;

import com.transonphat.carbooking.aggregation.AggregationExecutor;
import com.transonphat.carbooking.aggregation.car.PaginatedCarUsageQuery;
import com.transonphat.carbooking.aggregation.customer.RevenueByCustomerQuery;
import com.transonphat.carbooking.aggregation.driver.RevenueByDriverQuery;
import com.transonphat.carbooking.dao.aggregation.Revenue;
import com.transonphat.carbooking.dao.aggregation.Usage;
import com.transonphat.carbooking.pagination.PaginationResult;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class StatisticService {
    private AggregationExecutor aggregationExecutor;

    public StatisticService(AggregationExecutor aggregationExecutor) {
        this.aggregationExecutor = aggregationExecutor;
    }

    public Revenue getRevenueByDriver(ZonedDateTime from, ZonedDateTime to, long driverId) {
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
        //Get the usage
        return aggregationExecutor.execute(
                new PaginatedCarUsageQuery(month, year, page, size)
        );
    }
}
