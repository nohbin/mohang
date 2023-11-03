package com.example.mohang.repository;

import com.example.mohang.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    public List<Chat> findBySender(String sender);
    @Query(value= "SELECT * " +
                    "FROM chat " +
                    "WHERE hang_id = :hangId",
            nativeQuery=true)
    List<Chat> findByHangId(@Param("hangId") Long hangId);
}
