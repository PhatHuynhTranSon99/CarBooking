package com.transonphat.carbooking.search.customer;

import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.domain.Customer;
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
@ActiveProfiles(profiles = { "test" })
public class CustomerSearchableDAOTest {
    @Autowired
    private SearchableDAO<Customer> customerSearchableDAO;

    @Test
    public void searchWithNoCriteriaShouldReturnCorrectResult() {
        PaginationResult<Customer> customerPaginationResult = customerSearchableDAO.search(
                SearchCriteria.and(new ArrayList<>()),
                0,
                10
        );

        assertEquals(3L, customerPaginationResult.getTotalItems());
        assertThat(
                customerPaginationResult.getItems(),
                contains(
                        hasProperty("id", is(1L)),
                        hasProperty("id", is(2L)),
                        hasProperty("id", is(3L))
                )
        );
    }

    @Test
    public void searchWithFirstNameShouldReturnCorrectResult() {
        PaginationResult<Customer> fullNameResult = customerSearchableDAO.search(
                new CustomerNameCriterion("Cole"),
                0,
                10
        );

        assertEquals(1L, fullNameResult.getTotalItems());
        assertThat(
                fullNameResult.getItems(),
                contains(
                        hasProperty("id", is(1L))
                )
        );

        PaginationResult<Customer> partialNameResult = customerSearchableDAO.search(
                new CustomerNameCriterion("man"),
                0,
                10
        );
        assertEquals(1L, partialNameResult.getTotalItems());
        assertThat(
                partialNameResult.getItems(),
                contains(
                        hasProperty("id", is(3L))
                )
        );

        PaginationResult<Customer> boldNameResult = customerSearchableDAO.search(
                new CustomerNameCriterion("O'REILY"),
                0,
                10
        );
        assertEquals(1L, boldNameResult.getTotalItems());
        assertThat(
                boldNameResult.getItems(),
                contains(
                        hasProperty("id", is(2L))
                )
        );
    }

    @Test
    public void searchWithLastNameShouldReturnCorrectResult() {
        PaginationResult<Customer> fullNameResult = customerSearchableDAO.search(
                new CustomerNameCriterion("Adam"),
                0,
                10
        );

        assertEquals(1L, fullNameResult.getTotalItems());
        assertThat(
                fullNameResult.getItems(),
                contains(
                        hasProperty("id", is(1L))
                )
        );

        PaginationResult<Customer> partialNameResult = customerSearchableDAO.search(
                new CustomerNameCriterion("Os"),
                0,
                10
        );
        assertEquals(1L, partialNameResult.getTotalItems());
        assertThat(
                partialNameResult.getItems(),
                contains(
                        hasProperty("id", is(3L))
                )
        );

        PaginationResult<Customer> boldNameResult = customerSearchableDAO.search(
                new CustomerNameCriterion("KYLE"),
                0,
                10
        );
        assertEquals(1L, boldNameResult.getTotalItems());
        assertThat(
                boldNameResult.getItems(),
                contains(
                        hasProperty("id", is(2L))
                )
        );
    }

    @Test
    public void searchAddressShouldReturnCorrectResult() {
        //Full result
        PaginationResult<Customer> fullAddressResult = customerSearchableDAO.search(
                new CustomerAddressCriterion("11 Street"),
                0,
                10
        );
        assertEquals(1, fullAddressResult.getTotalItems());
        assertThat(
                fullAddressResult.getItems(),
                contains(
                        hasProperty("id", is(1L))
                )
        );

        //Partial result
        PaginationResult<Customer> partialAddressResult = customerSearchableDAO.search(
                new CustomerAddressCriterion("15"),
                0,
                10
        );
        assertEquals(1, partialAddressResult.getTotalItems());
        assertThat(
                partialAddressResult.getItems(),
                contains(
                        hasProperty("id", is(2L))
                )
        );

        //Bold
        PaginationResult<Customer> boldAddressResult = customerSearchableDAO.search(
                new CustomerAddressCriterion("17 STREET"),
                0,
                10
        );
        assertEquals(1, boldAddressResult.getTotalItems());
        assertThat(
                boldAddressResult.getItems(),
                contains(
                        hasProperty("id", is(3L))
                )
        );

        //Empty
        //Bold
        PaginationResult<Customer> emptyAddressResult = customerSearchableDAO.search(
                new CustomerAddressCriterion("17 STREETSSSS"),
                0,
                10
        );
        assertEquals(0, emptyAddressResult.getTotalItems());
    }

    @Test
    public void searchPhoneShouldReturnCorrectResult() {
        //Full phone
        PaginationResult<Customer> fullPhoneResult = customerSearchableDAO.search(
                new CustomerPhoneCriterion("0182019222"),
                0,
                10
        );
        assertEquals(1, fullPhoneResult.getTotalItems());
        assertThat(
                fullPhoneResult.getItems(),
                contains(
                        hasProperty("id", is(1L))
                )
        );

        //Partial phone
        PaginationResult<Customer> partialPhoneResult = customerSearchableDAO.search(
                new CustomerPhoneCriterion("2172"),
                0,
                10
        );
        assertEquals(1, partialPhoneResult.getTotalItems());
        assertThat(
                partialPhoneResult.getItems(),
                contains(
                        hasProperty("id", is(2L))
                )
        );
    }

    @Test
    public void searchByIdAndBookingShouldReturnCorrectResult() {
        //Should coincide
        PaginationResult<Customer> coincidedResultOne = customerSearchableDAO.search(
                new CustomerBookingExistCriterion(
                        1L,
                        ZonedDateTime.of(2020, 1, 1, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh")),
                        ZonedDateTime.of(2020, 1, 5, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh"))
                ),
                0,
                10
        );
        assertEquals(1L, coincidedResultOne.getTotalItems());
        assertThat(
                coincidedResultOne.getItems(),
                contains(
                        hasProperty("id", is(1L))
                )
        );

        PaginationResult<Customer> coincidedResultTwo = customerSearchableDAO.search(
                new CustomerBookingExistCriterion(
                        2L,
                        ZonedDateTime.of(2020, 1, 1, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh")),
                        ZonedDateTime.of(2020, 1, 5, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh"))
                ),
                0,
                10
        );
        assertEquals(1L, coincidedResultTwo.getTotalItems());
        assertThat(
                coincidedResultTwo.getItems(),
                contains(
                        hasProperty("id", is(2L))
                )
        );

        PaginationResult<Customer> coincidedResultThree = customerSearchableDAO.search(
                new CustomerBookingExistCriterion(
                        3L,
                        ZonedDateTime.of(2020, 1, 1, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh")),
                        ZonedDateTime.of(2020, 1, 5, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh"))
                ),
                0,
                10
        );
        assertEquals(1L, coincidedResultThree.getTotalItems());
        assertThat(
                coincidedResultThree.getItems(),
                contains(
                        hasProperty("id", is(3L))
                )
        );

        PaginationResult<Customer> coincidedResultFour = customerSearchableDAO.search(
                new CustomerBookingExistCriterion(
                        2L,
                        ZonedDateTime.of(2020, 1, 20, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh")),
                        ZonedDateTime.of(2020, 2, 1, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh"))
                ),
                0,
                10
        );
        assertEquals(1L, coincidedResultFour.getTotalItems());
        assertThat(
                coincidedResultThree.getItems(),
                contains(
                        hasProperty("id", is(2L))
                )
        );

        //Should not coincide
        PaginationResult<Customer> noCoincideResultOne = customerSearchableDAO.search(
                new CustomerBookingExistCriterion(
                        1L,
                        ZonedDateTime.of(2020, 1, 6, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh")),
                        ZonedDateTime.of(2020, 1, 9, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh"))
                ),
                0,
                10
        );
        assertEquals(0L, noCoincideResultOne.getTotalItems());

        PaginationResult<Customer> noCoincideResultTwo = customerSearchableDAO.search(
                new CustomerBookingExistCriterion(
                        2L,
                        ZonedDateTime.of(2020, 1, 6, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh")),
                        ZonedDateTime.of(2020, 1, 9, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh"))
                ),
                0,
                10
        );
        assertEquals(0L, noCoincideResultTwo.getTotalItems());

        PaginationResult<Customer> noCoincideResultThree = customerSearchableDAO.search(
                new CustomerBookingExistCriterion(
                        3L,
                        ZonedDateTime.of(2020, 1, 6, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh")),
                        ZonedDateTime.of(2020, 1, 9, 0, 0, 0, 0,
                                ZoneId.of("Asia/Ho_Chi_Minh"))
                ),
                0,
                10
        );
        assertEquals(0L, noCoincideResultThree.getTotalItems());
    }
}
