package com.transonphat.carbooking.dao.car;

import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.search.SearchCriteria;
import com.transonphat.carbooking.search.car.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@ActiveProfiles(profiles = { "test" })
public class CarSearchableDAOTest {
    @Autowired
    private SearchableDAO<Car> carSearchableDAO;

    @Test
    public void searchNoCriterion() {
        //Should return all cars with no criterion
        PaginationResult<Car> carPaginationResult = carSearchableDAO.search(
                SearchCriteria.and(new ArrayList<>()), //No criterion -> Empty list
                1,
                10
        );

        assertEquals(4, carPaginationResult.getTotalItems());
    }

    @Test
    public void searchColorGiveCorrectResult() {
        //Should return matching car with color
        PaginationResult<Car> greenCarsPagination = carSearchableDAO.search(
                new CarColorCriterion("green"),
                0,
                10
        );
        //Should return matching car with color
        PaginationResult<Car> blueCarsPagination = carSearchableDAO.search(
                new CarColorCriterion("blue"),
                0,
                10
        );
        //Should return matching car with color
        PaginationResult<Car> yellowCarsPagination = carSearchableDAO.search(
                new CarColorCriterion("yellow"),
                0,
                10
        );
        //Should return matching car with color
        PaginationResult<Car> redCarsPagination = carSearchableDAO.search(
                new CarColorCriterion("red"),
                0,
                10
        );
        //Should return matching car with color
        PaginationResult<Car> greyCarsPagination = carSearchableDAO.search(
                new CarColorCriterion("grey"),
                0,
                10
        );

        //Check the size
        assertEquals(1, greenCarsPagination.getTotalItems());
        assertEquals(1, blueCarsPagination.getTotalItems());
        assertEquals(1, yellowCarsPagination.getTotalItems());
        assertEquals(0, redCarsPagination.getTotalItems());
        assertEquals(0, greyCarsPagination.getTotalItems());

        //Check the item
        assertEquals(1L, greenCarsPagination.getItems().iterator().next().getId());
        assertEquals(2L, blueCarsPagination.getItems().iterator().next().getId());
        assertEquals(3L, yellowCarsPagination.getItems().iterator().next().getId());
    }

    @Test
    public void searchMakeGiveCorrectResult() {
        //Get the result
        PaginationResult<Car> toyoSearchResult = carSearchableDAO.search(
                new CarMakeCriterion("toyo"),
                0,
                10
        );

        //Confirm number of match
        assertEquals(1, toyoSearchResult.getTotalItems());

        //Confirm matching item
        assertEquals(1, toyoSearchResult.getItems().iterator().next().getId());

        PaginationResult<Car> toyotaSearchResult = carSearchableDAO.search(
                new CarMakeCriterion("toyota"),
                0,
                10
        );

        //Confirm number of match
        assertEquals(1, toyotaSearchResult.getTotalItems());

        //Confirm matching item
        assertEquals(1, toyotaSearchResult.getItems().iterator().next().getId());

        //Confirm matching item
        assertEquals(1, toyoSearchResult.getItems().iterator().next().getId());

        PaginationResult<Car> boldSearchResult = carSearchableDAO.search(
                new CarMakeCriterion("TOYOTA"),
                0,
                10
        );

        //Confirm number of match
        assertEquals(1, boldSearchResult.getTotalItems());

        //Confirm matching item
        assertEquals(1, boldSearchResult.getItems().iterator().next().getId());

        PaginationResult<Car> emptySearchResult = carSearchableDAO.search(
                new CarMakeCriterion("YOYO"),
                0,
                10
        );

        //Confirm number of match
        assertEquals(0, emptySearchResult.getTotalItems());
    }

    @Test
    public void searchModelReturnCorrectResult() {
        PaginationResult<Car> csSearchResult = carSearchableDAO.search(
                new CarModelCriterion("cs"),
                0,
                10
        );
        assertEquals(1, csSearchResult.getTotalItems());
        assertEquals(2, csSearchResult.getItems().iterator().next().getId());

        PaginationResult<Car> csvSearchResult = carSearchableDAO.search(
                new CarModelCriterion("csv"),
                0,
                10
        );
        assertEquals(1, csvSearchResult.getTotalItems());
        assertEquals(2, csvSearchResult.getItems().iterator().next().getId());

        PaginationResult<Car> boldSearchResult = carSearchableDAO.search(
                new CarModelCriterion("CSV"),
                0,
                10
        );
        assertEquals(1, boldSearchResult.getTotalItems());
        assertEquals(2, boldSearchResult.getItems().iterator().next().getId());

        PaginationResult<Car> emptySearchResult = carSearchableDAO.search(
                new CarModelCriterion("CSVVVVV"),
                0,
                10
        );
        assertEquals(0, emptySearchResult.getTotalItems());
    }

    @Test
    public void searchConvertibleReturnCorrectResult() {
        PaginationResult<Car> convertibleSearchResult = carSearchableDAO.search(
                new CarConvertibleCriterion(true),
                0,
                10
        );
        assertEquals(1, convertibleSearchResult.getTotalItems());
        assertEquals(1, convertibleSearchResult.getItems().iterator().next().getId());

        PaginationResult<Car> nonConvertibleSearchResult = carSearchableDAO.search(
                new CarConvertibleCriterion(false),
                0,
                10
        );
        assertEquals(3, nonConvertibleSearchResult.getTotalItems());
        assertThat(
                nonConvertibleSearchResult.getItems(),
                contains(
                        hasProperty("id", is(2L)),
                        hasProperty("id", is(3L)),
                        hasProperty("id", is(4L))
                )
        );
    }

    @Test
    public void searchIdentificationReturnCorrectResult() {
        //Exact
        PaginationResult<Car> exactCarResult = carSearchableDAO.search(
                new CarIdentificationCriterion("0180-989"),
                0,
                10
        );
        assertEquals(1L, exactCarResult.getTotalItems());
        assertEquals(1L, exactCarResult.getItems().iterator().next().getId());

        //Partial result
        PaginationResult<Car> partialCarResult = carSearchableDAO.search(
                new CarIdentificationCriterion("0111"),
                0,
                10
        );
        assertEquals(1, partialCarResult.getTotalItems());
        assertEquals(2L, partialCarResult.getItems().iterator().next().getId());

        //All three results
        PaginationResult<Car> allCarResult = carSearchableDAO.search(
                new CarIdentificationCriterion("0"),
                0,
                10
        );
        assertEquals(4, allCarResult.getTotalItems());
        assertThat(
                allCarResult.getItems(),
                contains(
                        hasProperty("id", is(1L)),
                        hasProperty("id", is(2L)),
                        hasProperty("id", is(3L)),
                        hasProperty("id", is(4L))
                )
        );

        //Empty result
        PaginationResult<Car> emptyCarResult = carSearchableDAO.search(
                new CarIdentificationCriterion("191919191919"),
                0,
                10
        );
        assertEquals(0, emptyCarResult.getTotalItems());
    }

    @Test
    public void searchCarFreeReturnCorrectResult() {
        //Empty result
        PaginationResult<Car> emptyCarResult = carSearchableDAO.search(
                new CarFreeCriterion(
                        ZonedDateTime.of(2020, 1, 1, 0, 0, 0, 0, ZoneId.of("Asia/Ho_Chi_Minh")),
                        ZonedDateTime.of(2020, 1, 5, 0, 0, 0, 0, ZoneId.of("Asia/Ho_Chi_Minh"))
                ),
                0,
                10
        );
        assertEquals(1, emptyCarResult.getTotalItems());
        assertThat(
                emptyCarResult.getItems(),
                contains(
                        hasProperty("id", is(4L))
                )
        );

        //Two result
        PaginationResult<Car> twoCarResult = carSearchableDAO.search(
                new CarFreeCriterion(
                        ZonedDateTime.of(2020, 1, 20, 0, 0, 0, 0, ZoneId.of("Asia/Ho_Chi_Minh")),
                        ZonedDateTime.of(2020, 1, 27, 0, 0, 0, 0, ZoneId.of("Asia/Ho_Chi_Minh"))
                ),
                0,
                10
        );
        assertEquals(3, twoCarResult.getTotalItems());
        assertThat(
                twoCarResult.getItems(),
                contains(
                        hasProperty("id", is(2L)),
                        hasProperty("id", is(3L)),
                        hasProperty("id", is(4L))
                )
        );
    }

    @Test
    public void searchByBookingDate() {
        //TODO: Search by booking date
    }
}
