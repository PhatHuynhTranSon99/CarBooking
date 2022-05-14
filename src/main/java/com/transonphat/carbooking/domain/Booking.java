package com.transonphat.carbooking.domain;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

/**
 * Author: Tran Son Phat
 * Represent the booking object
 * Properties: start-time and end-time, start-location and end-location
 * distance of the trip, and invoice of the trip
 */
@Entity
@Table(name = "Bookings")
@EntityListeners(AuditingEntityListener.class)
public class Booking extends Model {
    @Column
    @NotNull(message = "Start time is mandatory")
    private ZonedDateTime startTime;

    @Column
    @NotNull(message = "End time is mandatory")
    private ZonedDateTime endTime;

    @Column
    @NotBlank(message = "Start location is mandatory")
    private String startLocation;

    @Column
    @NotBlank(message = "Destination is mandatory")
    private String endLocation;

    @Column
    @NotNull
    @DecimalMin(value = "0.0", message = "Distance must be greater than 0")
    private Double distance;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
