package com.example.hexagonal_architecture_example.application.port.in;

import com.example.hexagonal_architecture_example.domain.Order;

public interface PlaceOrderUseCase {

	Order place(Long memberId, String productName);

}
