package com.toy.blog.api.controller;

import com.toy.blog.api.model.request.ArticleRequest;
import com.toy.blog.api.model.response.ArticleResponse;
import com.toy.blog.api.model.response.Response;
import com.toy.blog.api.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @PatchMapping("/{articleId}")
    @PreAuthorize("isAuthenticated()")
    public Response<ArticleResponse.BaseResponse> editArticle(@PathVariable Long articleId, @ModelAttribute ArticleRequest.Edit request) {

        return Response.<ArticleResponse.BaseResponse>builder()
                .code(HttpStatus.CREATED.value())
                .data(articleService.edit(articleId, request.getTitle(), request.getContent(), request.getImageList()))
                .build();
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
     * [API. ] : 글 삭제
     */

    @PostMapping("/{articleId}")
    @PreAuthorize("isAuthenticated()")
    public Response<Void> removeArticle(@PathVariable Long articleId) {
        articleService.remove(articleId);
        return Response.<Void>builder().build();
    }


    /**
     * [API. ] : 글 단건 조회
     * */
    @GetMapping("/{articleId}")
    public Response<ArticleResponse.Detail> getArticle(@PathVariable Long articleId) {

        return Response.<ArticleResponse.Detail>builder()
                .code(HttpStatus.OK.value())
                .data(articleService.getArticle(articleId))
                .build();
    }


    /**
     * [API. ] : 게시글 목록 조회
     */
    @GetMapping("")
    public Response<ArticleResponse.Search> getArticles(@ModelAttribute ArticleRequest.Inventory request) {
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
     
    @PostMapping("/likes/{articleId}")
    @PreAuthorize("isAuthenticated()")
    public Response<Void> likeArticle(@PathVariable Long articleId) {
        articleService.like(articleId);
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
    




    /**
     * 요청 순서
     * 1. 클라이언트 요청이 들어옴
     * 2. 요청에 Cookie 가 "없고" 글을 조회한다면 [게시글 ID] 값 추가하여 Cookie 생성
     * 3. 요청에 Cookie 가 "있고" 글을 조회한 기록이 있다면 pass 없다면 Cookie 에 [게시글 ID] 붙이기
     */

    private void viewCountUp(Long id, HttpServletRequest request, HttpServletResponse response) {
        Cookie oldCookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("articleView")) {
                    oldCookie = cookie;
                    break;
                }
            }
        }
        if (oldCookie == null) {
            // 새로운 쿠키를 생성하여 추가
            Cookie newCookie = new Cookie("articleView", "[" + id + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(7200);
            response.addCookie(newCookie);
            articleService.viewCountUp(id);
        } else {
            // 기존 쿠키에 해당 게시글 번호가 포함되어 있지 않으면 조회수 증가 및 쿠키 값 갱신
            if (!oldCookie.getValue().contains("[" + id.toString() + "]")) {
                oldCookie.setValue(oldCookie.getValue() + "_[" + id + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(7200);
                response.addCookie(oldCookie);
                articleService.viewCountUp(id);
            }
        }

    }


}
