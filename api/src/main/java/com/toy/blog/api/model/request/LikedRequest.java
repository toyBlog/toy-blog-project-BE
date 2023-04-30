package com.toy.blog.api.model.request;

import com.toy.blog.domain.common.Status;
import com.toy.blog.domain.entity.Article;
import com.toy.blog.domain.entity.Liked;
import com.toy.blog.domain.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;

@UtilityClass
@Deprecated
public class LikedRequest {


    // 사용하지 않는 클래스
    @Getter
    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Register {

        public Liked toEntity(Long articleId, Long userId) {
            return Liked.builder()
                    .status(Status.Like.ACTIVE)
                    .article(Article.builder().id(articleId).build())
                    .user(User.builder().id(userId).build())
                    .build();
        }
    }
}
