package com.transonphat.carbooking.controllers;

import com.transonphat.carbooking.domain.Driver;
import com.transonphat.carbooking.services.AllocationService;
import com.transonphat.carbooking.services.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AllocationController.class)
public class AllocationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AllocationService allocationService;

    @MockBean
    private BookingService bookingService;

    @Test
    public void allocateDriverShouldCallServiceMethod() throws Exception {
        //Call endpoint
        mockMvc.perform(
                post("/cars/1/driver/1")
        )
                .andExpect(status().isOk());

        //Verify call
        verify(allocationService).allocateDriverToCar(1L, 1L);
    }

    @Test
    public void deallocateDriverShouldCallServiceMethod() throws Exception {
        //Set up
        Driver driver = new Driver(1L, "Adam", "Levine",
                "0909111909", 4.9, ZonedDateTime.now());
        when(allocationService.removeDriverFromCar(1L)).thenReturn(driver);

        //Call endpoint
        mockMvc.perform(
                delete("/cars/1/driver")
        )
                .andExpect(status().isOk());

        //Verify call
        verify(bookingService).deleteRelatedBookingsWithDriver(1L);
        verify(allocationService).removeDriverFromCar(1L);
    }
}
