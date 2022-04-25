package com.transonphat.carbooking.dao.aggregation;

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
}
