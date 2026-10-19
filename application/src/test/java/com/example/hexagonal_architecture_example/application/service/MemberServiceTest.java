package com.example.hexagonal_architecture_example.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.example.hexagonal_architecture_example.application.port.out.LoadMemberPort;
import com.example.hexagonal_architecture_example.application.port.out.SaveMemberPort;
import com.example.hexagonal_architecture_example.domain.Member;
import com.example.hexagonal_architecture_example.domain.MemberNotFoundException;

/**
 * Spring/DB 없이 아웃바운드 포트를 메모리 구현으로 갈아끼워 유스케이스를 테스트한다.
 */
class MemberServiceTest {

	static class InMemoryMemberAdapter implements SaveMemberPort, LoadMemberPort {

		private final Map<Long, Member> store = new HashMap<>();
		private long sequence = 0;

		@Override
		public Member save(Member member) {
			Member saved = new Member(++sequence, member.name());
			store.put(saved.id(), saved);
			return saved;
		}

		@Override
		public Optional<Member> load(Long id) {
			return Optional.ofNullable(store.get(id));
		}

	}

	private final InMemoryMemberAdapter adapter = new InMemoryMemberAdapter();
	private final MemberService service = new MemberService(adapter, adapter);

	@Test
	void registerAndGet() {
		Member registered = service.register("kim");

		assertEquals(registered, service.get(registered.id()));
	}

	@Test
	void getUnknownMemberThrows() {
		assertThrows(MemberNotFoundException.class, () -> service.get(999L));
	}

	@Test
	void blankNameIsRejected() {
		assertThrows(IllegalArgumentException.class, () -> service.register(" "));
	}

}
