package com.toy.blog.api.service;

import com.toy.blog.api.exception.article.AccessDeniedException;
import com.toy.blog.api.exception.article.NotFoundArticleException;
import com.toy.blog.api.exception.user.NotFoundUserException;
import com.toy.blog.api.model.request.ArticleRequest;
import com.toy.blog.api.model.request.LikedRequest;
import com.toy.blog.api.model.response.ArticleResponse;
import com.toy.blog.auth.service.LoginService;
import com.toy.blog.domain.common.Status;
import com.toy.blog.domain.entity.Article;
import com.toy.blog.domain.entity.Liked;
import com.toy.blog.domain.entity.User;
import com.toy.blog.domain.entity.UserFriend;
import com.toy.blog.domain.repository.ArticleRepository;

import com.toy.blog.domain.repository.LikedRepository;
import com.toy.blog.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final LoginService loginService;

    private final LikedRepository likedRepository;

    private final UserRepository userRepository;
}
