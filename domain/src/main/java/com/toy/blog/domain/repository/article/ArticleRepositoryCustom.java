package com.toy.blog.domain.repository.article;

import com.toy.blog.domain.entity.Article;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleRepositoryCustom {

    List<Article> getFollowArticleList(List<Long> friendIdList, Pageable pageable);
}
