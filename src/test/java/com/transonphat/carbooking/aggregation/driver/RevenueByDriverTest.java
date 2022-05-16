package com.transonphat.carbooking.aggregation.driver;

import com.transonphat.carbooking.aggregation.AggregationExecutor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Author: Tran Son Phat
 * Unit tests for revenue by driver query
 */
@SpringBootTest
@ActiveProfiles(profiles = {"test"})
public class RevenueByDriverTest {
    @Autowired
    private AggregationExecutor aggregationExecutor;

    @Test
    public void shouldReturnCorrectResultForDriverOne() {
        Double revenueOne = aggregationExecutor.execute(
                new RevenueByDriverQuery(
                        1,
                        ZonedDateTime.of(2020, 1, 1, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh")),
                        ZonedDateTime.of(2020, 1, 5, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh"))
                )
        );
        assertEquals(100.0, revenueOne);

        Double revenueTwo = aggregationExecutor.execute(
                new RevenueByDriverQuery(
                        1,
                        ZonedDateTime.of(2020, 1, 1, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh")),
                        ZonedDateTime.of(2020, 3, 1, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh"))
                )
        );
        assertEquals(100.0, revenueTwo);

        Double revenueThree = aggregationExecutor.execute(
                new RevenueByDriverQuery(
                        1,
                        ZonedDateTime.of(2020, 1, 19, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh")),
                        ZonedDateTime.of(2020, 3, 5, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh"))
                )
        );
        assertEquals(0.0, revenueThree);

        Double revenueFour = aggregationExecutor.execute(
                new RevenueByDriverQuery(
                        1,
                        ZonedDateTime.of(2020, 5, 1, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh")),
                        ZonedDateTime.of(2020, 6, 1, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh"))
                )
        );
        assertEquals(0.0, revenueFour);
    }

    @Test
    public void shouldReturnCorrectResultForDriverTwo() {
        Double revenueOne = aggregationExecutor.execute(
                new RevenueByDriverQuery(
                        2,
                        ZonedDateTime.of(2020, 1, 1, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh")),
                        ZonedDateTime.of(2020, 1, 5, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh"))
                )
        );
        assertEquals(100.0, revenueOne);

        Double revenueTwo = aggregationExecutor.execute(
                new RevenueByDriverQuery(
                        2,
                        ZonedDateTime.of(2020, 1, 20, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh")),
                        ZonedDateTime.of(2020, 3, 1, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh"))
                )
        );
        assertEquals(0.0, revenueTwo);

        Double revenueThree = aggregationExecutor.execute(
                new RevenueByDriverQuery(
                        2,
                        ZonedDateTime.of(2020, 1, 3, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh")),
                        ZonedDateTime.of(2020, 1, 5, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh"))
                )
        );
        assertEquals(0.0, revenueThree);
    }

    @Test
    public void shouldReturnCorrectResultForDriverThree() {
        Double revenueOne = aggregationExecutor.execute(
                new RevenueByDriverQuery(
                        3,
                        ZonedDateTime.of(2020, 1, 1, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh")),
                        ZonedDateTime.of(2020, 1, 5, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh"))
                )
        );
        assertEquals(300.0, revenueOne);

        Double revenueTwo = aggregationExecutor.execute(
                new RevenueByDriverQuery(
                        3,
                        ZonedDateTime.of(2020, 1, 20, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh")),
                        ZonedDateTime.of(2020, 3, 1, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh"))
                )
        );
        assertEquals(0.0, revenueTwo);

        Double revenueThree = aggregationExecutor.execute(
                new RevenueByDriverQuery(
                        3,
                        ZonedDateTime.of(2020, 1, 3, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh")),
                        ZonedDateTime.of(2020, 1, 5, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh"))
                )
        );
        assertEquals(0.0, revenueThree);
    }
}
