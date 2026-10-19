package com.example.hexagonal_architecture_example.adapter.in.web.order;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.hexagonal_architecture_example.application.port.in.GetOrderUseCase;
import com.example.hexagonal_architecture_example.application.port.in.PlaceOrderUseCase;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

	private final PlaceOrderUseCase placeOrderUseCase;
	private final GetOrderUseCase getOrderUseCase;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrderResponse place(@RequestBody PlaceOrderRequest request) {
		return OrderResponse.from(placeOrderUseCase.place(request.memberId(), request.productName()));
	}

	@GetMapping("/{id}")
	public OrderResponse get(@PathVariable Long id) {
		return OrderResponse.from(getOrderUseCase.get(id));
	}

}
