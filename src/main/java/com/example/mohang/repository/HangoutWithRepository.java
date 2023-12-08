package com.example.mohang.repository;

import com.example.mohang.entity.HangoutWith;
import com.example.mohang.entity.HangoutWithID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HangoutWithRepository extends JpaRepository<HangoutWith, HangoutWithID> {

    @Query(value="select user_id from hangout_with where hangout_id = :hangoutId and user_id = :userId", nativeQuery = true)
    String findByHangoutAndUserId(@Param("hangoutId") Long hangoutId, @Param("userId") String userId);
}
