package com.transonphat.carbooking.aggregation.customer;

import com.transonphat.carbooking.aggregation.AggregationExecutor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles(profiles = {"test"})
public class RevenueByCustomerTest {
    @Autowired
    private AggregationExecutor aggregationExecutor;

    @Test
    public void shouldReturnCorrectResultForCustomerOne() {
        //From 1/1 to 5/1
        Double revenueOne = aggregationExecutor.execute(
                new RevenueByCustomerQuery(
                        1,
                        ZonedDateTime.of(2020, 1, 1, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh")),
                        ZonedDateTime.of(2020, 1, 5, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh"))
                )
        );

        assertEquals(100.0, revenueOne);

        //From 3/1 to 20/1
        Double revenueTwo = aggregationExecutor.execute(
                new RevenueByCustomerQuery(
                        1,
                        ZonedDateTime.of(2020, 1, 3, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh")),
                        ZonedDateTime.of(2020, 1, 20, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh"))
                )
        );

        assertEquals(0.0, revenueTwo);
    }

    @Test
    public void shouldReturnCorrectResultForCustomerTwo() {
        //From 1/1 to 5/1
        Double revenueOne = aggregationExecutor.execute(
                new RevenueByCustomerQuery(
                        2,
                        ZonedDateTime.of(2020, 1, 1, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh")),
                        ZonedDateTime.of(2020, 1, 5, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh"))
                )
        );
        assertEquals(100.0, revenueOne);

        //From 1/1 to 1/3
        Double revenueTwo = aggregationExecutor.execute(
                new RevenueByCustomerQuery(
                        2,
                        ZonedDateTime.of(2020, 1, 1, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh")),
                        ZonedDateTime.of(2020, 3, 1, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh"))
                )
        );
        assertEquals(250.0, revenueTwo);

        //From 3/1 to 21/1
        Double revenueThree = aggregationExecutor.execute(
                new RevenueByCustomerQuery(
                        2,
                        ZonedDateTime.of(2020, 1, 3, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh")),
                        ZonedDateTime.of(2020, 1, 21, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh"))
                )
        );
        assertEquals(0.0, revenueThree);
    }

    @Test
    public void shouldReturnCorrectResultForCustomerThree() {
        //From 1/1 to 5/1
        Double revenueOne = aggregationExecutor.execute(
                new RevenueByCustomerQuery(
                        3,
                        ZonedDateTime.of(2020, 1, 1, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh")),
                        ZonedDateTime.of(2020, 1, 5, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh"))
                )
        );
        assertEquals(300.0, revenueOne);

        //From 3/1 to 5/1
        Double revenueTwo = aggregationExecutor.execute(
                new RevenueByCustomerQuery(
                        3,
                        ZonedDateTime.of(2020, 1, 3, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh")),
                        ZonedDateTime.of(2020, 1, 5, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh"))
                )
        );
        assertEquals(0.0, revenueTwo);

        //From 20/1 to 1/3
        Double revenueThree = aggregationExecutor.execute(
                new RevenueByCustomerQuery(
                        3,
                        ZonedDateTime.of(2020, 1, 20, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh")),
                        ZonedDateTime.of(2020, 3, 1, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh"))
                )
        );
        assertEquals(0.0, revenueThree);
    }
}
