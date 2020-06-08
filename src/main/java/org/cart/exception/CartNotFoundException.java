package org.cart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Oops! Cart not found...")
public class CartNotFoundException extends ShoppingCartException {

	private static final long serialVersionUID = 1L;

	public CartNotFoundException() {
		super();
	}

	public CartNotFoundException(String message) {
		super(message);
	}
}
