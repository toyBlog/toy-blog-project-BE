package com.toy.blog.api.model.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ArticleRequest {


    @Getter
    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Inventory {

        Integer page = 0;

        Integer size = 20;
    }

    @Getter
    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Search extends Inventory {

        String keyword;
    }

    @Getter
    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Register {

        @NotNull(message = "제목은 필수입니다.")
        @Size(min = 2, max = 20)
        String title;

        @NotNull(message = "본문 내용은 필수입니다.")
        @Size(min = 2, max = 200)
        String content;

        @Size(max = 5, message = "사진은 최대 5장 까지 등록 가능합니다.")
        private List<MultipartFile> imageList = new ArrayList<>();
    }

    @Getter
    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Edit {

        @NotNull(message = "제목은 필수입니다.")
        @Size(min = 2, max = 20)
        String title;

        @NotNull(message = "본문 내용은 필수입니다.")
        @Size(min = 2, max = 200)
        String content;

        @Size(max = 5, message = "사진은 최대 5장 까지 등록 가능합니다.")
        private List<MultipartFile> imageList = new ArrayList<>();
    }

    @Getter
    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Comment { //todo: renaming 이름 고민해봐야함

        @NotNull(message = "댓글 내용은 필수입니다")
        String content;
    }
}
