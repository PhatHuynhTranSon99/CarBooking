package com.transonphat.carbooking.repositories;

import com.transonphat.carbooking.domain.Customer;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Author: Tran Son Phat
 * Spring JPA repository for paging and sorting
 * JpaSpecificationExecutor for performing searching with Specification object
 */
@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long>,
        JpaSpecificationExecutor<Customer> {
}
