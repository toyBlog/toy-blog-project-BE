package com.toy.blog.api.model.response;

import com.toy.blog.domain.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.toy.blog.domain.common.CommonConstant.BaseUrl.BASE_URL;

@UtilityClass
public class ArticleResponse {

    @Getter
    @Setter
    @SuperBuilder
    @AllArgsConstructor
    public static class ArticleBase {

        Long id;

        String title;

        String writer;

        Integer viewCount;

        Integer likedCount;

        String url;

    }

    @Getter
    @Setter
    @SuperBuilder
    public static class Detail extends ArticleBase {

        String content;



        public static Detail of(Article article) {
            return Detail.builder()
                    .id(article.getId())
                    .title(article.getTitle())
                    .viewCount(article.getViewCount())
                    .likedCount(article.getLikedCount())
                    .writer(article.getUser().getNickname())
                    .content(article.getContent())
                    .build();
        }

    }


    @Getter
    @Setter
    @SuperBuilder
    public static class Search extends ArticleBase {

        public static List<Search> of(List<Article> article) {
            return article.stream().map(Search::of).collect(Collectors.toList());
        }

        public static Search of(Article article) {
            return Search.builder()
                    .id(article.getId())
                    .title(article.getTitle())
                    .viewCount(article.getViewCount())
                    .likedCount(article.getLikedCount())
                    .writer(article.getUser().getNickname())
                    .build();
        }

    }




}
