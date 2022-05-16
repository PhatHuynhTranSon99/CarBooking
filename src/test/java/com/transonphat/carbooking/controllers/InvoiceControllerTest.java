package com.transonphat.carbooking.controllers;

import com.transonphat.carbooking.exceptions.types.MissingPeriodException;
import com.transonphat.carbooking.services.InvoiceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Author: Tran Son Phat
 * MockMVC tests for InvoiceController
 */
@WebMvcTest(InvoiceController.class)
public class InvoiceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvoiceService invoiceService;

    @Test
    public void getAllInvoicesShouldCallSearchMethod() throws Exception {
        //Call endpoint
        mockMvc.perform(get("/invoices"))
                .andExpect(status().isOk());

        //Assert call
        verify(invoiceService).searchInvoice(any(), anyInt(), anyInt());
    }

    @Test
    public void getAllInvoicesShouldThrowExceptionWhenFromIsMissing() throws Exception {
        //Params
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("from", "2020-01-06T07:30:00.000+07:00");

        //Call endpoint expecting exception
        mockMvc.perform(
                get("/invoices")
                        .params(requestParams)
        )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MissingPeriodException));
    }

    @Test
    public void getAllInvoicesShouldThrowExceptionWhenToIsMissing() throws Exception {
        //Params
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("to", "2020-01-06T07:30:00.000+07:00");

        //Call endpoint expecting exception
        mockMvc.perform(
                        get("/invoices")
                                .params(requestParams)
                )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MissingPeriodException));
    }
}
