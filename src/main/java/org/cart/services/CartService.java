package org.cart.services;

import java.util.List;

import org.cart.exception.ShoppingCartException;
import org.cart.models.CartVO;
import org.cart.models.ProductVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Interface for Cart related operations.
 */
@Service
@Transactional
public interface CartService {

	public void createOrUpdateCart(Long userId, List<ProductVO> productVOList) throws ShoppingCartException;
	public void deleteProductFromCart(Long userId, List<ProductVO> productVOList) throws ShoppingCartException;
	public void addProductToCart(Long id, ProductVO productVO) throws ShoppingCartException;
	public CartVO getCartForUser(Long userId) throws ShoppingCartException;
}
