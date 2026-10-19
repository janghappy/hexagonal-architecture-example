package com.example.hexagonal_architecture_example.application.port.out;

import com.example.hexagonal_architecture_example.domain.Order;

public interface SaveOrderPort {

	Order save(Order order);

}
