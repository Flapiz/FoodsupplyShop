package com.foodsupply.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;


import com.foodsupply.model.CartItem;
import com.foodsupply.model.ShoppingCart;
import com.foodsupply.model.Users;
import com.foodsupply.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class productrestcontroller {
	
	@Autowired
	private ShoppingCart shoppingcart;
	
	@Autowired UserRepository userRepository;

	@PostMapping("/add/cart")
	public String addCart(@RequestParam("product_id") Long product_id,
			@RequestParam("quantity") int quantity,@RequestParam("product_name") String name,
			@RequestParam("product_price") double price) {
		if(shoppingcart.getItems().isEmpty() || !shoppingcart.containProduct(shoppingcart.getItems(), product_id)) {
			CartItem item = new CartItem(product_id, name, price, quantity);
			shoppingcart.addProduct(item);
		}
		else {
			for(CartItem cart : shoppingcart.getItems()) {
				if(cart.getProduct_id() == product_id) {
					cart.setQuantity(cart.getQuantity() + quantity);
				}
			}
			
		}
		return "redirect:/product?success=true&productAdded=" + name + "&quantityAdded=" + quantity;
	}
	
	@GetMapping("/confirm")
	public String confirmOrder(Model model,Authentication authentication,HttpSession session) {
		Users user = userRepository.readByUsername(authentication.getName());
		double cashBalance = user.getCashBalance();
		double totalPrice = shoppingcart.CalTotal(shoppingcart.getItems());
		if(cashBalance >= totalPrice){
			user.setCashBalance(cashBalance - totalPrice);
			userRepository.save(user);
			shoppingcart.SuccessShop(shoppingcart.getItems());
			return "redirect:/cart?success=" +  "Order Confirm Purchase Success";
		}else {
			return "redirect:/cart?fail=" + "Can't Confirm Order. Please fill cashBalance";
		}
	}
	
	@GetMapping("/delete/cart")
	public String deleteProduct(@RequestParam("productId") Long productId){
		shoppingcart.removeProduct(productId);
		return "redirect:/cart";
	}
}


