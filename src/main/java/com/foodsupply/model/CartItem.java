package com.foodsupply.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem{
	private Long Product_id;
	private String product_name;
	private Double price;
	private int quantity;
}
