package com.foodsupply.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodsupply.model.Users;



public interface UserRepository extends JpaRepository<Users, Long>{

	Users readByUsername(String username);
}
