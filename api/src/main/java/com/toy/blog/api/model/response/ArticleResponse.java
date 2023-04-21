package com.toy.blog.api.model.response;

import com.toy.blog.domain.common.CommonConstant;
import com.toy.blog.domain.entity.Article;
import com.toy.blog.domain.entity.ArticleImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.experimental.UtilityClass;

import java.io.File;
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

        String url;

        public static ArticleBase of(Article article) {

            return ArticleBase.builder()
                    .id(article.getId())
                    .title(article.getTitle())
                    .writer(article.getUser().getNickname())
                    .viewCount(article.getViewCount())
                    .likedCount(article.getLikedCount())
                    .url(
                            BASE_URL + File.separator + article.getArticleImageList().get(0).getPath())
                    .build();
        }

        public static List<ArticleBase> of(List<Article> articleList) {

            return articleList.stream()
                    .map(ArticleBase::of)
                    .collect(Collectors.toList());
        }
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




}
