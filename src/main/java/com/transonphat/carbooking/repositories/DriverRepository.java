package com.transonphat.carbooking.repositories;

import com.transonphat.carbooking.domain.Driver;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends CrudRepository<Driver, Long> {
}
