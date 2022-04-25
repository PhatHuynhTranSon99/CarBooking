package com.transonphat.carbooking.controllers;

import com.transonphat.carbooking.domain.Invoice;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.search.invoice.InvoiceDateCriterion;
import com.transonphat.carbooking.services.InvoiceService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@RestController
public class InvoiceController {
    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/invoices")
    public PaginationResult<Invoice> filterInvoiceByDate(@RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime from,
                                                         @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime to,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "3") int size) {
        return this.invoiceService.searchInvoice(
                new InvoiceDateCriterion(from, to),
                page,
                size
        );
    }
}
