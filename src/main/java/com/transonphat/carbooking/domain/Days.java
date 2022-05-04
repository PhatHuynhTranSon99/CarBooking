package com.transonphat.carbooking.domain;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Days days1 = (Days) o;

        return Objects.equals(days, days1.days);
    }

    @Override
    public int hashCode() {
        return days != null ? days.hashCode() : 0;
    }
}
