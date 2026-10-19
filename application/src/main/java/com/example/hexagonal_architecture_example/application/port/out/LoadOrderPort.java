package com.example.hexagonal_architecture_example.application.port.out;

import java.util.Optional;

import com.example.hexagonal_architecture_example.domain.Order;

public interface LoadOrderPort {

	Optional<Order> load(Long id);

}
