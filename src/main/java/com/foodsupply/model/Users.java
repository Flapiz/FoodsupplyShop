package com.foodsupply.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.foodsupply.annotaion.FieldsValueMatch;
import com.foodsupply.annotaion.PasswordValidator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "users")
@FieldsValueMatch.List({
	@FieldsValueMatch(
			field = "pwd",
			fieldMatch = "confirmPwd",
			message = "Passwords do not match!"
			),
	@FieldsValueMatch(
			field = "email",
			fieldMatch = "confirmEmail",
			message = "Email addresses do not match"
			)
})
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int UserId;
	
	@NotBlank(message = "Username must not be blank")
	@Size(min=3, message = "Username must be at least 3 characters long")
	private String username;
	
	private double cashBalance;
	
	@NotBlank(message = "Email must not be blank")
	@Email(message = "Please provide a valid email address")
	private String email;
	
	@NotBlank(message="Confirm Email must not be blank")
    @Email(message = "Please provide a valid confirm email address" )
    @Transient
    @JsonIgnore
    private String confirmEmail;
	
	@NotBlank(message="Password must not be blank")
    @Size(min=5, message="Password must be at least 5 characters long")
    @PasswordValidator
    @JsonIgnore
    private String pwd;
	
	@NotBlank(message = "Confirm Password must not be blank")
	@Size(min = 5, message = "Confirm Password must be at least 5 characters long")
	@Transient
	@JsonIgnore
	private String confirmPwd;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, targetEntity = Roles.class)
	@JoinColumn(name = "role_id", referencedColumnName = "roleId", nullable = false)
	private Roles roles;
	
	
}
