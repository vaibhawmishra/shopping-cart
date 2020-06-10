/**
 * Web controller to take the input from the web page
 */
package org.cart.controllers;

import org.cart.exception.ShoppingCartException;
import org.cart.models.ProductVO;
import org.cart.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductWebController {
	
	@Autowired
	ProductService productService; 
	
	@GetMapping("/product")
	  public String loadProductForm(Model model) {
	    model.addAttribute("product", new ProductVO());
	    return "product";
	  }
	
	  @PostMapping("/product")
	  public String productSubmit(@ModelAttribute ProductVO productVO) throws ShoppingCartException {
		productService.addProduct(productVO);
	    return "result";
	  }
}
