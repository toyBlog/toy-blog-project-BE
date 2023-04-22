package com.toy.blog.api.controller;

import com.toy.blog.api.model.response.ArticleResponse;
import com.toy.blog.api.model.response.Response;
import com.toy.blog.api.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    /**
     * [API. ] : follower들의 게시물 조회
     * */
//    @GetMapping("/follow/list/{userId}")
//    public Response<List<ArticleResponse.Search>> getFollowArticleList(@PathVariable Long userId, Pageable pageable){
//
//        return Response.<List<ArticleResponse.Search>>builder()
//                .data(articleService.getFollowArticleList(userId, pageable))
//                .code(HttpStatus.OK.value())
//                .build();
//    }


}
