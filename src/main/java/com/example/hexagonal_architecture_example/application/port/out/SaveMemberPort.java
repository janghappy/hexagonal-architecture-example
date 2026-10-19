package com.example.hexagonal_architecture_example.application.port.out;

import com.example.hexagonal_architecture_example.domain.Member;

public interface SaveMemberPort {

	Member save(Member member);

}
