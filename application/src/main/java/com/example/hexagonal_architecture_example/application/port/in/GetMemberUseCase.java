package com.example.hexagonal_architecture_example.application.port.in;

import com.example.hexagonal_architecture_example.domain.Member;

public interface GetMemberUseCase {

	Member get(Long id);

}
