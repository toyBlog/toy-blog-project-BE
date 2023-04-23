package com.toy.blog.api.controller;

import com.toy.blog.api.model.request.ArticleRequest;
import com.toy.blog.api.model.response.ArticleResponse;
import com.toy.blog.api.model.response.Response;
import com.toy.blog.api.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    /**
     * [API. ] : 게시글 목록 조회
     */
    @GetMapping("/articles")
    public Response<List<ArticleResponse.Search>> getArticles(ArticleRequest.Search request) {
        return Response.<List<ArticleResponse.Search>>builder()
                .code(HttpStatus.OK.value())
                .data(articleService.getArticles(request))
                .build();
    }

    /**
     * [API. ] : 게시글 상세 보기
     */
    @GetMapping("/articles/{id}")
    public Response<ArticleResponse.Detail> getArticle(@PathVariable Long id) {
        return Response.<ArticleResponse.Detail>builder()
                .code(HttpStatus.OK.value())
                .data(articleService.getArticle(id))
                .build();
    }

    /**
     * [API. ] : 글 작성
     * Todo: 이미지 업로드 추가(박수빈)
     */
    @PostMapping("/articles")
    public Response<Void> insertArticle(@RequestBody ArticleRequest.Register request) {
        articleService.insertArticle(request);
        return Response.<Void>builder().build();
    }

    /**
     * [API. ] : 글 수정
     */
    @PatchMapping("/articles/{id}")
    @PreAuthorize("isAuthenticated()")
    public Response<Void> editArticle(@PathVariable Long id, ArticleRequest.Register request) {
        articleService.editArticle(id, request);
        return Response.<Void>builder().build();
    }

    /**
     * [API. ] : 글 삭제
     */
    @DeleteMapping("/articles/{id}")
    @PreAuthorize("isAuthenticated()")
    public Response<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return Response.<Void>builder().build();
    }

    /**
     * [API. ] : 글 검색
     * Todo: 글 검색 구현(박수빈)
     */

    /**
     * [API. ] : 좋아요
     */
    @PostMapping("/articles/{id}")
    public Response<Void> likeArticle(@PathVariable Long id) {
        articleService.likeArticle(id);
        return Response.<Void>builder().build();
    }

    /**
     * [API. ] : 팔로우한 친구의 게시글 목록 조회
     * Todo: 구현(용준님)
     * */

    @GetMapping("/follow/list/{userId}")
    public Response<List<ArticleResponse.Search>> getFollowArticleList(@PathVariable Long userId, Pageable pageable){

        return Response.<List<ArticleResponse.Search>>builder()
                .data(articleService.getFollowArticleList(userId, pageable))
                .code(HttpStatus.OK.value())
                .build();
    }


}
