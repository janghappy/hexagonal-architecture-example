package com.example.hexagonal_architecture_example.adapter.out.persistence.order;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.hexagonal_architecture_example.application.port.out.LoadOrderPort;
import com.example.hexagonal_architecture_example.application.port.out.SaveOrderPort;
import com.example.hexagonal_architecture_example.domain.Order;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class OrderPersistenceAdapter implements SaveOrderPort, LoadOrderPort {

	private final OrderJpaRepository repository;

	@Override
	public Order save(Order order) {
		OrderJpaEntity saved = repository.save(new OrderJpaEntity(order.id(), order.memberId(), order.productName()));
		return toDomain(saved);
	}

	@Override
	public Optional<Order> load(Long id) {
		return repository.findById(id).map(this::toDomain);
	}

	private Order toDomain(OrderJpaEntity entity) {
		return new Order(entity.getId(), entity.getMemberId(), entity.getProductName());
	}

}
