package com.toy.blog.api.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.toy.blog.domain.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.experimental.UtilityClass;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class CommentResponse {

    @Getter
    @Setter
    @SuperBuilder
    @AllArgsConstructor
    public static class CommentBase {

        Long id;

        String content;

        String writer;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        ZonedDateTime createdAt;
    }

    @Getter
    @Setter
    @Builder
    public static class BaseResponse {

        Long id;

        public static CommentResponse.BaseResponse of(Long id) {

            return CommentResponse.BaseResponse.builder()
                    .id(id)
                    .build();
        }
    }

    @Getter
    @Setter
    @SuperBuilder
    public static class Detail extends CommentResponse.CommentBase {

        Boolean isAuthor;

        public static Detail of(Comment comment) {

            return Detail.builder()
                    .id(comment.getId())
                    .content(comment.getContent())
                    .writer(comment.getUser().getNickname())
                    .isAuthor(false)
                    .createdAt(comment.getCreatedAt())
                    .build();
        }

        public static Detail of(Comment comment, Long authorId) {

            return Detail.builder()
                    .id(comment.getId())
                    .content(comment.getContent())
                    .writer(comment.getUser().getNickname())
                    .isAuthor(comment.getUser().getId().equals(authorId))
                    .createdAt(comment.getCreatedAt())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    public static class Search {

        List<CommentResponse.Detail> commentDetailList = new ArrayList<>();
        long totalCount;

        public static Search of(List<CommentResponse.Detail> commentDetailList, long totalCount) {

            return Search.builder()
                         .commentDetailList(commentDetailList)
                         .totalCount(totalCount)
                         .build();
        }
    }
}
