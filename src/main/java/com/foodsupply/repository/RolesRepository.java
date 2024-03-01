package com.foodsupply.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodsupply.model.Roles;

public interface RolesRepository extends JpaRepository<Roles, Integer>{

	Roles getByRoleName(String roleName);
	
}
