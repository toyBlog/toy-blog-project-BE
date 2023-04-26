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
     * [API. ] : 글 작성
     * Todo: 이미지 업로드 추가(박수빈)
     */

    @PostMapping("")
    @PreAuthorize("isAuthenticated()")
    public Response<ArticleResponse.BaseResponse> registerArticle(@Validated @ModelAttribute ArticleRequest.Register request) {

        return Response.<ArticleResponse.BaseResponse>builder()
                .code(HttpStatus.CREATED.value())
                .data(articleService.register(request.getTitle(), request.getContent(), request.getImageList()))
                .build();
    }


    /**
     * [API. ] : 글 수정
     */
    @PatchMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public Response<ArticleResponse.BaseResponse> editArticle(@PathVariable Long id, @ModelAttribute ArticleRequest.Edit request) {

        return Response.<ArticleResponse.BaseResponse>builder()
                .code(HttpStatus.CREATED.value())
                .data(articleService.edit(id, request.getTitle(), request.getContent(), request.getImageList()))
                .build();
    }


    /**
     * [API. ] : 글 삭제
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public Response<Void> removeArticle(@PathVariable Long id) {
        articleService.remove(id);
        return Response.<Void>builder().build();
    }


    /**
     * [API. ] : 글 단건 조회
     * */
    @GetMapping("/{id}")
    public Response<ArticleResponse.Detail> getArticle(@PathVariable Long id) {

        return Response.<ArticleResponse.Detail>builder()
                .code(HttpStatus.OK.value())
                .data(articleService.getArticle(id))
                .build();
    }


    /**
     * [API. ] : 게시글 목록 조회
     */
    @GetMapping("")
    public Response<ArticleResponse.Search> getArticleList(@ModelAttribute ArticleRequest.Inventory request) {
        return Response.<ArticleResponse.Search>builder()
                .code(HttpStatus.OK.value())
                .data(articleService.getArticleList("", request.getPage(), request.getSize()))
                .build();
    }


    /**
     * [API. ] : 글 검색
     * Todo: 글 검색 구현(박수빈)
     */

    @GetMapping("/search")
    public Response<ArticleResponse.Search> searchArticleList(@Validated @ModelAttribute ArticleRequest.Search request) {

        return Response.<ArticleResponse.Search>builder()
                .code(HttpStatus.OK.value())
                .data(articleService.getArticleList(request.getKeyword(), request.getPage(), request.getSize()))
                .build();
    }

    /**
     * [API. ] : 팔로우한 친구의 게시글 목록 조회
     * Todo: 구현(용준님)
     * */

    @GetMapping("/follower")
    @PreAuthorize("isAuthenticated()")
    public Response<ArticleResponse.Search> getFollowArticles(Pageable pageable){

        return Response.<ArticleResponse.Search>builder()
                .code(HttpStatus.OK.value())
                .data(articleService.getFollowArticleList(pageable))
                .build();
    }


    /**
     * [API. ] : 좋아요
     */
    @PostMapping("/likes/{id}")
    @PreAuthorize("isAuthenticated()")
    public Response<Void> likeArticle(@PathVariable Long id) {
        articleService.like(id);
        return Response.<Void>builder().build();
    }

    /**
     * [API. ] : 좋아요 한 게시글 목록 조회
     * */
    @GetMapping("/likes")
    @PreAuthorize("isAuthenticated()")
    public Response<ArticleResponse.Search> getLikeArticleList(Pageable pageable) {

        return Response.<ArticleResponse.Search>builder()
                .code(HttpStatus.OK.value())
                .data(articleService.getLikeArticleList(pageable))
                .build();
    }
}
