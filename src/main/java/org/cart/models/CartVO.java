package org.cart.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class CartVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private long cartId;
	
	private long userId;
	
	private float totalPrice;
	
	private Map<Long, ProductVO> productVOs = new HashMap<Long, ProductVO>();

	/**
	 * @return the cartId
	 */
	public long getCartId() {
		return cartId;
	}

	/**
	 * @param cartId the cartId to set
	 */
	public void setCartId(long cartId) {
		this.cartId = cartId;
	}

	/**
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * @return the totalPrice
	 */
	public float getTotalPrice() {
		return totalPrice;
	}

	/**
	 * @param totalPrice the totalPrice to set
	 */
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * @return the productVOs
	 */
	public Map<Long, ProductVO> getProductVOs() {
		return productVOs;
	}

	/**
	 * @param productVOs the productVOs to set
	 */
	public void setProductVOs(Map<Long, ProductVO> productVOs) {
		this.productVOs = productVOs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (cartId ^ (cartId >>> 32));
		result = prime * result + ((productVOs == null) ? 0 : productVOs.hashCode());
		result = prime * result + Float.floatToIntBits(totalPrice);
		result = prime * result + (int) (userId ^ (userId >>> 32));
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
		CartVO other = (CartVO) obj;
		if (cartId != other.cartId)
			return false;
		if (productVOs == null) {
			if (other.productVOs != null)
				return false;
		} else if (!productVOs.equals(other.productVOs))
			return false;
		if (Float.floatToIntBits(totalPrice) != Float.floatToIntBits(other.totalPrice))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

}
