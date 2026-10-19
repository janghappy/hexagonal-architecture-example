package com.example.hexagonal_architecture_example.application.port.out;

import java.util.Optional;

import com.example.hexagonal_architecture_example.domain.Member;

public interface LoadMemberPort {

	Optional<Member> load(Long id);

}
