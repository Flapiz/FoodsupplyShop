package com.foodsupply.service;

import java.util.List;


import com.foodsupply.model.CartItem;
import com.foodsupply.model.Product;
import com.foodsupply.model.Users;

public interface productService {

	public void putCart(CartItem cart);
	
	List<Product> getProduct();
	
}
