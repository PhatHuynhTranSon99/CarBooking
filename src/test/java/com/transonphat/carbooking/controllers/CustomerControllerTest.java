package com.transonphat.carbooking.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transonphat.carbooking.domain.Customer;
import com.transonphat.carbooking.exceptions.types.CustomerNotFoundException;
import com.transonphat.carbooking.services.BookingService;
import com.transonphat.carbooking.services.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Author: Tran Son Phat
 * MockMVC tests for CustomerController
 */
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private BookingService bookingService;

    @Test
    public void getCustomerByIdSuccessfully() throws Exception {
        Customer customer = new Customer(1L, "Adam", "Cole", "11 Street",
                "0182019222", ZonedDateTime.now());
        when(customerService.getCustomerById(1L)).thenReturn(customer);

        mockMvc.perform(get("/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customer.getId()))
                .andExpect(jsonPath("$.firstName").value(customer.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(customer.getLastName()))
                .andExpect(jsonPath("$.phoneNumber").value(customer.getPhoneNumber()));
    }

    @Test
    public void getCustomerByIdShouldThrowExceptionWhenNotFound() throws Exception {
        when(customerService.getCustomerById(1L)).thenThrow(new CustomerNotFoundException("Customer does not exist"));

        mockMvc.perform(get("/customers/1"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CustomerNotFoundException));
    }

    @Test
    public void createCustomerSuccessfully() throws Exception {
        Customer customer = new Customer(1L, "Adam", "Cole", "11 Street",
                "0182019222", ZonedDateTime.now());
        when(customerService.saveCustomer(customer)).thenReturn(customer);

        mockMvc.perform(
                post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer))
        )
                .andExpect(status().isOk());

        verify(customerService).saveCustomer(customer);
    }

    @Test
    public void createCustomerShouldThrowExceptionWhenMissingAttributes() throws Exception {
        //Missing first name
        Customer customer = new Customer(1L, null, "Cole", "11 Street",
                "0182019222", ZonedDateTime.now());
        when(customerService.saveCustomer(customer)).thenReturn(customer);

        mockMvc.perform(
                        post("/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(customer))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateCustomerSuccessfully() throws Exception {
        //New attributes
        String newFirstName = "John";
        String newLastName = "Celine";
        String newAddress = "15 Street";
        String newPhoneNumber = "0909191019";

        Customer customer = new Customer(1L, "Adam", "Cole", "11 Street",
                "0182019222", ZonedDateTime.now());
        Customer newCustomer = new Customer(1L, newFirstName, newLastName, newAddress,
                newPhoneNumber, ZonedDateTime.now());

        when(customerService.getCustomerById(1L)).thenReturn(customer);
        when(customerService.saveCustomer(any())).thenReturn(newCustomer);

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("firstName", newFirstName);
        requestParams.add("lastName", newLastName);
        requestParams.add("address", newAddress);
        requestParams.add("phoneNumber", newPhoneNumber);

        mockMvc.perform(
                    put("/customers/1")
                            .params(requestParams)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(newCustomer.getId()))
                .andExpect(jsonPath("$.firstName").value(newCustomer.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(newCustomer.getLastName()))
                .andExpect(jsonPath("$.phoneNumber").value(newCustomer.getPhoneNumber()));
    }

    @Test
    public void updateCustomerShouldThrowExceptionWhenNotFound() throws Exception {
        when(customerService.getCustomerById(1L)).thenThrow(new CustomerNotFoundException("Customer does not exist"));

        mockMvc.perform(put("/customers/1"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CustomerNotFoundException));
    }

    @Test
    public void deleteCustomerSuccessfully() throws Exception {
        Customer customer = new Customer(1L, "Adam", "Cole", "11 Street",
                "0182019222", ZonedDateTime.now());
        when(customerService.deleteCustomer(1L)).thenReturn(customer);

        mockMvc.perform(delete("/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customer.getId()))
                .andExpect(jsonPath("$.firstName").value(customer.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(customer.getLastName()))
                .andExpect(jsonPath("$.phoneNumber").value(customer.getPhoneNumber()));

        verify(bookingService).deleteRelatedBookingsWithCustomer(1L);
    }

    @Test
    public void getAllCustomersSuccessfully() throws Exception {
        //Assert call to search customer
        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk());

        verify(customerService).searchCustomer(any(), anyInt(), anyInt());
    }
}
