package org.cart.controllers;

import java.util.List;

import org.cart.entities.Product;
import org.cart.exception.ProductNotFoundException;
import org.cart.exception.ShoppingCartException;
import org.cart.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
	@Autowired
	ProductService productService; 
	
	Logger logger = LoggerFactory.getLogger(ProductController.class);
	/**
	 * Fetch all products
	 * @return
	 * @throws ShoppingCartException
	 */
	@GetMapping
	public List<Product> list() throws ShoppingCartException{
		List<Product> products = null;
		products = productService.list(); 
		return products;
	}
	/**
	 * Fetch product by ID
	 * @param productId
	 * @return
	 * @throws ProductNotFoundException 
	 */
	@GetMapping(value = "/id/{productId}")
	public Product get(@PathVariable Long productId) throws ShoppingCartException {
		logger.info("fetching the details for productId" + productId);
		Product product = null;
		product = productService.getProductByID(productId);
		return product;
	}
	/**
	 * Fetch product by name
	 * @param productName
	 * @return
	 * @throws ProductNotFoundException 
	 */
	@GetMapping(value = "/name/{productName}")
	public Product getProductByName(@PathVariable String productName) throws ProductNotFoundException {
		logger.info("fetching the details for productName" + productName);
		Product product = null;
		try {
			product = productService.getProductByName(productName);
		} catch (Exception exp) {
			logger.error(exp.getMessage());
			throw new ProductNotFoundException();
		}
		return product;
	}
	/**
	 * Fetch products by type
	 * @param productType
	 * @return
	 * @throws ProductNotFoundException 
	 */
	@GetMapping(value = "/type/{productType}")
	public List<Product> getProductByType(@PathVariable String productType) throws ProductNotFoundException {
		logger.info("fetching the details for productType" + productType);
		List<Product> products = null;
		try {
			products = productService.getProductByType(productType);
		} catch (Exception exp) {
			logger.error(exp.getMessage());
			throw new ProductNotFoundException();
		}
		return products;
	}

}
