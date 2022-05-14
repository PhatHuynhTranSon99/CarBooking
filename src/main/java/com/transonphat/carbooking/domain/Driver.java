package com.transonphat.carbooking.domain;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.Set;


/**
 * Author: Tran Son Phat
 * Represent the driver object
 * Properties: first name and  last name, phone number,
 * ratings and a car object
 */
@Entity
@Table(name = "Drivers")
@EntityListeners(AuditingEntityListener.class)
public class Driver extends Model {
    @Column
    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @Column
    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @Column
    @NotNull(message = "Phone number is mandatory")
    private String phoneNumber;

    @Column
    @DecimalMin(value = "0.0", message = "Rating should not be below 0.0")
    @DecimalMax(value = "5.0", message = "Rating should not be higher than 5.0")
    private Double ratings = 0.0;

    @OneToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @OneToMany(mappedBy = "driver")
    private Set<Invoice> invoices;

    public Driver() {
    }

    public Driver(Long id, String firstName, String lastName,
                  String phoneNumber, Double ratings, ZonedDateTime createdDate) {
        super(id, createdDate);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.ratings = ratings;
    }

    public Driver(String firstName, String lastName, String phoneNumber, Double ratings) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.ratings = ratings;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void removeCar() { this.car = null; }
}
