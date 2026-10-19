package com.example.hexagonal_architecture_example.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hexagonal_architecture_example.application.port.in.GetMemberUseCase;
import com.example.hexagonal_architecture_example.application.port.in.RegisterMemberUseCase;
import com.example.hexagonal_architecture_example.application.port.out.LoadMemberPort;
import com.example.hexagonal_architecture_example.application.port.out.SaveMemberPort;
import com.example.hexagonal_architecture_example.domain.Member;
import com.example.hexagonal_architecture_example.domain.MemberNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService implements RegisterMemberUseCase, GetMemberUseCase {

	private final SaveMemberPort saveMemberPort;
	private final LoadMemberPort loadMemberPort;

	@Override
	@Transactional
	public Member register(String name) {
		return saveMemberPort.save(Member.withoutId(name));
	}

	@Override
	@Transactional(readOnly = true)
	public Member get(Long id) {
		return loadMemberPort.load(id)
				.orElseThrow(() -> new MemberNotFoundException(id));
	}

}
