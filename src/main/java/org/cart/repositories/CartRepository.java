package org.cart.repositories;

import org.cart.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart , Long>{

	public Cart findByUserId(Long userId);
}
