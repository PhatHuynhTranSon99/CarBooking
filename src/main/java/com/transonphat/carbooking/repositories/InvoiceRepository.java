package com.transonphat.carbooking.repositories;

import com.transonphat.carbooking.domain.Invoice;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends PagingAndSortingRepository<Invoice, Long>, JpaSpecificationExecutor<Invoice> {
}
