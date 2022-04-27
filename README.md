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

## Technologies