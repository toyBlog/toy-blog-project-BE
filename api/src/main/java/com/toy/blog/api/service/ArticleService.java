package com.toy.blog.api.service;

import com.toy.blog.api.exception.article.AccessDeniedException;
import com.toy.blog.api.exception.article.NotFoundArticleException;
import com.toy.blog.api.model.request.ArticleRequest;
import com.toy.blog.api.model.request.LikedRequest;
import com.toy.blog.api.model.response.ArticleResponse;
import com.toy.blog.auth.service.LoginService;
import com.toy.blog.domain.entity.Article;
import com.toy.blog.domain.entity.User;
import com.toy.blog.domain.entity.UserFriend;
import com.toy.blog.domain.repository.ArticleRepository;
import com.toy.blog.domain.entity.Liked;
import com.toy.blog.domain.repository.ArticleRepository;
import com.toy.blog.domain.repository.LikedRepository;
import com.toy.blog.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
     */
    public List<ArticleResponse.Search> getArticles(ArticleRequest.Search request) {
        List<Article> articles = articleRepository.getArticleList(request.getPage(), request.getSize());

        return ArticleResponse.Search.of(articles);
    }

    /**
     * 게시글 상세 조회
     */
    public ArticleResponse.Detail getArticle(Long id) {
        Long userId = loginService.getLoginUserId();
        Article article = articleRepository.findArticleById(id).orElseThrow(NotFoundArticleException::new);

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
     * Todo: 이미지 업로드 추가(박수빈)
     */
    public void insertArticle(ArticleRequest.Register request) {
        Long userId = loginService.getLoginUserId();
        articleRepository.save(request.toEntity(userId));
    }

    /**
     * 게시글 수정
     */
    public void editArticle(Long id, ArticleRequest.Register request) {
        Long userId = loginService.getLoginUserId();
        Article article = articleRepository.findArticleById(id).orElseThrow(NotFoundArticleException::new);

        if (!userId.equals(article.getUser().getId())) {
            throw new AccessDeniedException();
        }

        articleRepository.editArticle(id, request.getTitle(), request.getContent());
    }

    /**
     * 게시글 삭제
     */
    public void deleteArticle(Long id) {
        Long userId = loginService.getLoginUserId();
        Article article = articleRepository.findArticleById(id).orElseThrow(NotFoundArticleException::new);

        if (!userId.equals(article.getUser().getId())) {
            throw new AccessDeniedException();
        }

        articleRepository.deleteArticle(id);
    }

    /**
     * 좋아요 증가/취소
     * 좋아요 테이블 저장/삭제 처리
     * Todo: 좋아요 수정(박수빈)
     */
    public void likeArticle(Long id) {
        Long userId = loginService.getLoginUserId();
        Liked liked = likedRepository.findByArticleAndUser(id, userId).orElseThrow();

        if (likedRepository.findByArticleAndUser(id, userId).isEmpty()) { /** 처음 좋아요 누르는 경우 */
            // article 테이블의 좋아요 +1
            articleRepository.updateLikedCount(id, 1);
            // liked 테이블 생성 (ACTIVE)
            likedRepository.save(new LikedRequest.Register().toEntity(id, userId));
        } else { /** 좋아요 취소 */
            // article 테이블의 좋아요 -1
            articleRepository.updateLikedCount(id, -1);
            // liked 테이블 상태 변경 (INACTIVE)
            likedRepository.deleteLiked(liked.getId());
        }
    }

    /**
     * [특정 User가 Follow 한 Friend들이 올린 Article List를 조회 하는 서비스]
     * Todo: 구현(용준님^^)
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
