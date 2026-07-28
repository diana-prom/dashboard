# Nutrition Facts App

Nutrition Facts App is a full-stack web application that provides nutrition information using USDA FoodData Central. The application retrieves and aggregates food data through REST APIs to display macronutrients, portion sizes, food categories, and calculated calorie values.

## Application Preview
<img width="889" height="520" alt="Screenshot 2026-07-28 at 2 15 33 AM" src="https://github.com/user-attachments/assets/d2224ef4-ef56-4297-96f7-f06c7c5858f9" />

Users can search foods and view nutrition information including macronutrients, serving sizes, categories, and calculated calorie values.

# Table of Contents
* [Features](#features)
* [Tech Stack](#tech-stack)
* [Getting Started](#getting-started)
* [Usage](#usage)
* [API](#api)
* [License](#license)

# Features
- Search foods by name and retrieve detailed nutrition info
- View macronutrients: protein, carbohydrates, fats
- Display portion sizes and measurement units
- Categorize foods using category codes
- Calculate total calories automatically
- Aggregates data from local database tables (food, food_portion, measure_unit, category)
- Full-stack web frontend for browsing and searching foods
- Dockerized for easy local setup and deployment

# Tech Stack
- Frontend: HTML, CSS, JavaScript (served via Nginx)
- Backend: Java Spring Boot with JPA/Hibernate 
- Database: MySQL
- Docker: Containers for frontend (Nginx), backend (Spring Boot), and database
- Data Source: USDA FoodData Central (preloaded in database)

## Architecture

The application follows a full-stack architecture:

**Frontend**
- JavaScript/React interface for searching and displaying nutrition information.

**Backend**
- Spring Boot REST API for retrieving and aggregating nutrition data.

**Data Layer**
- JPA/Hibernate manages object-relational mapping with the MySQL database.

# Getting Started
   - Clone the repository
   - Build and run using Docker Compose
       - ```docker compose up --build -d```
This will spin up three containers:
   - ```nutrition-facts-db``` → MySQL database
   - ```nutrition-facts-backend``` → Backend API
   - ```nutrition-facts-frontend``` → Frontend React app
Access the app
   - Frontend: http://localhost:3000
   - Backend API: http://localhost:8080
   - MySQL database: localhost:3306, user: root, password: ******** 

# Usage
- Search for foods by name and retrieve detailed nutrition information.
- View macronutrients, portion sizes, food categories, and calorie calculations..
- See automatically calculated calories based on protein, fat, and carbohydrate values.
- Access backend REST endpoints directly for programmatic use.

# API
- The backend exposes REST endpoints serving aggregated data from the database.
    - ***Food Endpoints***
        - ***Get all foods***
             ```http
             GET /api/food
             ```
             Returns a list of all foods.
          
        - ***Get food by FDC ID***
             ```http
             GET /api/food/{fdcId}
             ```
             Returns detailed information for a single food.
          
       - ***Search best food by description***
            ```http
            GET /api/food/search/best?name={foodName}
            ```
            Returns the best match for a food description, including food category ID.
         
   - ***Food Portion Endpoint***
        - ***Get portion info for a food***
            ```http
            GET /api/food/portion?fdc_id={fdcId}
            ```
            Returns aggregated portion info and measurement units.


    - ***Category Endpoint***
        - ***Get category by code***
            ```http
            GET /api/category?code={categoryCode}
            ```
            Returns category details for the given code.             

# License

This project is licensed under the [MIT License](https://github.com/diana-prom/nutrition-facts-app/blob/main/LICENSE).
