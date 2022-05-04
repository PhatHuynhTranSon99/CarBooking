package com.transonphat.carbooking.domain;

import java.util.Objects;

public class Revenue {
    private Double revenue;

    public Revenue(Double revenue) {
        this.revenue = revenue;
    }

    public static Revenue from(Double revenue) {
        return new Revenue(revenue);
    }

    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Revenue revenue1 = (Revenue) o;

        return Objects.equals(revenue, revenue1.revenue);
    }

    @Override
    public int hashCode() {
        return revenue != null ? revenue.hashCode() : 0;
    }
}
