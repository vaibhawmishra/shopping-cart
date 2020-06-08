/**
 * Apparal enity class which extends Product
 */
package org.cart.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name = "apparals")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Apparal extends Product implements Serializable { 

	private static final long serialVersionUID = 1L;
	@Column(name="product_id", nullable = false)
	private Long productId;
	@Column(name="apparal_type")
	private String apparalType;
	@Column(name="apparal_brand")
	private String apparalBrand;
	@Column(name="apparal_design")
	private String apparalDesign;
	
	public Apparal() {
		
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
	 * @return the apparalType
	 */
	public String getApparalType() {
		return apparalType;
	}

	/**
	 * @param apparalType the apparalType to set
	 */
	public void setApparalType(String apparalType) {
		this.apparalType = apparalType;
	}

	/**
	 * @return the apparalBrand
	 */
	public String getApparalBrand() {
		return apparalBrand;
	}

	/**
	 * @param apparalBrand the apparalBrand to set
	 */
	public void setApparalBrand(String apparalBrand) {
		this.apparalBrand = apparalBrand;
	}

	/**
	 * @return the apparalDesign
	 */
	public String getApparalDesign() {
		return apparalDesign;
	}

	/**
	 * @param apparalDesign the apparalDesign to set
	 */
	public void setApparalDesign(String apparalDesign) {
		this.apparalDesign = apparalDesign;
	}
	/**
	 * Default override
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((apparalBrand == null) ? 0 : apparalBrand.hashCode());
		result = prime * result + ((apparalDesign == null) ? 0 : apparalDesign.hashCode());
		result = prime * result + ((apparalType == null) ? 0 : apparalType.hashCode());
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		return result;
	}
	/**
	 * Default override
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Apparal other = (Apparal) obj;
		if (apparalBrand == null) {
			if (other.apparalBrand != null)
				return false;
		} else if (!apparalBrand.equals(other.apparalBrand))
			return false;
		if (apparalDesign == null) {
			if (other.apparalDesign != null)
				return false;
		} else if (!apparalDesign.equals(other.apparalDesign))
			return false;
		if (apparalType == null) {
			if (other.apparalType != null)
				return false;
		} else if (!apparalType.equals(other.apparalType))
			return false;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		return true;
	}

}
