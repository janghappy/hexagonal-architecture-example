package com.example.hexagonal_architecture_example.domain;

public class MemberNotFoundException extends RuntimeException {

	public MemberNotFoundException(Long id) {
		super("member not found: " + id);
	}

}
