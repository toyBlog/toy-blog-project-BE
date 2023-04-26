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

        Boolean isLiked;

        Long likedCount;

        ZonedDateTime createdAt;
    }

    @Getter
    @Setter
    @Builder
    public static class BaseResponse {

        Long id;

        public static BaseResponse of(Long id) {

            return BaseResponse.builder()
                    .id(id)
                    .build();
        }
    }


    @Getter
    @Setter
    @SuperBuilder
    public static class Detail extends ArticleBase {

        List<String> urlList;

        public static Detail of(Article article, Boolean isLiked, long likedCount) {

            return Detail.builder()
                    .id(article.getId())
                    .title(article.getTitle())
                    .content(article.getContent())
                    .writer(article.getUser().getNickname())
                    .viewCount(article.getViewCount())
                    .isLiked(isLiked)
                    .likedCount(likedCount)
                    .build();
        }

        public static Detail of(Article article, Boolean isLiked, long likedCount, List<String> urlList) {

            return Detail.builder()
                    .id(article.getId())
                    .title(article.getTitle())
                    .content(article.getContent())
                    .writer(article.getUser().getNickname())
                    .viewCount(article.getViewCount())
                    .isLiked(isLiked)
                    .likedCount(likedCount)
                    .urlList(urlList)
                    .build();
        }

    }

    @Getter
    @Setter
    @SuperBuilder
    public static class Summary extends ArticleBase {

        public static Summary of(Article article, Boolean isLiked, long likedCount) {

            return Summary.builder()
                    .id(article.getId())
                    .title(article.getTitle())
                    .content(article.getContent())
                    .writer(article.getUser().getNickname())
                    .viewCount(article.getViewCount())
                    .isLiked(isLiked)
                    .likedCount(likedCount)
                    .createdAt(article.getCreatedAt())
                    .build();

        }

        public static List<Summary> of(List<Article> articleList, List<Boolean> isLikedList, List<Long> likedCountList) {

            List<Summary> summaryList = new ArrayList<>();

            for (int i = 0; i < articleList.size(); i++) {
                summaryList.add(Summary.of(articleList.get(i), isLikedList.get(i), likedCountList.get(i)));
            }

            return summaryList;
        }
    }

    @Getter
    @Setter
    @Builder
    public static class Search {

        List<ArticleResponse.Summary> articleSummaryList = new ArrayList<>();
        long totalCount;

        public static Search of(List<ArticleResponse.Summary> articleSummaryList , long totalCount) {
            return Search.builder()
                    .articleSummaryList(articleSummaryList)
                    .totalCount(totalCount)
                    .build();
        }
    }


}
