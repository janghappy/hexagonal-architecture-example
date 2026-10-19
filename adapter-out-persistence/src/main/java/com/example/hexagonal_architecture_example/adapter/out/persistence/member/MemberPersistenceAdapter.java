package com.example.hexagonal_architecture_example.adapter.out.persistence.member;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.hexagonal_architecture_example.application.port.out.LoadMemberPort;
import com.example.hexagonal_architecture_example.application.port.out.SaveMemberPort;
import com.example.hexagonal_architecture_example.domain.Member;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class MemberPersistenceAdapter implements SaveMemberPort, LoadMemberPort {

	private final MemberJpaRepository repository;

	@Override
	public Member save(Member member) {
		MemberJpaEntity saved = repository.save(new MemberJpaEntity(member.id(), member.name()));
		return toDomain(saved);
	}

	@Override
	public Optional<Member> load(Long id) {
		return repository.findById(id).map(this::toDomain);
	}

	private Member toDomain(MemberJpaEntity entity) {
		return new Member(entity.getId(), entity.getName());
	}

}
