package com.example.mohang.repository;

import com.example.mohang.domain.Hangouts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HangoutsRepository extends JpaRepository<Hangouts, Long> {
}