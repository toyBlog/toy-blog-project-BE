package com.toy.blog.api.model.request;

import com.toy.blog.domain.common.CommonConstant;
import com.toy.blog.domain.common.Status;
import com.toy.blog.domain.entity.Article;
import com.toy.blog.domain.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@UtilityClass
public class ArticleRequest {


    @Getter
    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Inventory {

        Integer page = 0;

        Integer size = 5;
    }

    @Getter
    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Search extends Inventory{
        @Size(min = 2, max = 20)
        String title;

        @Size(min = 2, max = 200)
        String content;

        String writer;
    }

    @Getter
    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Register {

        @NotNull
        @Size(min = 2, max = 20)
        String title;

        @NotNull
        @Size(min = 2, max = 200)
        String content;

        public Article toEntity(Long userId) {
            return Article.builder()
                    .title(title)
                    .content(content)
                    .user(User.builder().id(userId).build())
                    .viewCount(0)
                    .likedCount(0)
                    .status(Status.Article.ACTIVE)
                    .build();
        }
    }

    @Getter
    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Login {

        @NotNull
        @Size(max = 50)
        @Pattern(regexp = CommonConstant.RegExp.EMAIL)
        String email;

        @NotNull
        @Size(max = 255)
        String password;
    }

}
