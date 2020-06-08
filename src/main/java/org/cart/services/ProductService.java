package org.cart.services;

import java.util.List;

import org.cart.entities.Product;
import org.cart.exception.ShoppingCartException;
import org.cart.models.ProductVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Interface for Product related operations.
 */
@Service
@Transactional
public interface ProductService {

	public List<Product> list() throws ShoppingCartException;

	public Product getProductByID(Long id) throws ShoppingCartException;
	
	public Product getProductByName(String productName) throws ShoppingCartException;
	
	public List<Product> getProductByType(String productType) throws ShoppingCartException;
	
	public void addProduct(ProductVO productVO) throws ShoppingCartException;

}
