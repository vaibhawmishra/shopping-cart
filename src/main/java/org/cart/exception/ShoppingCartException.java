package org.cart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Oops! Issue encountered, try again later!!")
public class ShoppingCartException extends Exception {

	private static final long serialVersionUID = 1L;

	public ShoppingCartException() {
		super();
	}

	public ShoppingCartException(String message) {
		super(message);
	}
}
