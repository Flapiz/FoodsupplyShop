# Food Supply Shop Project  
#### this project is a shopping web application for purchasing ingredients to cook a food. I made this project by used Java programming language integrate with Spring Boot framework and MySQL database for storage data that are used in web application. Users must register and add cash for purchasing products. Products have a variouse ingredients for cooking. There is role for administrator in web application to add products and edit products name and price in case products have changed.
#### Video Demo:  <https://youtu.be/aXIVedqaH_Q> 
## Components
- HTML
- CSS
- Bootstrap 5
- Thymeleaf
- Java 21
- Spring Boot
- Spring MVC
- Spring Security
- Spring Data JPA
- Spring Validation
- MySQL Database
## Features
- Users
    - Register on web application 
    - Login
    - Add Cash
    - View Product and Price
    - Purchasing products
    - Log out
- Admin
    - Login
    - Add products
    - Add cash
    - View product and price
    - Edit products
    - Purchasing products
    - Log out
## Tables
you can view how to create database and schema at **foodsupply_schema.sql** in resources folder.
- Products
  - product_id
  - name
  - price
- Users
    - user_id
    - cash_balance
    - email
    - username
    - pwd (password)
    - role_id
- Roles
    - role_id
    - role_name
## Configuration Application.properties for Database
you can configured for your database by replace url, username and password
``` properties
spring.datasource.url = jdbc:mysql://localhost:3306/supplyfood # Replace your database URL
spring.datasource.username = root # Replace your username for database
spring.datasource.password = 04D12m97Y # Replace your password for database

spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto = update # update schema if necessary

spring.jpa.properties.javax.persistence.validation.mode = none

```
In case use other relational database. you can replace dependency MySQL driver to other driver.
``` xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```
## Documentation
- <https://getbootstrap.com/docs/5.0/getting-started/introduction>
- <https://spring.io/projects/spring-boot>
- <https://www.thymeleaf.org>
- <https://www.w3schools.com/sql>
