# A simple REST api for car booking

## Overview
This project is a simple REST api which simulates a car booking agency and has the following functionalities:
- Create, read, *update* and delete cars.
- Find cars by color, is convertible or not, identification plate, make and model.
- Create, read, *update* and delete customers.
- Find customers by address, phone and name
- Create, read, *update* and delete drivers.
- Find drivers by name and phone.
- Create and remove bookings.
- Find bookings and invoices by date.
- Statistics: Find revenue from a customer in a period
- Statistics: Find revenue from a driver in a period
- Statistics: Find usage of all cars (number of days used) in a month

## Endpoints 
### Cars
####View car list
```
GET /cars
```
Parameters:
- size: The number of cars per page
- page: Current page to display

####View a car
```
GET /cars/{carId}
```
Path variables:
- carId: Integer, the id of the car

####Delete a car
```
DELETE /cars/{carId}
```
Path variables:
- carId: Integer, the id of the car

#### Create a car
```
POST /cars
```
Request body
```
{
    "identificationNumber": String,
    "make": String,
    "model": String,
    "color": String,
    "isConvertible": boolean,
    "rating": Double,
    "licensePlate": String,
    "rate": Double (Per kilometer)
}
```
#### Update car
**To be implemented**

####Search a car
```
GET /cars/search
```
Parameters:
- identification: String
- color: String
- make: String
- model: String
- convertible: Boolean
- page: Integer, current page
- size: Integer, the number of cars per page

###Driver
#### View all drivers
```
GET /drivers
```
Parameters:
- size: Integer, The number of drivers per page
- page: Integer, Current page

#### View a driver by id
```
GET /drivers/{driverId}
```
Path variables:
- driverId: Integer, The id of the driver

#### Search a driver
```
GET /drivers/search
```
Parameters:
- name: String, The name of the driver
- phone: String, The phone of the customer
- page: Integer, current page
- size: Integer, the number of cars per page

#### Create a driver
```
POST /drivers
```
Request body
```
{
    "firstName": String,
    "lastName": String,
    "phoneNumber": String,
    "rating": Double
}
```

#### Update a driver
**To be implemented**

#### Delete a driver
```
DELETE /drivers/{driverId}
```
Path variables:
- driverId: Integer, the name of the driver

### Customer

#### View all customers
```
GET /customers
```
Path variables: 
- size: Integer, number of element per page
- page: Integer, current page

#### View a customer by id
```
GET /customers/{customerId}
```
Path variable: 
-  customerId: Integer, the id of the customer

#### Search a customer
```
GET /customers/search
```
Parameters:
- name: String
- phone: String
- address: String
- page: Integer, current page
- size: Integer, number of customers per page

#### Create a customer
```
POST /customers
```
Request body
```
{
    "firstName": String,
    "lastName": String,
    "address": String,
    "phoneNumber": String
}
```

#### Delete a customer
```
DELETE /customers/{customerId}
```
Path variable:
- customerId: Integer, the id of the customer

#### Update a customer
**To be implemented**

## Technologies