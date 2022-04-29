package com.transonphat.carbooking.dao.car;

import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.search.SearchCriteria;
import com.transonphat.carbooking.search.car.CarColorCriterion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

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

        assertEquals(3, carPaginationResult.getTotalItems());
    }

    @Test
    public void searchColor() {
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
}
