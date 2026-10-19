package com.example.hexagonal_architecture_example.domain;

public record Order(Long id, Long memberId, String productName) {

	public Order {
		if (memberId == null) {
			throw new IllegalArgumentException("memberId must not be null");
		}
		if (productName == null || productName.isBlank()) {
			throw new IllegalArgumentException("productName must not be blank");
		}
	}

	public static Order withoutId(Long memberId, String productName) {
		return new Order(null, memberId, productName);
	}

}
