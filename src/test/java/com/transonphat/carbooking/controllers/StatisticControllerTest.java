package com.transonphat.carbooking.controllers;

import com.transonphat.carbooking.services.StatisticService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Author: Tran Son Phat
 * MockMVC tests for StatisticController
 */
@WebMvcTest(StatisticController.class)
public class StatisticControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatisticService statisticService;

    @Test
    public void getRevenueByCustomerShouldCallServiceMethod() throws Exception {
        //Params
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("from", "2020-01-06T00:00:00.000+07:00");
        requestParams.add("to", "2020-01-20T00:00:00.000+07:00");
        requestParams.add("customer", "1");

        //Call endpoint
        mockMvc.perform(get("/statistics/revenue/customer").params(requestParams))
                .andExpect(status().isOk());

        //Assert call
        verify(statisticService).getRevenueByCustomer(
                any(),
                any(),
                eq(1L)
        );
    }

    @Test
    public void getRevenueByDriverShouldCallServiceMethod() throws Exception {
        //Params
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("from", "2020-01-06T00:00:00.000+07:00");
        requestParams.add("to", "2020-01-20T00:00:00.000+07:00");
        requestParams.add("driver", "1");

        //Call endpoint
        mockMvc.perform(get("/statistics/revenue/driver").params(requestParams))
                .andExpect(status().isOk());

        //Assert call
        verify(statisticService).getRevenueByDriver(
                any(),
                any(),
                eq(1L)
        );
    }

    @Test
    public void getCarUsageShouldCallServiceMethod() throws Exception {
        //Params
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("month", "1");
        requestParams.add("year", "2020");
        requestParams.add("page", "0");
        requestParams.add("size", "3");

        //Call
        mockMvc.perform(get("/statistics/usage").params(requestParams))
                .andExpect(status().isOk());

        //Assert call
        verify(statisticService).getCarUsage(1, 2020, 0, 3);
    }
}
