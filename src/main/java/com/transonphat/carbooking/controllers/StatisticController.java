package com.transonphat.carbooking.controllers;

import com.transonphat.carbooking.aggregation.AggregationExecutor;
import com.transonphat.carbooking.aggregation.car.PaginatedCarUsageQuery;
import com.transonphat.carbooking.aggregation.customer.RevenueByCustomerQuery;
import com.transonphat.carbooking.aggregation.driver.RevenueByDriverQuery;
import com.transonphat.carbooking.dao.aggregation.Revenue;
import com.transonphat.carbooking.dao.aggregation.Usage;
import com.transonphat.carbooking.pagination.PaginationResult;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@RestController
public class StatisticController {
    private final AggregationExecutor aggregationExecutor;

    public StatisticController(AggregationExecutor aggregationExecutor) {
        this.aggregationExecutor = aggregationExecutor;
    }

    @GetMapping("/statistics/revenue/customer")
    public Revenue getRevenueByCustomer(@RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime from,
                                       @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime to,
                                       @RequestParam(value = "customer", required = false) Long customerId) {
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

    @GetMapping("statistics/revenue/driver")
    public Revenue getRevenueByDriver(@RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime from,
                                      @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime to,
                                      @RequestParam(value = "driver", required = false) Long driverId) {
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

    @GetMapping("statistics/usage")
    public PaginationResult<Usage> getCarUsage(@RequestParam Integer month,
                                               @RequestParam Integer year,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "2") int size) {
        //Get the usage
        return aggregationExecutor.execute(
                new PaginatedCarUsageQuery(month, year, page, size)
        );
    }
}
