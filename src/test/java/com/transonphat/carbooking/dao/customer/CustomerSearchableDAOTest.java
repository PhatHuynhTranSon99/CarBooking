package com.transonphat.carbooking.dao.customer;

import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Customer;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.search.SearchCriteria;
import com.transonphat.carbooking.search.customer.CustomerAddressCriterion;
import com.transonphat.carbooking.search.customer.CustomerNameCriterion;
import com.transonphat.carbooking.search.customer.CustomerPhoneCriterion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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
    public void searchWithNoCriteria() {
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
    public void searchWithFirstNameCriteria() {
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
    public void searchWithLastNameCriteria() {
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
    public void searchAddressCriteria() {
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
    public void searchPhoneCriteria() {
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
}