package com.example.hexagonal_architecture_example.adapter.in.web.order;

import com.example.hexagonal_architecture_example.domain.Order;

public record OrderResponse(Long id, Long memberId, String productName) {

	static OrderResponse from(Order order) {
		return new OrderResponse(order.id(), order.memberId(), order.productName());
	}

}
