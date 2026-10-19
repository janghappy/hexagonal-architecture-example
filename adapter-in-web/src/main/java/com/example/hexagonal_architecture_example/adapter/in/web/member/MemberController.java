package com.example.hexagonal_architecture_example.adapter.in.web.member;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.hexagonal_architecture_example.application.port.in.GetMemberUseCase;
import com.example.hexagonal_architecture_example.application.port.in.RegisterMemberUseCase;
import com.example.hexagonal_architecture_example.domain.MemberNotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

	private final RegisterMemberUseCase registerMemberUseCase;
	private final GetMemberUseCase getMemberUseCase;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public MemberResponse register(@RequestBody RegisterMemberRequest request) {
		return MemberResponse.from(registerMemberUseCase.register(request.name()));
	}

	@GetMapping("/{id}")
	public MemberResponse get(@PathVariable Long id) {
		return MemberResponse.from(getMemberUseCase.get(id));
	}

	@ExceptionHandler(MemberNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleNotFound(MemberNotFoundException e) {
		return e.getMessage();
	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleBadRequest(IllegalArgumentException e) {
		return e.getMessage();
	}

}
