package com.toy.blog.api.service;

import com.toy.blog.api.exception.article.AccessDeniedException;
import com.toy.blog.api.exception.article.NotFoundArticleException;
import com.toy.blog.api.exception.liked.NotFoundLikedException;
import com.toy.blog.api.model.request.ArticleRequest;
import com.toy.blog.api.model.request.LikedRequest;
import com.toy.blog.api.model.response.ArticleResponse;
import com.toy.blog.auth.service.LoginService;
import com.toy.blog.domain.entity.Article;
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

        // 조회수 증가
        if (!userId.equals(article.getUser().getId())) {
            articleRepository.updateViewCount(id);
        }

        return ArticleResponse.Detail.of(article);
    }

    /**
     * 게시글 작성
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
     */
    public void likeArticle(Long id) {
        Long userId = loginService.getLoginUserId();

        if (likedRepository.findByArticleAndUser(id, userId).isEmpty()) { /** 처음 좋아요 누르는 경우 */
            // article 테이블의 좋아요 +1
            articleRepository.updateLikedCount(id, 1);
            // liked 테이블 생성 (ACTIVE)
            likedRepository.save(new LikedRequest.Register().toEntity(id, userId));
        } else { /** 좋아요 취소 */
            Liked liked = likedRepository.findByArticleAndUser(id, userId).orElseThrow(NotFoundLikedException::new);
            // article 테이블의 좋아요 -1
            articleRepository.updateLikedCount(id, -1);
            // liked 테이블 상태 변경 (INACTIVE)
            likedRepository.deleteLiked(liked.getId());
        }
    }

    /**
     * [특정 User가 Follow 한 Friend들이 올린 Article List를 조회 하는 서비스]
     * To do 구현
     * */
//    public List<ArticleResponse.Search> getFollowArticles(Long userId, Pageable pageable) {
//
//        //1.ACTIVE 한 user 조회
//        User user = getUser(userId, Status.User.ACTIVE);
//
//        //2_1. 그 user가 follow 한 friendIdList 조회 후
//        List<Long> friendIdList = user.getUserFriendList().stream()
//                .map(UserFriend::getFriendId)
//                .collect(Collectors.toList());
//
//
//        //2_2 in절을 사용하여 하여 그 friend들이 쓴 articleList 조회
//        List<Article> articleSummaryDtoList = articleRepository.getFollowArticleList(friendIdList, pageable);
//
//
//        //3. 조회한 값 리턴
//        return articleSummaryDtoList.stream().map(ArticleResponse.Detail::of).collect(Collectors.toList());
//    }
//
//    /**
//     * [(id, status) 를 가지고 User를 조회하는 private Service 로직]
//     */
//    private User getUser(Long userId, Status.User status) {
//
//        return userRepository.findByIdAndStatus(userId, status).orElseThrow(NotFoundUserException::new);
//    }
}
