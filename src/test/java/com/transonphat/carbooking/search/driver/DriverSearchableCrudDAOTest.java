package com.transonphat.carbooking.search.driver;

import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.domain.Driver;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.search.SearchCriteria;
import com.transonphat.carbooking.search.car.CarBookingExistCriterion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles(profiles = {"test"})
public class DriverSearchableCrudDAOTest {
    @Autowired
    private SearchableDAO<Driver> driverSearchableDAO;

    @Test
    public void searchWithEmpty() {
        PaginationResult<Driver> driverPaginationResult = driverSearchableDAO.search(
                SearchCriteria.and(new ArrayList<>()),
                0,
                10
        );

        assertEquals(4L, driverPaginationResult.getTotalItems());
        assertThat(
                driverPaginationResult.getItems(),
                contains(
                        hasProperty("id", is(1L)),
                        hasProperty("id", is(2L)),
                        hasProperty("id", is(3L)),
                        hasProperty("id", is(4L))
                )
        );
    }

    @Test
    public void searchWithFirstName() {
        //Full name
        PaginationResult<Driver> fullNameResult = driverSearchableDAO.search(
                new DriverNameCriterion("Adam"),
                0,
                10
        );
        assertEquals(1, fullNameResult.getTotalItems());
        assertThat(
                fullNameResult.getItems(),
                contains(
                        hasProperty("id", is(1L))
                )
        );

        //Partial
        PaginationResult<Driver> partialNameResult = driverSearchableDAO.search(
                new DriverNameCriterion("ark"),
                0,
                10
        );
        assertEquals(1, partialNameResult.getTotalItems());
        assertThat(
                partialNameResult.getItems(),
                contains(
                        hasProperty("id", is(2L))
                )
        );

        //Bold
        PaginationResult<Driver> boldNameResult = driverSearchableDAO.search(
                new DriverNameCriterion("JAY"),
                0,
                10
        );
        assertEquals(1, boldNameResult.getTotalItems());
        assertThat(
                boldNameResult.getItems(),
                contains(
                        hasProperty("id", is(3L))
                )
        );
    }

    @Test
    public void searchWithLastName() {
        //Full name
        PaginationResult<Driver> fullNameResult = driverSearchableDAO.search(
                new DriverNameCriterion("Levine"),
                0,
                10
        );
        assertEquals(1, fullNameResult.getTotalItems());
        assertThat(
                fullNameResult.getItems(),
                contains(
                        hasProperty("id", is(1L))
                )
        );

        //Partial
        PaginationResult<Driver> partialNameResult = driverSearchableDAO.search(
                new DriverNameCriterion("ubber"),
                0,
                10
        );
        assertEquals(1, partialNameResult.getTotalItems());
        assertThat(
                partialNameResult.getItems(),
                contains(
                        hasProperty("id", is(2L))
                )
        );

        //Bold
        PaginationResult<Driver> boldNameResult = driverSearchableDAO.search(
                new DriverNameCriterion("LANGDON"),
                0,
                10
        );
        assertEquals(1, boldNameResult.getTotalItems());
        assertThat(
                boldNameResult.getItems(),
                contains(
                        hasProperty("id", is(3L))
                )
        );
    }

    @Test
    public void searchWithPhone() {
        //Full number
        PaginationResult<Driver> fullNumberResult = driverSearchableDAO.search(
                new DriverPhoneCriterion("0909111909"),
                0,
                10
        );
        assertEquals(1, fullNumberResult.getTotalItems());
        assertThat(
                fullNumberResult.getItems(),
                contains(
                        hasProperty("id", is(1L))
                )
        );

        //Partial
        PaginationResult<Driver> partialNumberResult = driverSearchableDAO.search(
                new DriverPhoneCriterion("921"),
                0,
                10
        );
        assertEquals(1, partialNumberResult.getTotalItems());
        assertThat(
                partialNumberResult.getItems(),
                contains(
                        hasProperty("id", is(3L))
                )
        );
    }
}
