# A simple REST api for car booking

## Overview
This project is a simple REST api which simulates a car booking agency and has the following functionalities:
- Create, read, update and delete cars.
- Find cars by color, is convertible or not, identification plate, make and model.
- Create, read, update and delete customers.
- Find customers by address, phone and name
- Create, read, update and delete drivers.
- Find drivers by name and phone.
- Create and remove bookings.
- Find bookings and invoices by date.
- Statistics: Find revenue from a customer in a period
- Statistics: Find revenue from a driver in a period
- Statistics: Find usage of all cars (number of days used) in a month

## Endpoints

**IMPORTANT NOTE**: The Date time format of this API follows ISO standards, for example:
```
2019-12-01T00:00:00.000%2B07:00
```
This is date 01/12/2019 0 hours, 0 minutes, 0 seconds, 0 milliseconds, UTC+7 (Vietnam) time.

**NOTE**: Use %2B to signify '+' in the query parameter or it won't work.

### Interactive GUI
All endpoints can be accessed and experimented with using the GUI available at
```
GET /swagger-ui/index.html
```

### Cars

#### Find all cars
```
GET /cars
```
Parameters:
- size: The number of cars per page
- page: Current page to display
- identification: String
- color: String
- make: String
- model: String
- convertible: Boolean
- page: Integer, current page
- size: Integer, the number of cars per page

Note: if search parameters are missing, the endpoint will retrieve all cars.

#### View a car
```
GET /cars/{carId}
```
Path variables:
- carId: Integer, the id of the car

#### Delete a car
```
DELETE /cars/{carId}
```
Path variables:
- carId: Integer, the id of the car

**NOTE**: Deleting a car also deletes related bookings.

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
```
PUT /cars/{carId}
```
Path variables:
- carId: Integer, the id of the car

Parameters:
- identification: String
- make: String
- model: String
- color: String
- convertible: boolean
- rating: Double
- licensePlate: String
- rate: Double (Per kilometer)

### Driver

#### View all drivers
```
GET /drivers
```
Parameters:
- name: String, The name of the driver
- phone: String, The phone of the customer
- size: Integer, The number of drivers per page
- page: Integer, Current page

Note: if search parameters are missing, the endpoint will retrieve all drivers.

#### View a driver by id
```
GET /drivers/{driverId}
```
Path variables:
- driverId: Integer, The id of the driver

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
    "ratings": Double
}
```

#### Update a driver
```
PUT /drivers/{driverId}
```
Path variables:
- driverId: Integer, the name of the driver

Parameters:
- firstName: String
- lastName: String
- phone: String
- ratings: Double

#### Delete a driver
```
DELETE /drivers/{driverId}
```
Path variables:
- driverId: Integer, the name of the driver

**NOTE**: Deleting a driver also deletes any related bookings

### Customer

#### View all customers
```
GET /customers
```
Parameters: 
- name: String
- phone: String
- address: String
- size: Integer, number of element per page
- page: Integer, current page

Note: if search parameters are missing, the endpoint will retrieve all customers.

#### View a customer by id
```
GET /customers/{customerId}
```
Path variable: 
-  customerId: Integer, the id of the customer

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

**NOTE**: Deleting a customer also deletes any related bookings.

#### Update a customer
```
PUT /customers/{customerId}
```
Path variable:
- customerId: Integer, the id of the customer

Parameters:
- firstName: String
- lastName: String
- phone: String
- address: String

### Car allocation

#### Allocate car to driver
```
POST /cars/{carId}/driver/{driverId}
```
Path variables:
- carId: Integer, the id of the car.
- driverId: Integer, the id of the driver.

#### Remove car from driver
```
DELETE /cars/{carId}/driver
```
Path variables:
- carId: Integer, the id of the car

**NOTE**: Remove car from driver also remove any related bookings. 

### Booking

#### Find available cars
Find all cars that are available during a time period
```
GET /bookings/cars
```
Parameters:
- start: String in ISO Date time format, the start of the period
- end: String in ISO Date time format, the end of the period
- page: Integer, current page
- size: Integer, the number of items in a page

Example: 
```
GET /booking/cars?start=2020-01-06T00:00:00.000%2B07:00&end=2020-01-09T00:00:00.000%2B07:00
```

#### Create car booking
```
POST /customers/{customerId}/bookings
```
Path variables:
- customerId: The id of the customer

Parameters:
- startingLocation: String
- endLocation: String
- startTime: String, in ISO Date time format
- endTime: String, in ISO Date time format
- distance: Double (kilometers)
- carId: Integer, the id of the car

#### Delete car booking
```
DELETE /bookings/{bookingId}
```
Path variables:
- bookingId: Integer, the id of the booking

**NOTE**: Deleting a booking also deletes its associated invoice.

#### Filter bookings by start and end date
Find all bookings that have start and end time contained in a period
```
GET /bookings
```
Parameters:
- from: String, in ISO DateTime format, the start of the period
- to: String, in ISO DateTime format, the end of the period
- page: Integer, the current page
- size: Integer, the number of items per page

### Invoices
Find all invoices with filtering
```
GET /invoices
```
Parameters:
- from: String, in ISO DateTime format, the start of the period
- to: String, in ISO DateTime format, the start of the period
- customer: Integer, the id of the customer
- driver: Integer, the id of the driver
- page: Integer, the current page
- size: Integer, the number of items per page

Note: If no parameters are present, this endpoint will retrieve all invoices

### Statistics

#### Get revenue from a customer in a period
```
GET /statistics/revenue/customer
```
Parameters:
- from: String, in ISO DateTime format, the start of the period
- to: String, in ISO DateTime format, the start of the period
- customer: Integer, the id of the customer

#### Get revenue from a driver in a period
```
GET /statistics/revenue/driver
```
Parameters:
- from: String, in ISO DateTime format, the start of the period
- to: String, in ISO DateTime format, the start of the period
- driver: Integer, the id of the driver

#### Find usage of all cars
Get the number of days used for each car in a month
```
GET /statistics/usage
```
Parameters:
- month: Integer
- year: Integer
- page: Integer, the current page
- size: Integer, the number of items per page

## How to run the local version

### Step 1: Install and run MySQL service on your machine
### Step 2.1: Create root database 
Firstly, you need to connect to MySQL Server on your machine and run this following script
```sql
DROP DATABASE IF EXISTS cardb;
DROP USER IF EXISTS `caradmin`@`%`;
DROP USER IF EXISTS `caruser`@`%`;
CREATE DATABASE IF NOT EXISTS cardb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS `caradmin`@`%` IDENTIFIED WITH mysql_native_password BY 'password';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, REFERENCES, INDEX, ALTER, EXECUTE, CREATE VIEW, SHOW VIEW,
CREATE ROUTINE, ALTER ROUTINE, EVENT, TRIGGER ON `cardb`.* TO `caradmin`@`%`;
CREATE USER IF NOT EXISTS `caruser`@`%` IDENTIFIED WITH mysql_native_password BY 'password';
GRANT SELECT, INSERT, UPDATE, DELETE, SHOW VIEW ON `cardb`.* TO `caruser`@`%`;
FLUSH PRIVILEGES;
```
This will create the database and also two users, caradmin, caruser with special privileges.

### Step 2.2: Create test database
Secondly, if you want to run the unit tests, you should also run the following scripts before running the tests.
```sql
DROP DATABASE IF EXISTS testcardb;
DROP USER IF EXISTS `testcaradmin`@`%`;
DROP USER IF EXISTS `testcaruser`@`%`;
CREATE DATABASE IF NOT EXISTS testcardb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS `testcaradmin`@`%` IDENTIFIED WITH mysql_native_password BY 'password';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, REFERENCES, INDEX, ALTER, EXECUTE, CREATE VIEW, SHOW VIEW,
    CREATE ROUTINE, ALTER ROUTINE, EVENT, TRIGGER ON `testcardb`.* TO `testcaradmin`@`%`;
CREATE USER IF NOT EXISTS `testcaruser`@`%` IDENTIFIED WITH mysql_native_password BY 'password';
GRANT SELECT, INSERT, UPDATE, DELETE, SHOW VIEW ON `testcardb`.* TO `testcaruser`@`%`;
FLUSH PRIVILEGES;
```
### Step 3: Open the repository using IntelliJ
### Step 4: Run the project