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

    /**
     * 게시글 목록 조회
     *
     * [수정 사항]
     * getArticleList() -> findArticleList()
     * 파라미터 ArticleRequest.Inventory request -> Integer page, Integer size
     */
    public List<ArticleResponse.Summary> getArticles(Integer page, Integer size) {
        List<Article> articles = articleRepository.findArticleList(page, size);

        return ArticleResponse.Summary.of(articles);
    }

    /**
     * 팔로우한 친구의 게시글 목록 조회
     * [특정 User가 Follow 한 Friend들이 올린 Article List를 조회 하는 서비스]
     */

    public ArticleResponse.Search getFollowArticleList(Long userId, Pageable pageable) {

        //1.ACTIVE 한 user 조회
        User user = getUser(userId, Status.User.ACTIVE);

        //2_1. 그 user가 follow 한 friendIdList 조회 후
        List<Long> friendIdList = user.getUserFriendList().stream()
                .filter(userFriend -> userFriend.getStatus().equals(Status.UserFriend.FOLLOW))
                .map(UserFriend::getFriendId)
                .collect(Collectors.toList());


        //2_2 in절을 사용하여 하여 그 friend들이 쓴 articleList 조회
        List<Article> articleList = articleRepository.findFollowArticleList(friendIdList, pageable);

        //2_3. 총 articleList의 개수 조회
        long totalCount = articleRepository.findFollowArticleListTotal(friendIdList);


        //3. 조회한 값 리턴
        return ArticleResponse.Search.of(ArticleResponse.Summary.of(articleList), totalCount);

    }

    /**
     * [(id, status) 를 가지고 User를 조회하는 private Service 로직]
     */
    private User getUser(Long userId, Status.User status) {

        return userRepository.findByIdAndStatus(userId, status).orElseThrow(NotFoundUserException::new);
    }

    /**
     * 게시글 검색
     */
    public List<ArticleResponse.Summary> getSearchArticles(String keyword, Integer page, Integer size) {
        List<Article> articles = articleRepository.findSearchArticleList(keyword, page, size);

        return ArticleResponse.Summary.of(articles);
    }

    /**
     * 게시글 상세 조회
     *
     * [수정 사항]
     * findArticleById() -> findByIdAndStatus()  <동적 쿼리도 아니니깐 굳이 querydsl을 쓰기보다는 spring data jpa 를 쓰는게 훨씬 깔끔 + 네이밍 부터 findArticleById() 라고 인위적이므로 - 차라리 쉽게 있는 기능 쓰자!>
     */
    public ArticleResponse.Detail getArticle(Long id) {
        Long userId = loginService.getLoginUserId();
        Article article = articleRepository.findByIdAndStatus(id, Status.Article.ACTIVE).orElseThrow(NotFoundArticleException::new);

        // 조회수 증가 Todo: 조회수 증가 로직 고민
        if (!userId.equals(article.getUser().getId())) {
            articleRepository.updateViewCount(id);
        } else if (userId == null) {
            articleRepository.updateViewCount(id);
        }

        return ArticleResponse.Detail.of(article);
    }

    /**
     * 게시글 작성
     *
     * [수정 사항]
     * insertArticle -> registerArticle <insert 용어 자체가 쿼리에서 사용되는 용어 이므로>
     */
    public void registerArticle(ArticleRequest.Register request) {
        Long userId = loginService.getLoginUserId();
        articleRepository.save(request.toEntity(userId));
    }

    /**
     * 게시글 수정
     *
     * [수정 사항]
     * findArticleById() -> findByIdAndStatus()  <동적 쿼리도 아니니깐 굳이 querydsl을 쓰기보다는 spring data jpa 를 쓰는게 훨씬 깔끔 + 네이밍 부터 findArticleById() 라고 인위적이므로 - 차라리 쉽게 있는 기능 쓰자!>
     * editArticle() -> updateArticle() <repository의 수정 네이밍은 update를 쓰는게 맞고 , 서비스 수정 네이밍은 edit를 쓰는게 더 적절해 보임>
     */
    public void editArticle(Long id, ArticleRequest.Register request) {
        Long userId = loginService.getLoginUserId();
        Article article = articleRepository.findByIdAndStatus(id, Status.Article.ACTIVE).orElseThrow(NotFoundArticleException::new);

        if (!userId.equals(article.getUser().getId())) {
            throw new AccessDeniedException();
        }

        articleRepository.updateArticle(id, request.getTitle(), request.getContent());
    }

    /**
     * 게시글 삭제
     *
     * [수정 사항]
     * deleteArticle() -> inactiveArticle() <실제로 delete할떄 delete라는 네이밍을 써야 함 / 그래서 inactiveArticle() 로 변경>
     */
    public void deleteArticle(Long id) {
        Long userId = loginService.getLoginUserId();
        Article article = articleRepository.findArticleById(id).orElseThrow(NotFoundArticleException::new);

        if (!userId.equals(article.getUser().getId())) {
            throw new AccessDeniedException();
        }

        articleRepository.inactiveArticle(id);
    }

    /**
     * 좋아요
     */
    public void likeArticle(Long id) {
        //
    }

}
