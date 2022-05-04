package com.transonphat.carbooking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(StatisticController.class)
public class StatisticControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatisticController statisticController;
}
