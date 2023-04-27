package com.toy.blog.domain.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleSummaryDto {

    Long id;

    String writer;

    String title;

    String summaryContent;

    Integer viewCount;

    Boolean isLiked;

    Long likedCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    ZonedDateTime createdAt;

    @QueryProjection
    public ArticleSummaryDto(Long id, String writer, String title, String summaryContent, Integer viewCount, ZonedDateTime createdAt) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.summaryContent = summaryContent;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
    }
}
