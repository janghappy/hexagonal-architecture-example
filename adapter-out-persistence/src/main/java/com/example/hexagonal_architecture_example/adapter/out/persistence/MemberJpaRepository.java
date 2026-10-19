package com.example.hexagonal_architecture_example.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface MemberJpaRepository extends JpaRepository<MemberJpaEntity, Long> {
}
