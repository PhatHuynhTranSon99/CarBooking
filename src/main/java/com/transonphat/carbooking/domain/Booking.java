package com.transonphat.carbooking.domain;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Entity
@Table(name = "Bookings")
@EntityListeners(AuditingEntityListener.class)
public class Booking extends Model {
    @Column
    @NotNull
    private ZonedDateTime startTime;

    @Column
    @NotNull
    private ZonedDateTime endTime;

    @Column
    @NotNull
    private String startLocation;

    @Column
    @NotNull
    private String endLocation;

    @Column
    @NotNull
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
