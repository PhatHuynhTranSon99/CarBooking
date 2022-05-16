package com.transonphat.carbooking.services;

import com.transonphat.carbooking.dao.CrudDAO;
import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Customer;
import com.transonphat.carbooking.search.SearchCriterion;
import com.transonphat.carbooking.search.customer.CustomerNameCriterion;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

/**
 * Author: Tran Son Phat
 * Unit tests for customer service
 */
@SpringBootTest(classes = {CustomerService.class})
@ActiveProfiles(profiles = {"test"})
public class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;

    @MockBean
    private CrudDAO<Customer> customerCrudDAO;

    @MockBean
    private SearchableDAO<Customer> customerSearchableDAO;

    @Test
    public void saveCustomerShouldCallDAOMethod() {
        Customer newCustomer = new Customer();
        customerService.saveCustomer(newCustomer);
        Mockito.verify(customerCrudDAO).save(newCustomer);
    }

    @Test
    public void deleteCustomerShouldCallDAOMethod() {
        customerService.deleteCustomer(1L);
        Mockito.verify(customerCrudDAO).delete(1L);
    }

    @Test
    public void getCustomerByIdShouldCallDAOMethod() {
        customerService.getCustomerById(2L);
        Mockito.verify(customerCrudDAO).getOne(2L);
    }

    @Test
    public void searchCustomerShouldCallDAOMethod() {
        //Create criterion
        SearchCriterion<Customer> customerSearchCriterion = new CustomerNameCriterion("Hello World");
        customerService.searchCustomer(customerSearchCriterion, 0, 10);
        Mockito.verify(customerSearchableDAO).search(customerSearchCriterion, 0, 10);
    }
}
