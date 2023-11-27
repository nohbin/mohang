package com.example.mohang.dto;

import com.example.mohang.domain.UserAccount;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.example.mohang.domain.UserAccount}
 */
public record UserAccountDto(
        String userId,
        String userPassword,
        String email,
        String nickname,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
        )  {
    public static UserAccountDto of(String userId, String userPassword, String email, String nickname) {
        return new UserAccountDto(userId, userPassword, email,nickname, null, null, null, null);
    }
    public static UserAccountDto of(String userId, String userPassword, String email, String nickname, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new UserAccountDto(userId, userPassword, email,nickname, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static UserAccountDto from(UserAccount entity){
        return new UserAccountDto(
                entity.getUserId(),
                entity.getUserPassword(),
                entity.getEmail(),
                entity.getNickname(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    public UserAccount toEntity(){
        return UserAccount.of(
                userId,
                userPassword,
                email,
                nickname
        );
    }
}