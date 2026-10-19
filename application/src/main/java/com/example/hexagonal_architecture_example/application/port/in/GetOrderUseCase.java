package com.example.hexagonal_architecture_example.application.port.in;

import com.example.hexagonal_architecture_example.domain.Order;

public interface GetOrderUseCase {

	Order get(Long id);

}
