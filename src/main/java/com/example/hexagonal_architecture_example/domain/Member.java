package com.example.hexagonal_architecture_example.domain;

public record Member(Long id, String name) {

	public Member {
		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException("name must not be blank");
		}
	}

	public static Member withoutId(String name) {
		return new Member(null, name);
	}

}
