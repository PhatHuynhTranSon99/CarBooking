package com.transonphat.carbooking.repositories;

import com.transonphat.carbooking.domain.Booking;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends PagingAndSortingRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {
}
