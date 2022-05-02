package com.transonphat.carbooking.dao.invoice;

import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Invoice;
import com.transonphat.carbooking.exceptions.InvoiceNotFoundException;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.repositories.InvoiceRepository;
import com.transonphat.carbooking.search.SearchCriterion;
import com.transonphat.carbooking.search.SearchSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MySQLInvoiceDAO implements InvoiceDAO, SearchableDAO<Invoice> {
    private InvoiceRepository invoiceRepository;

    public MySQLInvoiceDAO(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public Invoice save(Invoice invoice) {
        return this.invoiceRepository.save(invoice);
    }

    @Override
    public Invoice delete(long id) {
        Invoice invoice = this.getOne(id);
        this.invoiceRepository.delete(invoice);
        return invoice;
    }

    @Override
    public PaginationResult<Invoice> getAll(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Invoice> invoicePage = this.invoiceRepository.findAll(pageable);

        return new PaginationResult<>(
                invoicePage.getTotalElements(),
                invoicePage.get().collect(Collectors.toList()),
                invoicePage.getNumber(),
                invoicePage.getTotalPages()
        );
    }

    @Override
    public Invoice getOne(long id) {
        return this.invoiceRepository.findById(id).orElseThrow(() -> new InvoiceNotFoundException("Invoice does not exist"));
    }

    @Override
    public PaginationResult<Invoice> search(SearchCriterion<Invoice> criteria, int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Invoice> invoicePage = this.invoiceRepository.findAll(
                new SearchSpecification<>(criteria),
                pageable
        );

        return new PaginationResult<>(
                invoicePage.getTotalElements(),
                invoicePage.get().collect(Collectors.toList()),
                invoicePage.getNumber(),
                invoicePage.getTotalPages()
        );
    }
}
