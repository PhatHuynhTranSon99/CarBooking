package com.transonphat.carbooking.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Customers")
@EntityListeners(AuditingEntityListener.class)
public class Customer extends Model {
    @Column
    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @Column
    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @Column
    @NotNull
    private String address;

    @Column
    @NotBlank(message = "Phone number is mandatory")
    private String phoneNumber;

    @OneToMany(mappedBy = "customer")
    private Set<Invoice> invoices;

    public Customer() {
    }

    public Customer(Long id, String firstName, String lastName, String address, String phoneNumber, ZonedDateTime createdDate) {
        super(id, createdDate);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Customer(String firstName, String lastName, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
