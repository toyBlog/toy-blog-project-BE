package com.toy.blog.api.service;

import com.toy.blog.api.model.request.ArticleRequest;
import com.toy.blog.api.model.response.ArticleResponse;
import com.toy.blog.auth.service.LoginService;
import com.toy.blog.domain.entity.Article;
import com.toy.blog.domain.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final LoginService loginService;

    /**
     * 게시판 목록 조회
     */
    public List<ArticleResponse.Search> getArticles(ArticleRequest.Search request) {
        List<Article> articles = articleRepository.findAllArticleBySearch(request.getTitle(),
                request.getContent(),
                request.getNickname(),
                request.getPage(),
                request.getSize());

        return ArticleResponse.Search.of(articles);
    }

    /**
     * 게시글 상세 조회
     * 예외처리 해야 함
     */
    public ArticleResponse.Detail getArticle(Long id) {
        Long userId = loginService.getLoginUserId();
        Article article = articleRepository.findById(id).orElseThrow();

        // 조회수 증가
        if (!userId.equals(article.getUser().getId())) {
            articleRepository.updateViewCount(id);
        }

        return ArticleResponse.Detail.of(article);
    }

    /**
     * 게시글 작성
     */
    public void insertArticle(ArticleRequest.Register request) {
        Long userId = loginService.getLoginUserId();
        articleRepository.save(request.toEntity(userId));
    }

    /**
     * 게시글 수정
     * 예외처리 해야 함
     */
    public void editArticle(Long id, ArticleRequest.Register request) {
        Long userId = loginService.getLoginUserId();
        // 예외 처리 추가
        Article article = articleRepository.findArticleWithUserBy(id).orElseThrow();

        if (!userId.equals(article.getUser().getId())) {
//            throw new AccessDeniedException();
        }

        articleRepository.editArticle(id, userId, request.getTitle(), request.getContent());
    }

    /**
     * 게시글 삭제
     * 예외처리 해야 함
     */
    public void deleteArticle(Long id) {
        Long userId = loginService.getLoginUserId();
        // 예외 처리 추가
        Article article = articleRepository.findArticleWithUserBy(id).orElseThrow();

//        if (!userId.equals(article.getUser().getId())) {
//            throw new AccessDeniedException();
//        }

        articleRepository.deleteArticle(id);
    }

}
