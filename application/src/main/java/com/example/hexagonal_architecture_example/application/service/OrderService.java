package com.example.hexagonal_architecture_example.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hexagonal_architecture_example.application.port.in.GetOrderUseCase;
import com.example.hexagonal_architecture_example.application.port.in.PlaceOrderUseCase;
import com.example.hexagonal_architecture_example.application.port.out.LoadMemberPort;
import com.example.hexagonal_architecture_example.application.port.out.LoadOrderPort;
import com.example.hexagonal_architecture_example.application.port.out.SaveOrderPort;
import com.example.hexagonal_architecture_example.domain.MemberNotFoundException;
import com.example.hexagonal_architecture_example.domain.Order;
import com.example.hexagonal_architecture_example.domain.OrderNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class OrderService implements PlaceOrderUseCase, GetOrderUseCase {

	private final LoadMemberPort loadMemberPort;
	private final SaveOrderPort saveOrderPort;
	private final LoadOrderPort loadOrderPort;

	@Override
	@Transactional
	public Order place(Long memberId, String productName) {
		Order order = Order.withoutId(memberId, productName);
		loadMemberPort.load(memberId)
				.orElseThrow(() -> new MemberNotFoundException(memberId));
		return saveOrderPort.save(order);
	}

	@Override
	@Transactional(readOnly = true)
	public Order get(Long id) {
		return loadOrderPort.load(id)
				.orElseThrow(() -> new OrderNotFoundException(id));
	}

}
