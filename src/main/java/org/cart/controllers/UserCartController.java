package org.cart.controllers;

import java.util.List;

import org.cart.exception.ShoppingCartException;
import org.cart.models.CartVO;
import org.cart.models.ProductVO;
import org.cart.services.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/usercart")
public class UserCartController {
	@Autowired
	CartService cartService;

	Logger logger = LoggerFactory.getLogger(UserCartController.class);

	/**
	 * Fetch the cart details for a user.
	 * 
	 * @param userId
	 * @return
	 * @throws ShoppingCartException
	 */
	@GetMapping(value = "/id/{userId}")
	public CartVO get(@PathVariable Long userId) throws ShoppingCartException {
		logger.info("fetching the details for user" + userId);
		CartVO cartVO = null;
		cartVO = cartService.getCartForUser(userId);
		return cartVO;
	}

	/**
	 * This method is used to add product into cart.
	 * 
	 * @param userId
	 * @return
	 * @throws ShoppingCartException
	 */
	@PostMapping(value = "/id/{userId}")
	public void addProductToCart(@PathVariable Long userId, @RequestBody ProductVO productVO)
			throws ShoppingCartException {
		logger.info("fetching the cart details for user to add the product" + userId + "productVO " + productVO);
		cartService.addProductToCart(userId, productVO);
	}
	
	/**
	 * This method is used to update product details into cart of the given user.
	 * 
	 * @param userId
	 * @return
	 * @throws ShoppingCartException
	 */
	@PutMapping(value = "/id/{userId}")
	public void updateProductsInCart(@PathVariable Long userId, @RequestBody List<ProductVO> productVOList)
			throws ShoppingCartException {
		logger.info("Updating the product details in the cart for user" + userId);
		cartService.createOrUpdateCart(userId, productVOList);
	}

	/**
	 * This method is used to delete product into cart.
	 * 
	 * @param userId
	 * @return
	 * @throws ShoppingCartException
	 */
	@DeleteMapping(value = "/id/{userId}")
	public void deleteProductFromCart(@PathVariable Long userId, @RequestBody List<ProductVO> productVOList)
			throws ShoppingCartException {
		logger.info("Deleting the products for user" + userId);
		cartService.deleteProductFromCart(userId, productVOList);
	}

}
