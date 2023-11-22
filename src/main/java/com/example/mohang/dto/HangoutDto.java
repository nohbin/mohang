package com.example.mohang.dto;

import com.example.mohang.domain.Hangout;
import lombok.Builder;
import org.antlr.v4.runtime.misc.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.example.mohang.domain.Hangout}
 */
@Builder
public record HangoutDto(
        String title,
        String content,
        String hashtag,
        LocalDateTime meetDate,
        String place,
        String address,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) implements Serializable {

}