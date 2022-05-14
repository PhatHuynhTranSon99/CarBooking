package com.transonphat.carbooking.controllers;

import com.transonphat.carbooking.domain.Invoice;
import com.transonphat.carbooking.exceptions.types.MissingPeriodException;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.search.SearchCriteria;
import com.transonphat.carbooking.search.SearchCriterion;
import com.transonphat.carbooking.search.invoice.InvoiceByCustomerCriterion;
import com.transonphat.carbooking.search.invoice.InvoiceByDriverCriterion;
import com.transonphat.carbooking.search.invoice.InvoiceDateCriterion;
import com.transonphat.carbooking.services.InvoiceService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * Author: Tran Son Phat
 * InvoiceController contains all endpoints to perform invoice management
 */
@RestController
public class InvoiceController {
    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/invoices")
    public PaginationResult<Invoice> getAllInvoices(@RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime from,
                                                    @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime to,
                                                    @RequestParam(value = "customer", required = false) Long customerId,
                                                    @RequestParam(value = "driver", required = false) Long driverId,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "3") int size) {
        //From and to must all be visible
        if ((from != null && to == null) || (from == null && to != null))
            throw new MissingPeriodException("From and to must all be visible");

        //Create list of criterion
        List<SearchCriterion<Invoice>> criterionList = new LinkedList<>();

        //Add to list
        if (from != null) {
            criterionList.add(new InvoiceDateCriterion(from, to));
        }
        if (customerId != null) {
            criterionList.add(new InvoiceByCustomerCriterion(customerId));
        }
        if (driverId != null) {
            criterionList.add(new InvoiceByDriverCriterion(driverId));
        }

        return this.invoiceService.searchInvoice(
                SearchCriteria.and(criterionList),
                page,
                size
        );
    }
}
