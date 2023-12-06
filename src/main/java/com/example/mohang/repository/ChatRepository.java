package com.example.mohang.repository;

import com.example.mohang.domain.Chat;
import com.example.mohang.domain.QChat;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@RepositoryRestResource
public interface ChatRepository extends JpaRepository<Chat, Long>,
        QuerydslPredicateExecutor<Chat>,
        QuerydslBinderCustomizer<QChat>
{
//    public List<Chat> findBySender(String sender);
//    @Query(value= "SELECT * " +
//                    "FROM chat " +
//                    "WHERE hang_id = :hangId",
//            nativeQuery=true)

    List<Chat> findByHangout_Id(Long hangId);

    List<Chat> findByUserAccount_UserId(String userId);

    @Override
    default void customize(QuerydslBindings bindings, QChat root){
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.content, root.createdAt);
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
    }


}
