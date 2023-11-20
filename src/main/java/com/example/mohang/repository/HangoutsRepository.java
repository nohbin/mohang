package com.example.mohang.repository;

import com.example.mohang.domain.Hangouts;
import com.example.mohang.domain.QHangouts;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface HangoutsRepository extends
        JpaRepository<Hangouts, Long>,
        QuerydslPredicateExecutor<Hangouts>,
        QuerydslBinderCustomizer<QHangouts>
    {
        @Override
        default void customize(QuerydslBindings bindings, QHangouts root){
            bindings.excludeUnlistedProperties(true);
            bindings.including(root.title, root.content, root.hashtag, root.createdAt, root.createdBy);
            bindings.bind(root.title).first(StringExpression::containsIgnoreCase);
            bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
            bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
            bindings.bind(root.createdAt).first(DateTimeExpression::eq);
            bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);

        }


}