package com.foodsupply.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.foodsupply.constants.FoodSupplyConstants;
import com.foodsupply.model.Roles;
import com.foodsupply.model.Users;
import com.foodsupply.repository.RolesRepository;
import com.foodsupply.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RolesRepository rolesRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public boolean createNewUser(Users user) {
		boolean isSaved = false;
		Roles role = rolesRepository.getByRoleName(FoodSupplyConstants.USER_ROLE);
		user.setRoles(role);
		user.setPwd(passwordEncoder.encode(user.getPwd()));
		user.setCashBalance(0);
		user = userRepository.save(user);
		if(null != user && user.getUserId() > 0) {
			
			isSaved = true;
		}
		return isSaved;
	}
	
	public boolean AddedMoney(Users user, double cash) {
		boolean isAdded = false;
		user.setCashBalance(user.getCashBalance() + cash);
		userRepository.save(user);
		if(null != user && user.getUserId() > 0) {
			
			isAdded = true;
		}
		return isAdded;
	}
	
}
