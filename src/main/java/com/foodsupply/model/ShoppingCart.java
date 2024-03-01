package com.foodsupply.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart {
	private List<CartItem> items = new ArrayList<>();
	
	public void addProduct(CartItem product) {
		items.add(product);
	}
	
	public void removeProduct(Long id) {
		for(CartItem item:getItems()) {
			if(id == item.getProduct_id()) {
				items.remove(item);
				break;
			}
		}
	}
	
	public boolean containProduct(List<CartItem> items, Long productId) {
		return items.stream().anyMatch(item -> item.getProduct_id() == productId);
	}
	
	public Double CalTotal(List<CartItem> items) {
		double sumPrice = 0;
		for(CartItem cart: items) {
			sumPrice = sumPrice + (cart.getQuantity() * cart.getPrice());
		}
		return sumPrice;
	}
	
	public void SuccessShop(List<CartItem> items) {
		items.removeAll(getItems());
	}
}
