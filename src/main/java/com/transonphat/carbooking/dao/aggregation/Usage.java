package com.transonphat.carbooking.dao.aggregation;

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
}
