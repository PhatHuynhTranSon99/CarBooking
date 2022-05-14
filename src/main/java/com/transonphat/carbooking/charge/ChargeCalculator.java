package com.transonphat.carbooking.charge;

/**
 * Author: Tran Son Phat
 * Interface calculate the charge based on the distance of the trip and rate per distance unit.
 */
public interface ChargeCalculator {
    double calculateTotalCharge(double distance, double ratePerDistance);
}
