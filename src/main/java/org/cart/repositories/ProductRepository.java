package org.cart.repositories;

import java.util.List;

import org.cart.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product , Long>{
	public Product getProductByID(Long productID);
	public Product getProductByName(String productName);
	public List<Product> getProductByType(String productType);
}
