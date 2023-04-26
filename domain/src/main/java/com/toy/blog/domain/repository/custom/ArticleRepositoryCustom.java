package com.toy.blog.domain.repository.custom;

import com.toy.blog.domain.entity.Article;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleRepositoryCustom {

    List<Article> findFollowArticleList(List<Long> friendIdList, Pageable pageable);

    long countFollowArticleList(List<Long> friendIdList);

    /** --------------------------------------------------------------------------------------------------------------*/

    List<Article> findByTitleOrContent(String keyword, Integer page, Integer size);

    long countByTitleOrContent(String keyword);
}
