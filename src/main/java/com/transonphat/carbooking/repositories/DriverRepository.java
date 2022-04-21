package com.transonphat.carbooking.repositories;

import com.transonphat.carbooking.domain.Driver;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends PagingAndSortingRepository<Driver, Long>,
        JpaSpecificationExecutor<Driver> {
}
