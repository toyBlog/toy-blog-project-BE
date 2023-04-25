package com.toy.blog.api.model.response;

import com.toy.blog.domain.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.experimental.UtilityClass;
import org.apache.commons.collections4.CollectionUtils;

import java.io.File;
import java.time.ZonedDateTime;
import java.util.ArrayList;
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

        String writer;

        String title;

        String content;

        Integer viewCount;

        boolean isLiked;

        Integer likedCount;

        ZonedDateTime createdAt;
    }

    @Getter
    @Setter
    @SuperBuilder
    public static class Detail extends ArticleBase {

        List<String> urlList;

        public static Detail of(Article article) {

            List<String> urlList = article.getArticleImageList().stream()
                    .map(ai -> BASE_URL + File.separator + ai.getPath())
                    .collect(Collectors.toList());

            return Detail.builder()
                    .id(article.getId())
                    .title(article.getTitle())
                    .content(article.getContent())
                    .writer(article.getUser().getNickname())
                    .viewCount(article.getViewCount())
                    .likedCount(article.getLikedCount())
                    .urlList(CollectionUtils.isEmpty(article.getArticleImageList()) ? new ArrayList<>() : urlList)
                    .build();
        }

    }

    @Getter
    @Setter
    @SuperBuilder
    public static class Summary extends ArticleBase {

        String url;

        public static Summary of(Article article) {

            return Summary.builder()
                    .id(article.getId())
                    .title(article.getTitle())
                    .content(article.getContent().length() >= 100 ? article.getContent().substring(0, 100) : article.getContent())
                    .writer(article.getUser().getNickname())
                    .viewCount(article.getViewCount())
                    .likedCount(article.getLikedCount())
                    .createdAt(article.getCreatedAt())
                    .url(CollectionUtils.isEmpty(article.getArticleImageList()) ? "" : BASE_URL + File.separator + article.getArticleImageList().get(0).getPath())
                    .build();

        }

        public static List<Summary> of(List<Article> articleList) {

            return articleList.stream()
                    .map(Summary::of)
                    .collect(Collectors.toList());
        }
    }

    @Getter
    @Setter
    @Builder
    public static class Search {

        List<ArticleResponse.Summary> articleSummaryList;
        long totalCount;

        public static Search of(List<ArticleResponse.Summary> articleSummaryList, long totalCount) {
            return Search.builder()
                    .articleSummaryList(articleSummaryList)
                    .totalCount(totalCount)
                    .build();
        }
    }

}
