package com.toy.blog.api.controller;

import com.toy.blog.api.model.request.ArticleRequest;
import com.toy.blog.api.model.response.ArticleResponse;
import com.toy.blog.api.model.response.Response;
import com.toy.blog.api.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
                //.data(articleService.getArticles(request))
                .data(null)
                .build();
    }


    /**
     * [API. ] : follower들의 게시물 조회
     * */
    @GetMapping("/articles/follow")
    public Response<List<ArticleResponse.Search>> getFollowedArticles(ArticleRequest.Search request) {
        return Response.<List<ArticleResponse.Search>>builder()
                .code(HttpStatus.OK.value())
                //.data(articleService.getArticles(request))
                .data(null)
                .build();
    }

    /**
     * [API. ] : 게시글 상세 보기
     */
    @GetMapping("/articles/{id:[\\d]+}")
    public Response<ArticleResponse.Detail> getArticle(@PathVariable Long id) {
        return Response.<ArticleResponse.Detail>builder()
                .code(HttpStatus.OK.value())
                .data(articleService.getArticle(id))
//                .data(null)
                .build();
    }

    /**
     * [API. ] : 글 작성
     */
    @PostMapping("/articles")
    public void insertArticle(@RequestBody ArticleRequest.Register request) {
        articleService.insertArticle(request);
    }

    /**
     * [API. ] : 글 수정
     */
    @PatchMapping("/articles/{id:[\\d]+}")
    public Response<Void> editArticle(@PathVariable Long id, ArticleRequest.Register request) {
        articleService.editArticle(id, request);
        return Response.<Void>builder().build();
    }

    /**
     * [API. ] : 글 삭제
     */
    @DeleteMapping("/articles/{id:[\\d]+}")
    public Response<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return Response.<Void>builder().build();
    }


    /**
     * [API. ] : 조회수
     */
    @GetMapping("/boards/{id:[\\d]+}")
    public Response<BoardResponse.Detail> getBoard(@PathVariable Long id) {
        return Response.<BoardResponse.Detail>builder()
                .code(HttpStatus.OK.value())
                .data(boardService.getBoard(id))
                .build();
    }

    /**
     * [API. ] : 좋아요
     */
    @GetMapping("/boards/{id:[\\d]+}")
    public Response<BoardResponse.Detail> getBoard(@PathVariable Long id) {
        return Response.<BoardResponse.Detail>builder()
                .code(HttpStatus.OK.value())
                .data(boardService.getBoard(id))
                .build();
    }

    /**
     * [API. ] : 페이징
     */
    @GetMapping("/boards/{id:[\\d]+}")
    public Response<BoardResponse.Detail> getBoard(@PathVariable Long id) {
        return Response.<BoardResponse.Detail>builder()
                .code(HttpStatus.OK.value())
                .data(boardService.getBoard(id))
                .build();
    }



}
