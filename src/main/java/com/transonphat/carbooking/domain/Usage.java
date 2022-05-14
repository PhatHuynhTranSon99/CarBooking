package com.transonphat.carbooking.domain;

import java.util.Objects;

/**
 * Author: Tran Son Phat
 * Represent an entry in the car usage query
 * Has car id field and number of days field
 */
public class Usage {
    private Long carId;
    private Long days;

    public Usage(Long id, Long days) {
        this.carId = id;
        this.days = days;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Long getDays() {
        return days;
    }

    public void setDays(Long days) {
        this.days = days;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usage usage = (Usage) o;

        if (!Objects.equals(carId, usage.carId)) return false;
        return Objects.equals(days, usage.days);
    }

    @Override
    public int hashCode() {
        int result = carId != null ? carId.hashCode() : 0;
        result = 31 * result + (days != null ? days.hashCode() : 0);
        return result;
    }
}
