package com.transonphat.carbooking.charge;

import org.springframework.stereotype.Component;

@Component
public class StandardChargeCalculator implements ChargeCalculator {
    @Override
    public double calculateTotalCharge(double distance, double ratePerDistance) {
        return distance * ratePerDistance;
    }
}
