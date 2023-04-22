package com.toy.blog.domain.repository.article.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.Generated;

/**
 * com.toy.blog.domain.repository.article.dto.QArticleSummaryDto is a Querydsl Projection type for ArticleSummaryDto
 */
@Generated("com.querydsl.codegen.ProjectionSerializer")
public class QArticleSummaryDto extends ConstructorExpression<ArticleSummaryDto> {

    private static final long serialVersionUID = 1346377439L;

    public QArticleSummaryDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> writer, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<String> summaryContent, com.querydsl.core.types.Expression<Integer> viewCount, com.querydsl.core.types.Expression<Integer> likedCount, com.querydsl.core.types.Expression<java.time.ZonedDateTime> createdAt) {
        super(ArticleSummaryDto.class, new Class<?>[]{long.class, String.class, String.class, String.class, int.class, int.class, java.time.ZonedDateTime.class}, id, writer, title, summaryContent, viewCount, likedCount, createdAt);
    }

}

