package com.toy.blog.api.service;

import com.toy.blog.domain.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;


    /**
     * [특정 User가 Follow 한 Friend들이 올린 Article List를 조회 하는 서비스]
     * */

}
