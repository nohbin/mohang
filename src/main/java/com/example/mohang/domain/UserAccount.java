package com.example.mohang.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "userId", unique = true),
        @Index(columnList = "email", unique = true),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class)
public class UserAccount {

    @Id
    @Setter
    @Column(length = 50)
    private String userId;

    @Setter
    @Column(nullable = false)
    private String userPassword;

    @Setter
    @Column(length = 100)
    private String email;

    @Setter
    @Column(length = 100)
    private String nickname;



    protected UserAccount(){}

    private UserAccount(String userId, String userPassword, String nickname, String email, String createdBy) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.nickname = nickname;
        this.email = email;
        this.createdBy = createdBy;
        this.modifiedBy = createdBy;
    }

    public static UserAccount of(String userId, String userPassword, String nickname, String email) {
        return new UserAccount(userId, userPassword, nickname, email, null);
    }

    public static UserAccount of(String userId, String userPassword, String nickname, String email, String createdBy) {
        return new UserAccount(userId, userPassword, nickname, email, createdBy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccount userAccount)) return false;
        return userId != null && userId.equals(userAccount.userId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId);
    }

    // JpaAuditing 을 통해 사용
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    @Column(nullable = false , updatable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(nullable = false , length = 100 , updatable = false)
    private String createdBy;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt;

    @LastModifiedBy
    @Column(nullable = false,length = 100)
    private String modifiedBy;
}
