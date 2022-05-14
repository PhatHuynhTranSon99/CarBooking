package com.transonphat.carbooking.domain;


import java.time.ZonedDateTime;

/**
 * Author: Tran Son Phat
 * Apply the Builder design pattern to create complex car object
 */
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

    public CarBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public CarBuilder setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
        return this;
    }

    public CarBuilder setMake(String make) {
        this.make = make;
        return this;
    }

    public CarBuilder setModel(String model) {
        this.model = model;
        return this;
    }

    public CarBuilder setColor(String color) {
        this.color = color;
        return this;
    }

    public CarBuilder setConvertible(Boolean convertible) {
        isConvertible = convertible;
        return this;
    }

    public CarBuilder setRating(Double rating) {
        this.rating = rating;
        return this;
    }

    public CarBuilder setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
        return this;
    }

    public CarBuilder setRate(Double rate) {
        this.rate = rate;
        return this;
    }

    public Car build() {
        Car car = new Car();

        car.setId(id);
        car.setIdentificationNumber(identificationNumber);
        car.setMake(make);
        car.setModel(model);
        car.setColor(color);
        car.setIsConvertible(isConvertible);
        car.setRating(rating);
        car.setLicensePlate(licensePlate);
        car.setRate(rate);
        car.setCreatedDate(ZonedDateTime.now());

        return car;
    }
}
