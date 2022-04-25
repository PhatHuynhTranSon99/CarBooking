package com.transonphat.carbooking.dao.aggregation;

public class Days {
    private Integer days;

    public Days(Integer days) {
        this.days = days;
    }

    public static Days of(Integer days) {
        return new Days(days);
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
}
