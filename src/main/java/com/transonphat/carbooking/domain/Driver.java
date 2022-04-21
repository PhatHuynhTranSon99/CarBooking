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
    private String firstName;

    @Column String lastName;

    @Column
    private String phoneNumber;

    @Column
    private Double ratings;

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
}
