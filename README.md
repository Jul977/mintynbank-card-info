# MintynBank Card Info Service

## Overview and Project Summary

We want to give customers the best experience possible. So we should inform
them about the details of his/her card like:
- valid/not valid
- the scheme (i.e. VISA, MASTERCARD or AMEX)
- the bank when it is available.

## Features
-  A customer can register an account
-  A customer can login and receive an access token which is used to authenticate other endpoints.
-  A customer can transfer verify his card details.
-  The microservice is able to handle the rate limiting.

## Tools & Technologies used for this project
- Java (JDK version 17)
- SpringBoot (version 2.6.2)
- Maven
- JPA and Hibernate
- MySQL Databasee (version 8.0)
- JUnit
- Postman

## Postman Documentation

- [Click Here](https://documenter.getpostman.com/view/23777914/2s9YsDjEqv)


## API Endpoints
| HTTP Verbs | Endpoints | Action |
| --- | --- | --- |
| POST | http://localhost:8080/api/v1/customer/registration | Register a new customer |
| POST | http://localhost:8080/api/v1/customer/login | Login customer |
| GET | http://localhost:8080/api/v1/card-scheme/verify/{cardBin} | Card Lookup |
