package com.toy.blog.api.service;

import com.toy.blog.api.model.request.ArticleRequest;
import com.toy.blog.api.model.response.ArticleResponse;
import com.toy.blog.domain.entity.Article;
import com.toy.blog.domain.entity.QArticle;
import com.toy.blog.domain.entity.QUser;
import com.toy.blog.domain.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;

    public List<ArticleResponse.Search> getArticles(ArticleRequest.Search request) {
        QArticle article = QArticle.article;
        QUser user = QUser.user;

        List<Article> articles = articleRepository.findArticlebySearchColum(request.getTitle(),request.getContent(), request.getNickname(),request.getPage(), request.getSize());

        return ArticleResponse.Search.of(articles);
    }

    public ArticleResponse.Detail getArticle(Long id) {
        Article article = articleRepository.findById(id).orElseThrow();
        // 조회수 증가
        articleRepository.updateViewCount(id);
        return ArticleResponse.Detail.of(article);
    }

    public void insertArticle(ArticleRequest.Register request) {
        articleRepository.save(request.toEntity(1L));
    }

    public void editArticle(Long id, ArticleRequest.Register request) {
    }

    public void deleteArticle(Long id) {
    }


    /**
     * [특정 User가 Follow 한 Friend들이 올린 Article List를 조회 하는 서비스]
     * */

}
