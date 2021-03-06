package com.transonphat.carbooking.domain;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Author: Tran Son Phat
 * Represent the invoice object
 * Properties: customer, driver, total charge of the trip and the related booking object
 */
@Entity
@Table(name = "Invoices")
@EntityListeners(AuditingEntityListener.class)
public class Invoice extends Model {
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @Column
    @NotNull
    private Double totalCharge;

    @OneToOne(mappedBy = "invoice")
    private Booking booking;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Double getTotalCharges() {
        return totalCharge;
    }

    public void setTotalCharges(Double totalCharges) {
        this.totalCharge = totalCharges;
    }
}
