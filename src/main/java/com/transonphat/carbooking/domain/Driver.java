package com.transonphat.carbooking.domain;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "Drivers")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String licenseNumber;

    @Column
    private String phoneNumber;

    @Column
    private Float ratings;

    @CreatedDate
    @Column
    private ZonedDateTime createdDate;

    public Driver() {
    }

    public Driver(String licenseNumber, String phoneNumber, Float ratings) {
        this.licenseNumber = licenseNumber;
        this.phoneNumber = phoneNumber;
        this.ratings = ratings;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Float getRatings() {
        return ratings;
    }

    public void setRatings(Float ratings) {
        this.ratings = ratings;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Driver driver = (Driver) o;

        return Objects.equals(id, driver.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
