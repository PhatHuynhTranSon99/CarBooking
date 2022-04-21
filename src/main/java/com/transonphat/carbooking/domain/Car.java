package com.transonphat.carbooking.domain;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;

@Entity
@Table(name = "Cars")
@EntityListeners(AuditingEntityListener.class)
public class Car extends Model {
    @Column
    private String identificationNumber;

    @Column
    private String make;

    @Column
    private String model;

    @Column
    private String color;

    @Column
    private Boolean isConvertible;

    @Column
    private Double rating;

    @Column
    private String licensePlate;

    @Column
    private Double rate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private Driver driver;

    public Car() {
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Boolean getConvertible() {
        return isConvertible;
    }

    public void setConvertible(Boolean convertible) {
        isConvertible = convertible;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Driver getDriver() {
        return driver;
    }

    public void removeDriver() {
        this.driver = null;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public boolean isAllocated() {
        return this.driver != null;
    }
}
