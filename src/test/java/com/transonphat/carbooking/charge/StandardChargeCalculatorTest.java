package com.transonphat.carbooking.charge;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Author: Tran Son Phat
 * Unit tests for standard charge calculation
 */
@SpringBootTest
public class StandardChargeCalculatorTest {
    @Autowired
    private StandardChargeCalculator standardChargeCalculator;

    @Test
    public void shouldBeEqual() {
        assertEquals(10.0, standardChargeCalculator.calculateTotalCharge(5.0, 2.0));
        assertEquals(100.0, standardChargeCalculator.calculateTotalCharge(10.0, 10.0));
    }

    @Test
    public void shouldNotBeEqual() {
        assertNotEquals(-0.0, standardChargeCalculator.calculateTotalCharge(50.0, 7.0));
        assertNotEquals(200.0, standardChargeCalculator.calculateTotalCharge(290.0, 50.0));
    }
}
