package com.foodsupply.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodsupply.model.CartItem;
import com.foodsupply.model.Product;
import com.foodsupply.model.ShoppingCart;
import com.foodsupply.model.Users;
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

	@Override
	public String addProduct(Product product) {
		String massege = null;
		if(product != null) {
			productRepo.save(product);
			massege = "Add " + product.getName() + ".price is " + product.getPrice();
		}else {
			massege = "Fail to add product. Try Again!!";
		}
		return massege;
	}

	@Override
	public void editProduct(Long id, String productname, double productprice) {
		Optional<Product> productFromId = productRepo.findById(id);
		Product product = productFromId.get();
		if(product.getId() > 0 || product != null) {
			product.setName(productname);
			product.setPrice(productprice);
			productRepo.save(product);
		}
		
	}

}
