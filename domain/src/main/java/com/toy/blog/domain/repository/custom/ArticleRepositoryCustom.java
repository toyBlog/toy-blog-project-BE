package com.toy.blog.domain.repository.custom;

import com.toy.blog.domain.common.Status;
import com.toy.blog.domain.entity.Article;
import com.toy.blog.domain.entity.Comment;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ArticleRepositoryCustom {

    List<Article> findFollowArticleList(List<Long> friendIdList, Pageable pageable);

    long countFollowArticleList(List<Long> friendIdList);

    /** --------------------------------------------------------------------------------------------------------------*/

    List<Article> findByTitleOrContent(String keyword, Integer page, Integer size);

    long countByTitleOrContent(String keyword);

    /** --------------------------------------------------------------------------------------------------------------*/

    Optional<Article> findArticleWithCommentsById(Long id);
}
