package com.example.mohang.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.example.mohang.domain.Hangouts}
 */
public record HangoutsDto(
        String title,
        String content,
        String hashtag,
        LocalDateTime meetDate,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) implements Serializable {
}