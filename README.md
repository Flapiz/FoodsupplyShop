# Food Supply Shop Project  
#### this project is a shopping web application for purchasing ingredients to cook a food. I made this project by used Java programming language integrate with Spring Boot framework and MySQL database for storage data that are used in web application. Users must register and add cash for purchasing products. Products have a variouse ingredients for cooking. There is role for administrator in web application to add products and edit products name and price in case products have changed.
#### Video Demo:  <https://youtu.be/aXIVedqaH_Q> 
## Components
- HTML : markup language used to create web pages.
- CSS : used to styling web page.
- Bootstrap 5 : provides pre-built layouts.
- Thymeleaf : modern server-side java template engin for web.
- Java 21 : main programming language.
- Spring Boot : provide a set of tools and conventions for quickly building applications with minimal configuration.
- Spring MVC : web framework for building web applications in java.
- Spring Security : provides comprehensive security features to secure web application.
- Spring Data JPA : provides simplified programming model for working with relational database.
- Spring Validation : used to validating data input in java applications.
- MySQL Database : relational database is used for storage information.
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
## Configuration Security
This project have security part that use for implement authentication for user and admin role. By configured at **ProjectSecurityConfig.java** as demonstrate below.
``` java
package com.foodsupply.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {
	
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{
		
		http.csrf((csrf) -> csrf.ignoringRequestMatchers("/creatUser") // configure Cross-Site Request Forgery attack prevention.
				.ignoringRequestMatchers("/add/cart").ignoringRequestMatchers("/addcash")
				.ignoringRequestMatchers("/addcash/confirm").ignoringRequestMatchers("/add/product")
				.ignoringRequestMatchers("/addproduct").ignoringRequestMatchers("/edit/submit")
				.ignoringRequestMatchers("/edit/submit")
				.ignoringRequestMatchers("/asset/**"))
		.authorizeHttpRequests(requests -> requests.requestMatchers("/home").permitAll() // configure authorization every path on web application.
				.requestMatchers("/").permitAll()
				.requestMatchers("/product").permitAll()
				.requestMatchers("/cart").authenticated()
				.requestMatchers("/add/cart").authenticated()
				.requestMatchers("/delete/cart").authenticated()
				.requestMatchers("/addcash/confirm").authenticated()
				.requestMatchers("/addcash").authenticated()
				.requestMatchers("/confirm").authenticated()
				.requestMatchers("/addproduct").hasRole("ADMIN")
				.requestMatchers("/add/product").hasRole("ADMIN")
				.requestMatchers("/edit/**").hasRole("ADMIN")
				.requestMatchers("/login").permitAll()
				.requestMatchers("/logout").permitAll()
				.requestMatchers("/register").permitAll()
				.requestMatchers("/creatUser").permitAll()
				.requestMatchers("/asset/**").permitAll())
		.formLogin(loginConfigurer -> loginConfigurer.loginPage("/login") // configure path for Log in page
				.defaultSuccessUrl("/cart") // if successful go to /cart path
				.failureUrl("/login?error=true").permitAll()) // if fail go to /login path and send error param true.
		.logout(logoutConfigurer -> logoutConfigurer // configure log out
				.logoutSuccessUrl("/login?logout=true")  // if successful go to /login path and send logout param true.
                .invalidateHttpSession(true).permitAll())
		.httpBasic(Customizer.withDefaults());	
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
```
##  Resources Folder
All resoureces are used by web application storage in **resources** folder include HTML file that contain in template folder, CSS and images file are storaged in asset folder

## Documentation
- <https://getbootstrap.com/docs/5.0/getting-started/introduction>
- <https://spring.io/projects/spring-boot>
- <https://www.thymeleaf.org>
- <https://www.w3schools.com/sql>
