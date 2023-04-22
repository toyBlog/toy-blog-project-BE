package com.toy.blog.api.model.response;

import com.querydsl.core.annotations.QueryProjection;
import com.toy.blog.domain.common.CommonConstant;
import com.toy.blog.domain.entity.Article;
import com.toy.blog.domain.entity.ArticleImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.experimental.UtilityClass;
import org.apache.commons.collections4.CollectionUtils;

import java.io.File;
import java.time.ZonedDateTime;
import java.util.List;
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

        ZonedDateTime createdAt;
    }

    @Getter
    @Setter
    @SuperBuilder
    public static class Detail extends ArticleBase {

        String content;

        List<String> urlList;

        public static Detail of(Article article) {

            return Detail.builder()
                    .id(article.getId())
                    .title(article.getTitle())
                    .content(article.getContent())
                    .writer(article.getUser().getNickname())
                    .viewCount(article.getViewCount())
                    .likedCount(article.getLikedCount())
                    .urlList(article.getArticleImageList().stream().map(articleImage -> BASE_URL + File.separator + articleImage.getPath()).collect(Collectors.toList()))
                    .build();
        }

    }

    @Getter
    @Setter
    @SuperBuilder
    public static class Search extends ArticleBase {

        String url;

        public static Search of(Article article) {

            return Search.builder()
                    .id(article.getId())
                    .title(article.getTitle())
                    .writer(article.getUser().getNickname())
                    .viewCount(article.getViewCount())
                    .likedCount(article.getLikedCount())
                    .createdAt(article.getCreatedAt())
                    .url(CollectionUtils.isEmpty(article.getArticleImageList()) ? "" : BASE_URL + File.separator + article.getArticleImageList().get(0).getPath())
                    .build();

        }

        public static List<Search> of(List<Article> articleList) {

            return articleList.stream()
                    .map(Search::of)
                    .collect(Collectors.toList());
        }
    }





}
