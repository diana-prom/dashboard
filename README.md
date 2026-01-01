# Nutrition Facts App

Nutrition-Facts-App is a full-stack web application that provides detailed nutrition information about foods. It uses preloaded USDA FoodData to display macronutrients, portion sizes, food categories, and calculates total calories. The backend exposes endpoints for retrieving and aggregating this information.

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
1. Browse foods or search for a specific food by name.
2. Retrieve macronutrients, portion information, and food category.
3. View calories per macronutrient and total calories calculated automatically.
4. Use backend endpoints programmatically if needed.

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
