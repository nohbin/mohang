package com.example.mohang.repository;

import com.example.mohang.domain.Hangout;
import com.example.mohang.domain.QHangout;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface HangoutRepository extends
        JpaRepository<Hangout, Long>,
        QuerydslPredicateExecutor<Hangout>,
        QuerydslBinderCustomizer<QHangout>
    {

        @Override
        default void customize(QuerydslBindings bindings, QHangout root){
            bindings.excludeUnlistedProperties(true);
            bindings.including(root.title, root.content, root.hashtag, root.createdAt, root.createdBy);
            bindings.bind(root.title).first(StringExpression::containsIgnoreCase);
            bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
            bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
            bindings.bind(root.createdAt).first(DateTimeExpression::eq);
            bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);

        }

        Page<Hangout> findAll(Pageable pageable);
        Page<Hangout> findByTitleContaining(String keyword, Pageable pageable);
        Page<Hangout> findByContentContaining(String keyword, Pageable pageable);
        Page<Hangout> findByCreatedByContaining(String keyword, Pageable pageable);


}