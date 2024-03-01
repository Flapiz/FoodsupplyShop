package com.foodsupply.validations;

import java.util.Arrays;
import java.util.List;
import com.foodsupply.annotaion.PasswordValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordStrengthValidator implements ConstraintValidator<PasswordValidator, String>{

	List<String> weakPasswords;
	
	@Override
	public void initialize(PasswordValidator passwordValidator) {
		weakPasswords = Arrays.asList("12345", "password", "qwerty");
	}

	
	@Override
	public boolean isValid(String passwordField, ConstraintValidatorContext cxt) {
		return passwordField != null && (!weakPasswords.contains(passwordField));
	}

	
	
}
