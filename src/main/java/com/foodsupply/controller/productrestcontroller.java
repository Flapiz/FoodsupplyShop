package com.foodsupply.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;


import com.foodsupply.model.CartItem;
import com.foodsupply.model.Product;
import com.foodsupply.model.ShoppingCart;
import com.foodsupply.model.Users;
import com.foodsupply.repository.UserRepository;
import com.foodsupply.repository.productRepository;
import com.foodsupply.service.productService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class productrestcontroller {
	
	@Autowired
	private ShoppingCart shoppingcart;
	
	@Autowired UserRepository userRepository;
	
	@Autowired productRepository productrepo;
	
	@Autowired productService productservice;

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
	
	@GetMapping("/edit/{id}")
	public String editProduct(Model model,@PathVariable("id") Long id){
		Optional<Product> product = productrepo.findById(id);
		model.addAttribute("product", product.get());
		model.addAttribute("product_id", product.get().getId());
		model.addAttribute("name", product.get().getName());
		model.addAttribute("price", product.get().getPrice());
		return "editproduct.html";
		
	}
	
	@PostMapping("edit/submit")
	public String editSubmit(@Valid @ModelAttribute("product") Product editproduct,Errors errors) {
		String EditStatus = null;
		if (errors.hasErrors()) {
			EditStatus = "Edit Product failed.";
			return "redirect:/product?errormassge=" + EditStatus;
		}else {
			productservice.editProduct(editproduct.getId(), editproduct.getName(), editproduct.getPrice());
			EditStatus = "Edit Product Success.";
			return "redirect:/product?editsuccess=" + EditStatus;
		}
	}
	
}


