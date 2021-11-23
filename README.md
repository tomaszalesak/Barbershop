# Barbershop

## Description

Your task is to create an information system supporting the barbershop. For one, the application should keep record of
customers (their name, surname, address, and phone). The barbershop employees are also kept in the system. The employees
have the same attributes as the customers with the addition of their salary. The customers can enjoy different
services (e.g., haircut, washing), and they can post their feedback after the service has been provided. The system
should keep track of the performed service, the duration and price.

![Use case diagram](https://github.com/tomaszalesak/Barbershop/blob/main/doc/BarbershopCD.svg)

![Class diagram](https://github.com/tomaszalesak/Barbershop/blob/main/doc/BarbershopCD.svg)

## REST API

### How to use:

1. Compile: `mvn clean install`  
2. Start web application with rest: `cd barbershop-rest-react && mvn spring-boot:run`  


* Documentation for REST API is available on SWAGGER UI: http://localhost:8080/pa165/rest/swagger-ui/

## Barbershop frontend implemented in React

### Prerequisites

You should have Node.js installed on your machine.

Installation instructions can be found here [https://nodejs.org/](https://nodejs.org/en/).

### Available Scripts

In the React project directory `barbershop-react`, you can run:

#### `yarn install`

Installs dependecies to working folder. You need to install dependencies only before the first start.

If you do not have `yarn` in path install it via `npm install --global yarn`.

#### `yarn start`

It fetches data from REST API, so you need to have it running.

Runs the app in the development mode.\
Open [http://localhost:3000](http://localhost:3000) to view it in the browser.

The page will reload if you make edits.\
You will also see any lint errors in the console.

#### `yarn build`

Builds the app for production to the `build` folder.\
It correctly bundles React in production mode and optimizes the build for the best performance.


