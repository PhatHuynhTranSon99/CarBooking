package com.transonphat.carbooking.services;

import com.transonphat.carbooking.aggregation.AggregationExecutor;
import com.transonphat.carbooking.domain.Revenue;
import com.transonphat.carbooking.exceptions.types.InvalidTimePeriodException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Author: Tran Son Phat
 * Unit tests for statistics service
 */
@SpringBootTest(classes = {StatisticService.class})
@ActiveProfiles(profiles = {"test"})
public class StatisticServiceTest {
    @Autowired
    private StatisticService statisticService;

    @MockBean
    private AggregationExecutor aggregationExecutor;

    @Test
    public void getRevenueByDriverShouldCallAggregator() {
        when(aggregationExecutor.execute(any())).thenReturn(200.0);

        Revenue revenue = statisticService.getRevenueByDriver(
                ZonedDateTime.now(),
                ZonedDateTime.now(),
                1L
        );

        assertEquals(new Revenue(200.0), revenue);
    }

    @Test
    public void getRevenueByDriverThrowExceptionWhenInvalidDate() {
        //Start time after end time
        ZonedDateTime startTime = ZonedDateTime.of(2020, 1, 1, 0, 0, 0,
                0, ZoneId.of("Asia/Ho_Chi_Minh"));
        ZonedDateTime endTime = ZonedDateTime.of(2019, 1, 1, 0, 0, 0,
                0, ZoneId.of("Asia/Ho_Chi_Minh"));

        //Assert throw
        InvalidTimePeriodException invalidTimePeriodException = assertThrows(
                InvalidTimePeriodException.class,
                () -> {
                    statisticService.getRevenueByDriver(startTime, endTime, 1L);
                }
        );
        assertEquals("FromDate must come before ToDate", invalidTimePeriodException.getMessage());
    }

    @Test
    public void getRevenueByCustomerShouldCallAggregator() {
        when(aggregationExecutor.execute(any())).thenReturn(300.0);

        Revenue revenue = statisticService.getRevenueByCustomer(
                ZonedDateTime.now(),
                ZonedDateTime.now(),
                1L
        );

        assertEquals(new Revenue(300.0), revenue);
    }

    @Test
    public void getRevenueByCustomerShouldThrowExceptionWhenInvalidTime() {
        //Start time after end time
        ZonedDateTime startTime = ZonedDateTime.of(2020, 1, 1, 0, 0, 0,
                0, ZoneId.of("Asia/Ho_Chi_Minh"));
        ZonedDateTime endTime = ZonedDateTime.of(2019, 1, 1, 0, 0, 0,
                0, ZoneId.of("Asia/Ho_Chi_Minh"));

        //Assert throw
        InvalidTimePeriodException invalidTimePeriodException = assertThrows(
                InvalidTimePeriodException.class,
                () -> {
                    statisticService.getRevenueByCustomer(startTime, endTime, 1L);
                }
        );
        assertEquals("FromDate must come before ToDate", invalidTimePeriodException.getMessage());
    }

    @Test
    public void getCarUsageShouldCallAggregator() {
        //Call method
        int month = 1;
        int year = 2020;
        int page = 0;
        int size = 3;
        statisticService.getCarUsage(month, year, page, size);

        //Assert method call
        verify(aggregationExecutor).execute(any());
    }

    @Test
    public void getCarUsageShouldThrowExceptionWhenInvalidTime() {
        //Call method
        InvalidTimePeriodException exceptionOne = assertThrows(
                InvalidTimePeriodException.class,
                () -> {
                    statisticService.getCarUsage(1, -1, 0, 0);
                }
        );
        assertEquals("Year must be greater than zero", exceptionOne.getMessage());

        InvalidTimePeriodException exceptionTwo = assertThrows(
                InvalidTimePeriodException.class,
                () -> {
                    statisticService.getCarUsage(0, 2020, 0, 0);
                }
        );
        assertEquals("Month must be in the range of 1-12", exceptionTwo.getMessage());

        InvalidTimePeriodException exceptionThree = assertThrows(
                InvalidTimePeriodException.class,
                () -> {
                    statisticService.getCarUsage(-1, 2020, 0, 0);
                }
        );
        assertEquals("Month must be in the range of 1-12", exceptionThree.getMessage());

    }
}
