package com.example.hexagonal_architecture_example.adapter.in.web.member;

import com.example.hexagonal_architecture_example.domain.Member;

public record MemberResponse(Long id, String name) {

	static MemberResponse from(Member member) {
		return new MemberResponse(member.id(), member.name());
	}

}
