package com.transonphat.carbooking.repositories;

import com.transonphat.carbooking.domain.Customer;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long>,
        JpaSpecificationExecutor<Customer> {
}
