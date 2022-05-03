package com.transonphat.carbooking.aggregation.car;

import com.transonphat.carbooking.aggregation.AggregationExecutor;
import com.transonphat.carbooking.dao.aggregation.Usage;
import com.transonphat.carbooking.pagination.PaginationResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles(profiles = {"test"})
public class PaginatedCarUsageTest {
    @Autowired
    private AggregationExecutor aggregationExecutor;

    @Test
    public void shouldReturnCorrectResultForJanuary() {
        PaginationResult<Usage> carUsageResult = aggregationExecutor.execute(
                new PaginatedCarUsageQuery(1, 2020, 0, 10)
        );

        //Check the date
        Iterable<Usage> usages = carUsageResult.getItems();

        //Check contains
        assertThat(usages)
                .extracting(Usage::getCarId, Usage::getDays)
                .containsExactlyInAnyOrder(
                        tuple(1L, 15L),
                        tuple(2L, 4L),
                        tuple(3L, 4L)
                );
    }

    @Test
    public void shouldReturnCorrectResultForFebruary() {

    }

    @Test
    public void shouldReturnCorrectResultForMarch() {

    }
}
