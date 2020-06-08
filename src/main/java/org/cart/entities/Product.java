/**
 * Product Entity class
 */
package org.cart.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "products")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({@NamedQuery(name = "Product.getProductByName", query = "FROM Product p WHERE LOWER(p.productName)=LOWER(?1)"),
		@NamedQuery(name = "Product.getProductByType", query = "FROM Product p WHERE LOWER(p.productType)=LOWER(?1)"),
		@NamedQuery(name = "Product.getProductByID", query = "FROM Product p WHERE p.productId=(?1)")})
public class Product implements Serializable, Comparable<Product> {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="product_id", nullable = false)
	private Long productId;
	@Column(name="product_name", nullable = false)
	private String productName;
	@Column(name="price", nullable = false)
	private float price;
	@Column(name="product_type", nullable = false)
	private char productType;
	
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	private List<CartProductMapping> cartProductMappings;
	
	public Product() {
		
	}

	/**
	 * @return the productId
	 */
	public Long getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the price
	 */
	public float getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(float price) {
		this.price = price;
	}

	/**
	 * @return the productType
	 */
	public char getProductType() {
		return productType;
	}

	/**
	 * @param productType the productType to set
	 */
	public void setProductType(char productType) {
		this.productType = productType;
	}
	/**
	 * Default override
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(price);
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		result = prime * result + ((productName == null) ? 0 : productName.hashCode());
		result = prime * result + productType;
		return result;
	}
	/**
	 * Default override
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (Float.floatToIntBits(price) != Float.floatToIntBits(other.price))
			return false;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		if (productName == null) {
			if (other.productName != null)
				return false;
		} else if (!productName.equals(other.productName))
			return false;
		if (productType != other.productType)
			return false;
		return true;
	}

	@Override
	public int compareTo(Product product) {
		return productName.compareTo(product.getProductName());
	}


}
