package com.toy.blog.api.service;

import com.toy.blog.api.exception.user.NotFoundUserException;
import com.toy.blog.api.model.response.ArticleResponse;
import com.toy.blog.domain.common.Status;
import com.toy.blog.domain.entity.Article;
import com.toy.blog.domain.entity.User;
import com.toy.blog.domain.entity.UserFriend;
import com.toy.blog.domain.repository.ArticleRepository;
import com.toy.blog.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.toy.blog.domain.common.Status.User.ACTIVE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;


    /**
     * [특정 User가 Follow 한 Friend들이 올린 Article List를 조회 하는 서비스]
     * */
    public List<ArticleResponse.Search> getFollowArticleList(Long userId, Pageable pageable) {

        //1.ACTIVE 한 user 조회
        User user = getUser(userId, ACTIVE);

        //2_1. 그 user가 follow 한 friendIdList 조회 후
        List<Long> friendIdList = user.getUserFriendList().stream()
                                      .filter(userFriend -> userFriend.getStatus().equals(Status.UserFriend.FOLLOW))
                                      .map(UserFriend::getFriendId)
                                      .collect(Collectors.toList());


        //2_2 in절을 사용하여 하여 그 friend들이 쓴 articleList 조회
        List<Article> articleList = articleRepository.findFollowArticleList(friendIdList, pageable);


        //3. 조회한 값 리턴
        return ArticleResponse.Search.of(articleList);
    }

    /** [(id, status) 를 가지고 User를 조회하는 private Service 로직]*/
    private User getUser(Long userId, Status.User status){

        return userRepository.findByIdAndStatus(userId, status).orElseThrow(NotFoundUserException::new);
    }
}
