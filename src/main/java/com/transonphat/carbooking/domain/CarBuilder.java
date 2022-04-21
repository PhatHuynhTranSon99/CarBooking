package com.transonphat.carbooking.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class CarBuilder {
    private Long id;
    private String identificationNumber;
    private String make;
    private String model;
    private String color;
    private Boolean isConvertible;
    private Double rating;
    private String licensePlate;
    private Double rate;

    public void setId(Long id) {
        this.id = id;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setConvertible(Boolean convertible) {
        isConvertible = convertible;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Car build() {
        Car car = new Car();

        car.setId(id);
        car.setIdentificationNumber(identificationNumber);
        car.setMake(make);
        car.setModel(model);
        car.setColor(color);
        car.setConvertible(isConvertible);
        car.setRating(rating);
        car.setLicensePlate(licensePlate);
        car.setRate(rate);

        return car;
    }
}
