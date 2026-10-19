package com.example.hexagonal_architecture_example.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.example.hexagonal_architecture_example.application.port.out.LoadOrderPort;
import com.example.hexagonal_architecture_example.application.port.out.SaveOrderPort;
import com.example.hexagonal_architecture_example.application.service.MemberServiceTest.InMemoryMemberAdapter;
import com.example.hexagonal_architecture_example.domain.Member;
import com.example.hexagonal_architecture_example.domain.MemberNotFoundException;
import com.example.hexagonal_architecture_example.domain.Order;
import com.example.hexagonal_architecture_example.domain.OrderNotFoundException;

/**
 * 회원·주문 아웃바운드 포트를 메모리 구현으로 갈아끼워 주문 유스케이스를 테스트한다.
 */
class OrderServiceTest {

	static class InMemoryOrderAdapter implements SaveOrderPort, LoadOrderPort {

		private final Map<Long, Order> store = new HashMap<>();
		private long sequence = 0;

		@Override
		public Order save(Order order) {
			Order saved = new Order(++sequence, order.memberId(), order.productName());
			store.put(saved.id(), saved);
			return saved;
		}

		@Override
		public Optional<Order> load(Long id) {
			return Optional.ofNullable(store.get(id));
		}

	}

	private final InMemoryMemberAdapter memberAdapter = new InMemoryMemberAdapter();
	private final InMemoryOrderAdapter orderAdapter = new InMemoryOrderAdapter();
	private final OrderService service = new OrderService(memberAdapter, orderAdapter, orderAdapter);

	@Test
	void placeAndGet() {
		Member member = memberAdapter.save(Member.withoutId("kim"));

		Order placed = service.place(member.id(), "book");

		assertEquals(placed, service.get(placed.id()));
	}

	@Test
	void placeOrderForUnknownMemberThrows() {
		assertThrows(MemberNotFoundException.class, () -> service.place(999L, "book"));
	}

	@Test
	void getUnknownOrderThrows() {
		assertThrows(OrderNotFoundException.class, () -> service.get(999L));
	}

	@Test
	void blankProductNameIsRejected() {
		Member member = memberAdapter.save(Member.withoutId("kim"));

		assertThrows(IllegalArgumentException.class, () -> service.place(member.id(), " "));
	}

}
