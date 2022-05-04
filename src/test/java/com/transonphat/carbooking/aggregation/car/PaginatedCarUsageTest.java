package com.transonphat.carbooking.aggregation.car;

import com.transonphat.carbooking.aggregation.AggregationExecutor;
import com.transonphat.carbooking.domain.Usage;
import com.transonphat.carbooking.pagination.PaginationResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

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
        PaginationResult<Usage> carUsageResult = aggregationExecutor.execute(
                new PaginatedCarUsageQuery(2, 2020, 0, 10)
        );

        //Check the date
        Iterable<Usage> usages = carUsageResult.getItems();

        //Check contains
        assertThat(usages)
                .extracting(Usage::getCarId, Usage::getDays)
                .containsExactlyInAnyOrder(
                        tuple(1L, 28L),
                        tuple(2L, 0L),
                        tuple(3L, 0L)
                );
    }

    @Test
    public void shouldReturnCorrectResultForMarch() {
        PaginationResult<Usage> carUsageResult = aggregationExecutor.execute(
                new PaginatedCarUsageQuery(3, 2020, 0, 10)
        );

        //Check the date
        Iterable<Usage> usages = carUsageResult.getItems();

        //Check contains
        assertThat(usages)
                .extracting(Usage::getCarId, Usage::getDays)
                .containsExactlyInAnyOrder(
                        tuple(1L, 9L),
                        tuple(2L, 0L),
                        tuple(3L, 0L)
                );
    }
}
