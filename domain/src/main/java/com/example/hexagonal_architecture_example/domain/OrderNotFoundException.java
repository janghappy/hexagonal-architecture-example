package com.example.hexagonal_architecture_example.domain;

public class OrderNotFoundException extends RuntimeException {

	public OrderNotFoundException(Long id) {
		super("order not found: " + id);
	}

}
