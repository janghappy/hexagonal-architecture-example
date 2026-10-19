package com.example.hexagonal_architecture_example.adapter.out.persistence.order;

import org.springframework.data.jpa.repository.JpaRepository;

interface OrderJpaRepository extends JpaRepository<OrderJpaEntity, Long> {
}
