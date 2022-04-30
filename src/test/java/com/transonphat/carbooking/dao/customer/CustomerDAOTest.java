package com.transonphat.carbooking.dao.customer;

import com.transonphat.carbooking.dao.DAO;
import com.transonphat.carbooking.domain.Customer;
import com.transonphat.carbooking.exceptions.CustomerNotFoundException;
import com.transonphat.carbooking.pagination.PaginationResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles(profiles = {"test"})
@Transactional
class CustomerDAOTest {
    @Autowired
    private DAO<Customer> customerDAO;

    @Test
    public void findByIdReturnCorrectResult() {
        Customer customer = customerDAO.getOne(1L);

        assertEquals(1L, customer.getId());
        assertEquals("Adam", customer.getFirstName());
        assertEquals("Cole", customer.getLastName());
        assertEquals("Street", customer.getAddress());
        assertEquals("0182019222", customer.getPhoneNumber());
    }

    @Test
    public void findByIdNotFound() {
        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> {
            customerDAO.getOne(100L);
        });

        assertEquals("Customer does not exist", exception.getMessage());
    }

    @Test
    @Rollback
    public void deleteCustomerSuccessfully() {
        Customer customer = customerDAO.delete(1L);

        assertEquals(1L, customer.getId());
        assertEquals("Adam", customer.getFirstName());
        assertEquals("Cole", customer.getLastName());
        assertEquals("Street", customer.getAddress());
        assertEquals("0182019222", customer.getPhoneNumber());
    }

    @Test
    public void deleteCustomerNotFound() {
        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> {
           customerDAO.delete(100L);
        });

        assertEquals("Customer does not exist", exception.getMessage());
    }

    @Test
    public void findAllCCustomersReturnCorrectResult() {
        PaginationResult<Customer> customerPaginationResult = customerDAO.getAll(0, 10);
        assertEquals(3, customerPaginationResult.getTotalItems());

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
    @Rollback
    public void addCustomerSuccessfully() {
        Customer customer = new Customer();
        customer.setFirstName("Man");
        customer.setLastName("Kind");
        customer.setAddress("Street");
        customer.setPhoneNumber("0123456789");
        customer = customerDAO.save(customer);

        assertNotNull(customer.getId());
        assertEquals("Man", customer.getFirstName());
        assertEquals("Kind", customer.getLastName());
        assertEquals("Street", customer.getAddress());
        assertEquals("0123456789", customer.getPhoneNumber());
    }
}