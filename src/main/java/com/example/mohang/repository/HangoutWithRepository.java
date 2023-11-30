package com.example.mohang.repository;

import com.example.mohang.entity.HangoutWith;
import com.example.mohang.entity.HangoutWithID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HangoutWithRepository extends JpaRepository<HangoutWith, HangoutWithID> {
}
