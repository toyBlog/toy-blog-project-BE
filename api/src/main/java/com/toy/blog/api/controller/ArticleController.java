package com.toy.blog.api.controller;

import com.toy.blog.api.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class ArticleController {

    private final ArticleService articleService;

    /**
     * [API. ] : follower들의 게시물 조회
     * */


}
