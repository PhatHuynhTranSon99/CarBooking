package com.transonphat.carbooking.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transonphat.carbooking.domain.Driver;
import com.transonphat.carbooking.exceptions.types.DriverNotFoundException;
import com.transonphat.carbooking.services.BookingService;
import com.transonphat.carbooking.services.DriverService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Author: Tran Son Phat
 * MockMVC tests for DriverController
 */
@WebMvcTest(DriverController.class)
public class DriverControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DriverService driverService;

    @MockBean
    private BookingService bookingService;

    @Test
    public void getDriverByIdShouldReturnCorrectResult() throws Exception {
        Driver driver = new Driver(1L, "Adam", "Levine",
                "0909111909", 4.9, ZonedDateTime.now());
        when(driverService.getDriverById(1L)).thenReturn(driver);

        mockMvc.perform(get("/drivers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(driver.getId()))
                .andExpect(jsonPath("$.firstName").value(driver.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(driver.getLastName()))
                .andExpect(jsonPath("$.phoneNumber").value(driver.getPhoneNumber()))
                .andExpect(jsonPath("$.ratings").value(driver.getRatings()));
    }

    @Test
    public void getDriverByIdThrowExceptionWhenNotFound() throws Exception {
        when(driverService.getDriverById(1L)).thenThrow(new DriverNotFoundException("Driver does not exist"));

        mockMvc.perform(get("/drivers/1"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DriverNotFoundException));
    }

    @Test
    public void createDriverSuccessfully() throws Exception {
        Driver driver = new Driver(1L, "Adam", "Levine",
                "0909111909", 4.9, ZonedDateTime.now());
        when(driverService.saveDriver(driver)).thenReturn(driver);

        mockMvc.perform(
                        post("/drivers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(driver))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(driver.getId()))
                .andExpect(jsonPath("$.firstName").value(driver.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(driver.getLastName()))
                .andExpect(jsonPath("$.phoneNumber").value(driver.getPhoneNumber()))
                .andExpect(jsonPath("$.ratings").value(driver.getRatings()));
    }

    @Test
    public void createDriverShouldThrowExceptionWhenMissingAttributes() throws Exception {
        //Missing first name
        Driver driver = new Driver(1L, null, "Levine",
                "0909111909", 4.9, ZonedDateTime.now());
        when(driverService.saveDriver(driver)).thenReturn(driver);

        mockMvc.perform(
                        post("/drivers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(driver))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateCarSuccessfully() throws Exception {
        String newFirstName = "Daym";
        String newLastName = "Boy";
        String newPhoneNumber = "Street Another";
        String newRatings = "3.7";

        Driver driver = new Driver(1L, "Adam", "Levine",
                "0909111909", 4.9, ZonedDateTime.now());
        Driver newDriver = new Driver(1L, "Adam", "Levine",
                "0909111909", 4.9, ZonedDateTime.now());

        when(driverService.getDriverById(1L)).thenReturn(driver);
        when(driverService.saveDriver(any())).thenReturn(newDriver);

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("firstName", newFirstName);
        requestParams.add("lastName", newLastName);
        requestParams.add("phoneNumber", newPhoneNumber);
        requestParams.add("ratings", newRatings);

        mockMvc.perform(
                        put("/drivers/1").params(requestParams)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(newDriver.getId()))
                .andExpect(jsonPath("$.firstName").value(newDriver.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(newDriver.getLastName()))
                .andExpect(jsonPath("$.phoneNumber").value(newDriver.getPhoneNumber()))
                .andExpect(jsonPath("$.ratings").value(newDriver.getRatings()));
    }

    @Test
    public void updateCarShouldThrowExceptionWhenNotFound() throws Exception {
        when(driverService.getDriverById(1L)).thenThrow(new DriverNotFoundException("Driver does not exist"));

        mockMvc.perform(put("/drivers/1"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DriverNotFoundException));
    }

    @Test
    public void deleteDriverByIdSuccessfully() throws Exception {
        Driver driver = new Driver(1L, "Adam", "Levine",
                "0909111909", 4.9, ZonedDateTime.now());
        when(driverService.deleteDriver(1L)).thenReturn(driver);

        mockMvc.perform(delete("/drivers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(driver.getId()))
                .andExpect(jsonPath("$.firstName").value(driver.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(driver.getLastName()))
                .andExpect(jsonPath("$.phoneNumber").value(driver.getPhoneNumber()))
                .andExpect(jsonPath("$.ratings").value(driver.getRatings()));

        verify(bookingService).deleteRelatedBookingsWithDriver(1L);
    }

    @Test
    public void getAllDriverSuccessfully() throws Exception {
        //Assert that search driver is called
        mockMvc.perform(get("/drivers"))
                .andExpect(status().isOk());

        verify(driverService).searchDriver(any(), anyInt(), anyInt());
    }
}
