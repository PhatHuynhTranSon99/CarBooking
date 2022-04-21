package com.transonphat.carbooking.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "Drivers")
@EntityListeners(AuditingEntityListener.class)
public class Driver extends Model {
    @Column
    private String licenseNumber;

    @Column
    private String phoneNumber;

    @Column
    private Double ratings;

    public Driver() {
    }

    public Driver(Long id, String licenseNumber, String phoneNumber, Double ratings, ZonedDateTime createdDate) {
        super(id, createdDate);
        this.licenseNumber = licenseNumber;
        this.phoneNumber = phoneNumber;
        this.ratings = ratings;
    }

    public Driver(String licenseNumber, String phoneNumber, Double ratings) {
        this.licenseNumber = licenseNumber;
        this.phoneNumber = phoneNumber;
        this.ratings = ratings;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Double getRatings() {
        return ratings;
    }

    public void setRatings(Double ratings) {
        this.ratings = ratings;
    }
}
