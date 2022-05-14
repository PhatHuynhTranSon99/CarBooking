package com.transonphat.carbooking.charge;

import org.springframework.stereotype.Component;

/**
 * Author: Tran Son Phat
 * Implementation of charge calculator: charge = distance * ratePerDistance
 * This is a simple form of Strategy design pattern
 */
@Component
public class StandardChargeCalculator implements ChargeCalculator {
    @Override
    public double calculateTotalCharge(double distance, double ratePerDistance) {
        return distance * ratePerDistance;
    }
}
