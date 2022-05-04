package com.transonphat.carbooking.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Cars")
@EntityListeners(AuditingEntityListener.class)
public class Car extends Model {
    @Column
    @NotBlank(message = "Identification number is mandatory")
    private String identificationNumber;

    @Column
    @NotNull
    private String make = "Unknown";

    @Column
    @NotNull
    private String model = "Unknown";

    @Column
    @NotNull
    private String color = "Unknown";

    @Column
    @NotNull
    private Boolean isConvertible = false;

    @Column
    @DecimalMin(value = "0.0", message = "Rating should not be below 0.0")
    @DecimalMax(value = "5.0", message = "Rating should not be higher than 5.0")
    private Double rating = 0.0;

    @Column
    @NotBlank(message = "License plate is mandatory")
    private String licensePlate;

    @Column
    private Double rate = 0.0;

    @OneToOne(mappedBy = "car", fetch = FetchType.LAZY)
    @JsonIgnore
    private Driver driver;

    @PreRemove
    private void updateDriver() {
        driver.setCar(null);
    }

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

    public Boolean getIsConvertible() {
        return isConvertible;
    }

    public void setIsConvertible(Boolean convertible) {
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

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public boolean isAllocated() {
        return this.driver != null;
    }
}
