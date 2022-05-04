package com.transonphat.carbooking.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transonphat.carbooking.exceptions.types.MissingPeriodException;
import com.transonphat.carbooking.services.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @Test
    public void findAvailableCarsShouldCallServiceMethod() throws Exception {
        //Call endpoints
        String startTime = "2020-01-06T07:30:00.000+07:00";
        String endTime = "2020-01-20T07:30:00.000+07:00";
        String page = "0";
        String size = "3";

        //Create params
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("start", startTime);
        requestParams.add("end", endTime);
        requestParams.add("page", page);
        requestParams.add("size", size);

        //Call find cars
        mockMvc.perform(
                get("/bookings/cars")
                        .params(requestParams)
        )
                .andExpect(status().isOk());

        //Assert call to mock bean
        verify(bookingService).findAvailableCars(any(), any(), eq(0), eq(3));
    }

    @Test
    public void getAllBookingsShouldCallServiceMethod() throws Exception {
        //Call endpoints
        String startTime = "2020-01-06T07:30:00.000+07:00";
        String endTime = "2020-01-20T07:30:00.000+07:00";
        String page = "0";
        String size = "3";

        //Create params
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("from", startTime);
        requestParams.add("to", endTime);
        requestParams.add("page", page);
        requestParams.add("size", size);

        mockMvc.perform(
                        get("/bookings")
                                .params(requestParams)
                )
                .andExpect(status().isOk());

        //Assert call
        verify(bookingService).searchBookings(any(), eq(0), eq(3));
    }

    @Test
    public void getAllBookingsShouldThrowExceptionWhenFromIsMissing() throws Exception {
        //Call endpoints
        String startTime = "2020-01-06T07:30:00.000+07:00";
        String page = "0";
        String size = "3";

        //Create params
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("from", startTime);
        requestParams.add("page", page);
        requestParams.add("size", size);

        mockMvc.perform(
                        get("/bookings")
                                .params(requestParams)
                )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MissingPeriodException));
    }

    @Test
    public void getAllBookingsShouldThrowExceptionWhenToIsMissing() throws Exception {
        //Call endpoints
        String startTime = "2020-01-06T07:30:00.000+07:00";
        String page = "0";
        String size = "3";

        //Create params
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("to", startTime);
        requestParams.add("page", page);
        requestParams.add("size", size);

        mockMvc.perform(
                        get("/bookings")
                                .params(requestParams)
                )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MissingPeriodException));
    }

    @Test
    public void deleteBookingShouldCallServiceMethod() throws Exception {
        //Call endpoint
        mockMvc.perform(delete("/bookings/1"))
                .andExpect(status().isOk());

        //Assert call
        verify(bookingService).deleteBooking(1L);
    }

    @Test
    public void createBookingShouldCallServiceMethod() throws Exception {
        //Params
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("startingLocation", "Street");
        requestParams.add("destinationLocation", "Street Too");
        requestParams.add("startTime", "2020-01-06T07:30:00.000+07:00");
        requestParams.add("endTime", "2020-01-20T07:30:00.000+07:00");
        requestParams.add("distance", "100.0");
        requestParams.add("carId", "1");

        //Call endpoint
        mockMvc.perform(post("/customers/1/bookings").params(requestParams))
                .andExpect(status().isOk());

        //Assert call to service
        verify(bookingService).createBooking(
                eq("Street"),
                eq("Street Too"),
                any(),
                any(),
                eq(100.0),
                eq(1L),
                eq(1L)
        );
    }
}
