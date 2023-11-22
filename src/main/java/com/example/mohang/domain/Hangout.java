package com.example.mohang.domain;

import com.example.mohang.dto.HangoutDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class)
public class Hangout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String title;

    @Setter
    @Column(nullable = false, length = 10000)
    private String content;

    @Setter
    private String hashtag;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(nullable = true , updatable = false)
    private LocalDateTime meetDate;

    protected Hangout(){}

    private Hangout(String title, String content, String hashtag){
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Hangout of(String title, String content, String hashtag ) {
        return new Hangout(title, content, hashtag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hangout mohang)) return false;
        return Objects.equals(id, mohang.id) && Objects.equals(title, mohang.title) && Objects.equals(content, mohang.content) && Objects.equals(hashtag, mohang.hashtag) && Objects.equals(meetDate, mohang.meetDate) && Objects.equals(createdAt, mohang.createdAt) && Objects.equals(createdBy, mohang.createdBy) && Objects.equals(modifiedAt, mohang.modifiedAt) && Objects.equals(modifiedBy, mohang.modifiedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, hashtag, meetDate, createdAt, createdBy, modifiedAt, modifiedBy);
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

    // 임시 생성자

    public Hangout(HangoutDto dto) {
        this.title = dto.title();
        this.content = dto.content();
        this.hashtag = dto.hashtag();
        this.createdBy = dto.createdBy();
        this.meetDate = dto.meetDate();
    }

}
