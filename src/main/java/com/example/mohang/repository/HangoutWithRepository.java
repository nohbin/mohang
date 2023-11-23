package com.example.mohang.repository;

import com.example.mohang.entity.Chat;
import com.example.mohang.entity.HangoutWith;
import com.example.mohang.entity.HangoutWithID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HangoutWithRepository extends JpaRepository<HangoutWith, HangoutWithID> {
}
