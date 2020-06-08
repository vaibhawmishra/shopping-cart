package org.cart.services.impl;

import java.util.List;

import javax.persistence.NoResultException;

import org.cart.entities.Product;
import org.cart.exception.ProductNotFoundException;
import org.cart.exception.ShoppingCartException;
import org.cart.models.ProductVO;
import org.cart.repositories.ProductRepository;
import org.cart.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Interface implementation for Product related operations.
 * @author Vaibhaw
 *
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository productRepository;
	
	Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	/**
	 * Get all products
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Product> list() throws ShoppingCartException {
		try {
			return productRepository.findAll();
		}  catch (NoResultException exp) {
			logger.error(exp.getMessage());
			throw new ProductNotFoundException();
		} catch (Exception exp) {
			logger.error(exp.getMessage());
			throw new ProductNotFoundException();
		}
	}

	/**
	 * Get product by name
	 * @param productName
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public Product getProductByName(String productName) throws ShoppingCartException {
		try {
			return productRepository.getProductByName(productName);
		} catch (Exception exp) {
			logger.error(exp.getMessage());
			throw new ProductNotFoundException();
		}
	}
	/**
	 * Get product by type
	 * @param productType
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Product> getProductByType(String productType) throws ShoppingCartException {
		try {
			return productRepository.getProductByType(productType);
		} catch (NoResultException exp) {
			logger.error(exp.getMessage());
			throw new ProductNotFoundException();
		} catch (Exception exp) {
			logger.error(exp.getMessage());
			throw new ProductNotFoundException();
		}
	}
	
	/**
	 * Get product by ID
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public Product getProductByID(Long id)  throws ShoppingCartException{
		try {
			return productRepository.getProductByID(id);
		} catch (NoResultException exp) {
			logger.error(exp.getMessage());
			throw new ProductNotFoundException();
		} catch (Exception exp) {
			logger.error(exp.getMessage());
			throw new ShoppingCartException();
		}
	}
	/**
	 * Add a product.
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public void addProduct(ProductVO productVO) throws ShoppingCartException {
		Product product = new Product();
		product.setProductType(productVO.getProductType().charAt(0));
		product.setPrice(productVO.getPrice());
		product.setProductName(productVO.getProductName());
		productRepository.save(product);
	}


}
