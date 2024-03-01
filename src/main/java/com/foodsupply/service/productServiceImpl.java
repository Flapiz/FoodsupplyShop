package com.foodsupply.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodsupply.model.CartItem;
import com.foodsupply.model.Product;
import com.foodsupply.model.ShoppingCart;
import com.foodsupply.repository.productRepository;

@Service
public class productServiceImpl implements productService {

	@Autowired ShoppingCart shoppingcart;
	
	@Autowired productRepository productRepo;
	
	@Override
	public void putCart(CartItem cart) {
		shoppingcart.addProduct(cart);	
	}

	@Override
	public List<Product> getProduct() {
		return productRepo.findAll();
	}

}
