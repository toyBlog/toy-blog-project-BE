package com.toy.blog.api.controller;

import com.toy.blog.api.model.request.ArticleRequest;
import com.toy.blog.api.model.response.ArticleResponse;
import com.toy.blog.api.model.response.Response;
import com.toy.blog.api.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    /**
     * [API. ] : 게시글 목록 조회
     */
    @GetMapping("")
    public Response<List<ArticleResponse.Summary>> getArticles(ArticleRequest.Inventory request) {
        return Response.<List<ArticleResponse.Summary>>builder()
                .code(HttpStatus.OK.value())
                .data(articleService.getArticles(request.getPage(), request.getSize()))
                .build();
    }

    /**
     * [API. ] : 팔로우한 친구의 게시글 목록 조회
     */
    @GetMapping("/follower/{userId}")
    public Response<ArticleResponse.Search> getFollowArticles(@PathVariable Long userId, Pageable pageable) {

        return Response.<ArticleResponse.Search>builder()
                .data(articleService.getFollowArticleList(userId, pageable))
                .code(HttpStatus.OK.value())
                .build();
    }

    /**
     * [API. ] : 글 검색
     */
    @GetMapping("/search")
    public Response<List<ArticleResponse.Summary>> getSearchArticles(ArticleRequest.Search request) {
        return Response.<List<ArticleResponse.Summary>>builder()
                .code(HttpStatus.OK.value())
                .data(articleService.getSearchArticles(request.getKeyword(), request.getPage(), request.getSize()))
                .build();
    }

    /**
     * [API. ] : 게시글 상세 보기
     */
    @GetMapping("/{id}")
    public Response<ArticleResponse.Detail> getArticle(@PathVariable Long id) {
        return Response.<ArticleResponse.Detail>builder()
                .code(HttpStatus.OK.value())
                .data(articleService.getArticle(id))
                .build();
    }

    /**
     * [API. ] : 글 작성
     *
     * [수정사항]
     * insertArticle -> registerArticle <insert는 쿼리에서 사용되는 용어이므로 배제>
     */
    @PostMapping("")
    public Response<Void> registerArticle(@RequestBody ArticleRequest.Register request) {
        articleService.registerArticle(request);
        return Response.<Void>builder().build();
    }

    /**
     * [API. ] : 글 수정
     */
    @PatchMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public Response<Void> editArticle(@PathVariable Long id, ArticleRequest.Register request) {
        articleService.editArticle(id, request);
        return Response.<Void>builder().build();
    }

    /**
     * [API. ] : 글 삭제
     */
    @PostMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public Response<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return Response.<Void>builder().build();
    }

    /**
     * [API. ] : 좋아요
     */
    @PostMapping("/likes/{id}")
    @PreAuthorize("isAuthenticated()")
    public Response<Void> likeArticle(@PathVariable Long id) {
        articleService.likeArticle(id);
        return Response.<Void>builder().build();
    }

}
