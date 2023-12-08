package com.example.mohang.domain;

import com.example.mohang.dto.HangoutDto;
import com.example.mohang.dto.UserAccountDto;
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
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

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
    @ManyToOne(optional = false)
    @JoinColumn(name = "userId")
    private UserAccount userAccount;

    @Setter
    @Column(nullable = false)
    private String title;

    @Setter
    @Column(nullable = false, length = 10000)
    private String content;

    @Setter
    private String hashtag;

    @Setter
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(nullable = true , updatable = false)
    private LocalDateTime meetDate;

    @ToString.Exclude
    @OrderBy("createdAt DESC ")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hangout")
    private final Set<Chat> chats = new LinkedHashSet<>();

    @Setter
    @Column
    private String place;

    @Setter
    @Column
    private String address;
    private String region1;
    private String region2;
    protected Hangout(){}

    private Hangout(UserAccount userAccount,String title, String content, String hashtag , LocalDateTime meetDate, String place, String address){
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
        this.meetDate = meetDate;
        this.place = place;
        this.address = address;
        String[] splittedAddress = address.split(" ");
        this.region1 = splittedAddress[0];
        this.region2 = splittedAddress[1];
    }

    public static Hangout of(UserAccount userAccount,String title, String content, String hashtag, LocalDateTime meetDate, String place, String address ) {
        return new Hangout(userAccount,title, content, hashtag, meetDate, place, address);
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
        this.place = dto.place();
        this.address = dto.address();
    }

}
