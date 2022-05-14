package com.transonphat.carbooking.controllers;

import com.transonphat.carbooking.domain.Revenue;
import com.transonphat.carbooking.domain.Usage;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.services.StatisticService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

/**
 * Author: Tran Son Phat
 * StatisticController contains all endpoints to get relevant statistics
 */
@RestController
public class StatisticController {
    private final StatisticService statisticService;

    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping("/statistics/revenue/customer")
    public Revenue getRevenueByCustomer(@RequestParam(value = "from")
                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime from,
                                       @RequestParam(value = "to")
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime to,
                                       @RequestParam(value = "customer") Long customerId) {
        return statisticService.getRevenueByCustomer(from, to, customerId);
    }

    @GetMapping("/statistics/revenue/driver")
    public Revenue getRevenueByDriver(@RequestParam(value = "from")
                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime from,
                                      @RequestParam(value = "to")
                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime to,
                                      @RequestParam(value = "driver") Long driverId) {
        return statisticService.getRevenueByDriver(from, to, driverId);
    }

    @GetMapping("/statistics/usage")
    public PaginationResult<Usage> getCarUsage(@RequestParam Integer month,
                                               @RequestParam Integer year,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "3") int size) {
        //Get the usage
        return statisticService.getCarUsage(month, year, page, size);
    }
}
