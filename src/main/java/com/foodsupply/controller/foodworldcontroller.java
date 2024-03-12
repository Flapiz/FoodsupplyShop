package com.foodsupply.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.foodsupply.model.CartItem;
import com.foodsupply.model.Product;
import com.foodsupply.model.ShoppingCart;
import com.foodsupply.model.Users;
import com.foodsupply.repository.UserRepository;
import com.foodsupply.repository.productRepository;
import com.foodsupply.service.UserService;
import com.foodsupply.service.productService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class foodworldcontroller {
	
	@Autowired productService productservice;
	
	@Autowired ShoppingCart shoppingcart;
	
	@Autowired productRepository productrepo;
	
	@Autowired UserService userservice;
	
	@Autowired UserRepository userrepo;
	
	
	@RequestMapping(value = { "", "/", "/home" }, method = {RequestMethod.GET})
	public String DisplayHome(Model model) {
		model.addAttribute("username", "Sippakorn");
		return "home.html";
	}
	
	@RequestMapping("/product")
	public String DisplayProduct(Model model,@RequestParam(value = "success", required = false) String success,
			@RequestParam(value = "productAdded", required = false) String productAdded,
			@RequestParam(value = "quantityAdded", required = false) String quantityAdded,
			@RequestParam(value = "editsuccess", required = false) String editsuccess,
			@RequestParam(value = "errormassge", required = false) String errormassege) {
		String addProduct = null;
		String addQuantity = null;
		String successedit = null;
		String Errormassege = errormassege;
		
		List<Product> products = productservice.getProduct();
		CartItem cart = new CartItem();
		if(success != null){
			addProduct = productAdded;
			addQuantity = quantityAdded;
		}if(editsuccess != null) {
			successedit = editsuccess;
		}if(errormassege != null) {
			Errormassege = errormassege;
		}
		model.addAttribute("Addproduct", addProduct );
		model.addAttribute("Addquantity", addQuantity );
		model.addAttribute("editStatus", successedit);
		model.addAttribute("products", products);
		model.addAttribute("errormassege", Errormassege);
		model.addAttribute("cart", cart);
		return "product.html";
	}
	
	@RequestMapping("/cart")
	public String DisplayCart(Model model,Authentication authentication, HttpSession session,
			@RequestParam(value = "success", required = false) String success,
			@RequestParam(value = "fail", required =  false) String fail){
		Users user = userrepo.readByUsername(authentication.getName());
		double totalprice = shoppingcart.CalTotal(shoppingcart.getItems());
		String messageSuccess = null;
		String messagefail = null;
		if(success != null) {
			messageSuccess = success;
		}if(fail != null){
			messagefail = fail;
		}
		model.addAttribute("successmessage", messageSuccess);
		model.addAttribute("failmessage", messagefail);
		model.addAttribute("username", user.getUsername());
		model.addAttribute("cash", user.getCashBalance());
		model.addAttribute("totalprice", totalprice);
		model.addAttribute("carts", shoppingcart.getItems());
		session.setAttribute("loggedInPerson", user);
		return "cart.html";
	}
	
	@RequestMapping(value = "/register", method = { RequestMethod.GET })
	public String registerPage(Model model) {
		model.addAttribute("users", new Users());
		return "register.html";
	}
	
	@RequestMapping(value = "/creatUser",method = {RequestMethod.POST})
	public String creatUser(@Valid @ModelAttribute("users") Users user, Errors errors) {
		if(errors.hasErrors()) {
			return "register.html";
		}
		boolean isSaved = userservice.createNewUser(user);
		if(isSaved) {
			return "redirect:/login?register=true";
		}else {
			return "register.html";
		}
		}
	
	
	
	@RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
	public String loginPageModel(@RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
			@RequestParam(value = "register", required = false) String register,Model model) {
		
		  String errorMessage = null;
	        if(error != null) {
	            errorMessage = "Username or Password is incorrect !!";
	        } else if(logout != null) {
	            errorMessage = "You have been successfully logged out !!";
	        } else if(register != null) {
	            errorMessage = "You registration successful";
	        }
	        
	        model.addAttribute("errorMessage", errorMessage);
		
		return "login.html";
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout=true";
    }
	
	@RequestMapping(value = "/addcash", method = RequestMethod.GET)
	public String CashBalance(Model model,Authentication authentication) {
		Users user = userrepo.readByUsername(authentication.getName());
		model.addAttribute("user", user);
		return "addcash.html";
	}
	
	@PostMapping("/addcash/confirm")
	public String addCash(Model model,@RequestParam("Addedcash") double addedcash,Authentication authentication,HttpSession session) {
		if(addedcash == 0) {
			return "redirect:/cart?fail=" + "Fail to add Money!!";
		}
		Users user = userrepo.readByUsername(authentication.getName());
		boolean addsuccess = userservice.AddedMoney(user, addedcash);
		if(addsuccess){
			return "redirect:/cart?success=" + "Add Money Success";
		}else {
			return "redirect:/cart?fail=" + "Fail to add Money!!";
		}
	}
	
	@RequestMapping(value = "/addproduct", method = RequestMethod.GET)
	public String addproduct(Model model,@RequestParam(value = "massege", required = false) String massege) {
		model.addAttribute("product", new Product());
		model.addAttribute("massege", massege);
		return "addproduct.html";
	}
	
	@PostMapping(value = "/add/product")
	public String productadded(Model model,@Valid @ModelAttribute(value = "product") Product product,Errors errors) {
		String productadded = null;
		if (errors.hasErrors()) {
			productadded = "Invalid Name or Price";
			return "redirect:/addproduct?massege=" + productadded;
		}
		productadded = productservice.addProduct(product);
		return "redirect:/addproduct?massege=" + productadded;
	}
	
}
