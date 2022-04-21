package com.transonphat.carbooking.repositories;

import com.transonphat.carbooking.domain.Car;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends PagingAndSortingRepository<Car, Long> {
}
