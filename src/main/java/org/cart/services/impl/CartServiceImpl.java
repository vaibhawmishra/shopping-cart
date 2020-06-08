package org.cart.services.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import org.cart.entities.Cart;
import org.cart.entities.CartProductMapping;
import org.cart.entities.Product;
import org.cart.exception.CartNotFoundException;
import org.cart.exception.ProductNotFoundException;
import org.cart.exception.ShoppingCartException;
import org.cart.models.CartVO;
import org.cart.models.ProductVO;
import org.cart.repositories.CartRepository;
import org.cart.repositories.ProductRepository;
import org.cart.services.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * Interface implementation for Cart related operations.
 * 
 * @author Vaibhaw
 *
 */
@Service
@Transactional
public class CartServiceImpl implements CartService {
	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ProductRepository productRepository;

	Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

	/**
	 * This method fetches the cart for any user.
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public CartVO getCartForUser(Long userId) throws ShoppingCartException {
		CartVO cartVO = null;
		Cart existingCart = null;
		float totalPrice = 0;
		float productPrice;
		try {
			existingCart = cartRepository.findByUserId(userId);
		} catch (NoResultException nre) {
			logger.error("No Cart was found for the user!" + nre.getMessage());
			throw new CartNotFoundException(nre.getMessage());
		}
		if (existingCart != null) {
			cartVO = new CartVO();
			cartVO.setCartId(existingCart.getCartId());
			cartVO.setUserId(existingCart.getUserId());
			Map<Long, ProductVO> productVOs = cartVO.getProductVOs();
			for (Map.Entry<Long, CartProductMapping> entry : existingCart.getCartProductMappings().entrySet()) {
				ProductVO productVO = new ProductVO();
				productPrice = 0;
				productVO.setProductId(entry.getValue().getProduct().getProductId());
				productVO.setProductName(entry.getValue().getProduct().getProductName());
				productVO.setQuantity(entry.getValue().getQuantity());
				productVO.setPrice(entry.getValue().getProduct().getPrice());
				productPrice = productVO.getPrice() * productVO.getQuantity();
				totalPrice = totalPrice + productPrice;
				productVOs.put(productVO.getProductId(), productVO);
			}
			cartVO.setTotalPrice(totalPrice);
		}
		return cartVO;
	}

	/**
	 * This method is used to add a product to the Cart. If the product is already
	 * in the cart then the product quantity is incremented. If the product is not
	 * found in the cart then the it is added.
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void addProductToCart(Long userId, ProductVO productVO) throws ShoppingCartException {
		boolean productFound = false;
		Cart existingCart = null;
		logger.info("adding the product in cart for user id " + userId);
		try {
			existingCart = cartRepository.findByUserId(userId);
		} catch (NoResultException nre) {
			logger.error("No Cart was found for the user!" + nre.getMessage());
		}
		logger.info("Fetching the cart" + existingCart);
		if (existingCart != null) {
			logger.info("cart is not null");
			if (!CollectionUtils.isEmpty(existingCart.getCartProductMappings())) {
				for (Map.Entry<Long, CartProductMapping> entry : existingCart.getCartProductMappings().entrySet()) {
					logger.debug("entry.getKey()" + entry.getKey());
					if (entry.getKey() == productVO.getProductId()) {
						/*
						 * If the product quantity is greater than 1 then take that value else increase
						 * the quantity by 1.
						 */
						entry.getValue()
								.setQuantity(entry.getValue().getQuantity() + productVO.getQuantity() > 1
										? productVO.getQuantity()
										: 1);
						productFound = true;
						logger.debug("productFound was true");
					}
				}
			}
		}
		if (!productFound) {
			logger.info("productFound was not found in the cart");
			CartProductMapping cartProductMapping = new CartProductMapping();
			Product product = null;
			try {
				product = productRepository.getProductByID(productVO.getProductId());
			} catch (NoResultException nre) {
				logger.error("No product found for the product id" + productVO.getProductId());
				throw new ProductNotFoundException("No product found for the product id" + productVO.getProductId());
			}
			cartProductMapping.setCart(existingCart);
			cartProductMapping.setProduct(product);
			cartProductMapping.setQuantity(productVO.getQuantity());
			existingCart.getCartProductMappings().put(productVO.getProductId(), cartProductMapping);
		}
		cartRepository.saveAndFlush(existingCart);
	}

	/**
	 * This method updates the cart with products.
	 * 
	 * @param userId
	 * @param productVOList
	 * @throws CartNotFoundException
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void createOrUpdateCart(Long userId, List<ProductVO> productVOList) throws ShoppingCartException {
		logger.info("updating the products of cart for user id " + userId);
		Cart existingCart = null;
		try {
			existingCart = cartRepository.findByUserId(userId);
		} catch (Exception exp) {
			logger.info("No Cart details found for the user, we will continue creating cart" + exp.getMessage());
		}
		logger.debug("Fetching the cart" + existingCart);

		if (existingCart != null) {
			// Iterate over the product list and update in the cart of the user
			for (ProductVO productVO : productVOList) {
				if (productVO.getQuantity() > 0) {
					CartProductMapping cartProductMapping = new CartProductMapping();
					Product product = null;
					try {
						product = productRepository.getProductByID(productVO.getProductId());
					} catch (NoResultException nre) {
						logger.error("No product found for the product id" + productVO.getProductId());
						throw new ProductNotFoundException("Invalid product" + productVO.getProductId());
					}
					cartProductMapping.setCart(existingCart);
					cartProductMapping.setProduct(product);
					cartProductMapping.setQuantity(productVO.getQuantity());
					existingCart.getCartProductMappings().put(productVO.getProductId(), cartProductMapping);
				} else if (productVO.getQuantity() == 0) {
					existingCart.getCartProductMappings().keySet().removeIf(key -> key == productVO.getProductId());
				} else {
					throw new ShoppingCartException("The quantity of the product can not be negative!");
				}
			}
			cartRepository.save(existingCart);
		} else {
			Cart newCart = new Cart();
			// Iterate over the product list and add in the new cart of the user
			for (ProductVO productVO : productVOList) {
				CartProductMapping cartProductMapping = new CartProductMapping();
				Product product = null;
				try {
					product = productRepository.getProductByID(productVO.getProductId());
				} catch (NoResultException nre) {
					logger.error("No product found for the product id" + productVO.getProductId());
					throw new ProductNotFoundException("Invalid product" + productVO.getProductId());
				}
				cartProductMapping.setCart(existingCart);
				cartProductMapping.setProduct(product);
				cartProductMapping.setQuantity(productVO.getQuantity());
				newCart.getCartProductMappings().put(productVO.getProductId(), cartProductMapping);
				cartRepository.save(newCart);
			}
		}
		cartRepository.flush();
	}

	/**
	 * This method removes the product from the cart.
	 * 
	 * @param userId
	 * @param productVO
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteProductFromCart(Long userId, List<ProductVO> productVOList) throws ShoppingCartException {
		logger.info("deleting the products from cart for user id " + userId);
		Cart existingCart = null;
		try {
			existingCart = cartRepository.findByUserId(userId);
		} catch (NoResultException nre) {
			logger.error("No Cart was found for the user!" + nre.getMessage());
			throw new CartNotFoundException(nre.getMessage());
		}
		logger.info("Fetching the cart" + existingCart);

		if (existingCart != null) {
			// Iterate over the product list and remove from the cart of the user
			for (ProductVO productVO : productVOList) {
				existingCart.getCartProductMappings().keySet().removeIf(key -> key == productVO.getProductId());
			}
		}
		/*
		 * If the product is last from the mapping then remove the cart object also,
		 * which will be like deleting the cart.
		 */
		if (CollectionUtils.isEmpty(existingCart.getCartProductMappings())) {
			cartRepository.deleteById(existingCart.getCartId());
		}
		cartRepository.saveAndFlush(existingCart);
	}
}
