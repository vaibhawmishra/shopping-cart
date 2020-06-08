/**
 * Cart Product mapping Entity class
 */
package org.cart.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity(name = "cart_product_mapping")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(uniqueConstraints={
	    @UniqueConstraint(columnNames = {"cart_id", "product_id"})
	}) 
public class CartProductMapping implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cart_product_id")
	private Long cartProductId;

	@Column(name="quantity")
	private Integer quantity;
	
	@ManyToOne
	@JoinColumn(name="cart_id")
    private Cart cart;
	
	@ManyToOne
	@JoinColumn(name="product_id")
    private Product product;
	
	public CartProductMapping() {
		
	}

	/**
	 * @return the cartProductId
	 */
	public Long getCartProductId() {
		return cartProductId;
	}

	/**
	 * @param cartProductId the cartProductId to set
	 */
	public void setCartProductId(Long cartProductId) {
		this.cartProductId = cartProductId;
	}

	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the product
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * @param cart the cart to set
	 */
	public void setCart(Cart cart) {
		this.cart = cart;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cart == null) ? 0 : cart.hashCode());
		result = prime * result + ((cartProductId == null) ? 0 : cartProductId.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartProductMapping other = (CartProductMapping) obj;
		if (cart == null) {
			if (other.cart != null)
				return false;
		} else if (!cart.equals(other.cart))
			return false;
		if (cartProductId == null) {
			if (other.cartProductId != null)
				return false;
		} else if (!cartProductId.equals(other.cartProductId))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		return true;
	}

}
