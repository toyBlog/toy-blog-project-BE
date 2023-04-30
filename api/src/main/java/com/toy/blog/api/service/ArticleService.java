package com.toy.blog.api.service;

import com.toy.blog.api.exception.article.*;
import com.toy.blog.api.exception.file.NotImageFileException;
import com.toy.blog.api.exception.user.NotFoundUserException;
import com.toy.blog.api.model.response.ArticleResponse;
import com.toy.blog.api.model.response.CommentResponse;
import com.toy.blog.api.service.file.FileServiceUtil;
import com.toy.blog.auth.service.LoginService;
import com.toy.blog.domain.common.Status;
import com.toy.blog.domain.entity.*;
import com.toy.blog.domain.repository.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final ArticleImageRepository articleImageRepository;

    private final LoginService loginService;

    private final LikedRepository likedRepository;

    private final UserRepository userRepository;

    private final CommentRepository commentRepository;

    private final FileServiceUtil fileServiceUtil;

    /**
     * [게시글 작성 서비스]
     */
    @Transactional
    public ArticleResponse.BaseResponse register(String title, String content, List<MultipartFile> imageList) {

        //0. 글을 등록할 수 있는 로그인 한 User인지 확인
        Long userId = loginService.getLoginUserId();
        User user = getUser(userId, Status.User.ACTIVE);

        //0_2. 함께 넘어온 파일이 -> 이미지 파일인지 확인 -> 하나라도 이미지 파일이 아닌게 있으면 예외
        if (fileServiceUtil.hasNonImageExt(imageList)) {
            throw new NotImageFileException();
        }

        //1. 들어온 (title, content) 정보를 가지고 Article 엔티티를 생성하여 save
        Article article = Article.builder()
                .title(title)
                .content(content)
                .viewCount(0)
                .user(user)
                .status(Status.Article.ACTIVE)
                .build();

        articleRepository.save(article);

        //1_2. 넘어온 Image 파일이 없다면 그대로 응답 리턴
        if (CollectionUtils.isEmpty(imageList)) {
            return ArticleResponse.BaseResponse.of(article.getId());
        }

        //2. 들어온 imageList정보를 가지고 ArticleImage 엔티티들을 생성하여 save
        List<String> pathList = fileServiceUtil.getPathList(imageList, article.getId());

        List<ArticleImage> articleImageList = pathList.stream()
                .map(path -> ArticleImage.builder().path(path).article(article).status(Status.ArticleImage.ACTIVE).build())
                .collect(Collectors.toList());

        articleImageRepository.saveAll(articleImageList);

        //3. 실제 이미지를 저장
        fileServiceUtil.uploadFileList(pathList, imageList);

        //4. 응답값 리턴
        return ArticleResponse.BaseResponse.of(article.getId());
    }

    /**---------------------------------------------------------------------------------------------------------------*/

    /**
     * 게시글 수정 서비스
     */
    @Transactional
    public ArticleResponse.BaseResponse edit(Long articleId, String title, String content, List<MultipartFile> imageList) {

        /** 0. 검증 로직 */

        //0_1. 글을 수정할 수 있는 로그인 한 User인지 확인
        Long userId = loginService.getLoginUserId();

        //0_2. 넘어온 파일이 모두 이미지 파일인지 확인
        if (fileServiceUtil.hasNonImageExt(imageList)) {
            throw new NotImageFileException();
        }

        //0_3. 글 조회 - 이때 수정하고자 하는 글이 유효한 글인지 확인
        Article article = getArticle(articleId, Status.Article.ACTIVE);

        // 0_4. 이 글을 쓴 작성자와 / 요청한 User가 동일 User인지 확인
        if (!article.getUser().getId().equals(userId)) {
            throw new NoEditPermissionException();
        }

        //1. Article 수정
        article.changeTitleAndContent(title, content);

        //1_2. 이후 새로 넘어온 이미지가 있든 없든 기존의 ArticleImage들을 INACTIVE하게 변경
        article.getArticleImageList()
                .forEach(articleImage -> articleImage.changeStatus(Status.ArticleImage.INACTIVE));

        //1_3. 이후 새로 넘어온 이미지가 없으면 -> 바로 응답 리턴
        if (CollectionUtils.isEmpty(imageList)) {
            return ArticleResponse.BaseResponse.of(article.getId());
        }

        //2. ArticleImage 수정 -> 사실상 새로운 ArticleImage들을 생성 하여 save
        List<String> pathList = fileServiceUtil.getPathList(imageList, article.getId());

        List<ArticleImage> articleImageList = pathList.stream()
                .map(path -> ArticleImage.builder().path(path).article(article).status(Status.ArticleImage.ACTIVE).build())
                .collect(Collectors.toList());

        articleImageRepository.saveAll(articleImageList);

        //3. 실제 이미지 수정 -> 사실상 넘어온 이미지들을 모두 저장 (이떄 저장솟에는 중복된 이미지가 저장되는 상황이 발생하지만 , 일단 그렇게 구현)
        fileServiceUtil.uploadFileList(pathList, imageList);

        //4. 응답 리턴
        return ArticleResponse.BaseResponse.of(article.getId());
    }

    /**---------------------------------------------------------------------------------------------------------------*/

    /**
     * 게시글 삭제 서비스
     */
    @Transactional
    public void remove(Long articleId) {

        /** 0. 검증 로직 */

        //0_1. 글을 수정할 수 있는 로그인 한 User인지 확인
        Long userId = loginService.getLoginUserId();

        //0_2. 글 조회 - 이때 수정하고자 하는 글이 유효한 글인지 확인
        Article article = getArticle(articleId, Status.Article.ACTIVE);

        // 0_3. 이 글을 쓴 작성자와 / 요청한 User가 동일 User인지 확인
        if (!article.getUser().getId().equals(userId)) {
            throw new NoRemovePermissionException();
        }

        //1. Article의 상태를 INACTIVE로 변경
        article.changeStatus(Status.Article.INACTIVE);

        //2. 이 Article과 연관된 ArticleImage들이 있다면 -> 그 이미지들의 상태를 INACTIVE로 변경 (변경감지 말고 - 벌크성 업데이트 활용)
        articleImageRepository.updateStatusByArticleId(Status.ArticleImage.INACTIVE, article.getId());

        //3. 이 Article 과 연관된 Comment 들이 있다면 -> 댓글 상태 INACTIVE 로 변경 (변경감지 말고 - 벌크성 업데이트 활용)
        commentRepository.updateStatusByArticleId(Status.Comment.INACTIVE, article.getId());
    }


    /**---------------------------------------------------------------------------------------------------------------*/


    /**
     * 글 단건 조회 서비스
     */
    public ArticleResponse.Detail getArticle(Long articleId, Pageable pageable) {

        //0. login 한 userId 조회
        /** 로그인 하지 않으면 null이 조회됨 */
        Long userId = loginService.getLoginUserId();

        //1_1. Article 조회 -> 이때 그 글을 쓴 작성자 User도 함께 fetchJoin
        Article article = articleRepository.findByIdAndStatusWithUser(articleId, Status.Article.ACTIVE).orElseThrow(NotFoundArticleException::new);

        //1_2. 좋아요 수 조회
        long likedCount = likedRepository.countByArticleAndStatus(article, Status.Like.ACTIVE);

        //2_1. 그 글에 달린 ACTIVE 한 댓글 조회 - 이때 각 댓글은 그 댓글을 작성한 User를 함께 조회 (fetchJoin)
        List<Comment> commentList = commentRepository.findByArticleIdAndStatusWithUser(articleId, Status.Comment.ACTIVE, pageable);

        //2_2. 그 글에 달린 ACTIVE 한 댓글 개수 조회
        long commentCount = commentRepository.countByArticleIdAndStatus(articleId, Status.Comment.ACTIVE);

        //3_1. 조회한 그 Article과 연관된 ACTIVE 한 ArticleImageList 조회
        List<ArticleImage> articleImageList = articleImageRepository.findByArticleAndStatus(article, Status.ArticleImage.ACTIVE);

        /** CASE1: 이미지가 없는 경우 */
        if (CollectionUtils.isEmpty(articleImageList)) {

            /** CASE 1_1 : 로그인 된 사용자라면 (인증 검증) -> 그 글의 좋아요 여부 + 그 글의 작성 여부 + 그 글에 달린 댓글의 작성 여부 -> 를 함께 고려 */
            if (Objects.nonNull(userId)) {
                User user = getUser(userId, Status.User.ACTIVE); /** 차단된 사용라면 -> 여기서 걸림 (인가 검증) */
                boolean isLiked = likedRepository.existsByUserAndAndArticleAndStatus(user, article, Status.Like.ACTIVE);
                return getArticleInfo(article, user.getId(), isLiked, likedCount, commentList, commentCount, new ArrayList<>());
            }

            /** CASE 1_2 : 로그인 된 사용자가 아닌 경우 */
            return getArticleInfo(article, likedCount, commentList, commentCount, new ArrayList<>());
        }

        /** CASE 2 : 이미지가 있는 경우 */
        //3_2. 이미지가 하나라도 있으면 -> 그 ArticleImage들 안에있는 path를 가지고 -> urlList를 만들어서 -> 함께 반환
        List<String> pathList = articleImageList.stream().map(ArticleImage::getPath).collect(Collectors.toList());
        List<String> imageUrlList = fileServiceUtil.getImageUrlList(pathList);

        /** CASE 2_1 : 로그인 된 사용자라면 (인증 검증) -> 그 글의 좋아요 여부 + 그 글의 작성 여부 + 그 글에 달린 댓글의 작성 여부 -> 를 함께 고려 */
        if (Objects.nonNull(userId)) {
            User user = getUser(userId, Status.User.ACTIVE); /** 차단된 사용라면 -> 여기서 걸림 (인가 검증) */
            boolean isLiked = likedRepository.existsByUserAndAndArticleAndStatus(user, article, Status.Like.ACTIVE);
            return getArticleInfo(article, user.getId(), isLiked, likedCount, commentList, commentCount, imageUrlList);
        }

        /** CASE 2_2: 로그인 된 사용자가 아닌 경우 */
        return getArticleInfo(article, likedCount, commentList, commentCount, imageUrlList);
    }

    /**---------------------------------------------------------------------------------------------------------------*/

    /**
     * 글 목록 조회 + keyword 에 따른 검색 기능 추가 제공
     */
    public ArticleResponse.Search getArticleList(String keyword, Integer page, Integer size) {

        //0. 로그인 한 User의 Id 가져옴
        Long userId = loginService.getLoginUserId();

        //1_1. 넘어온 값에 따른 ArticleList 조회
        /** 혹시나 null이 들어오는 경우 대비*/
        if (Objects.isNull(keyword)) {
            keyword = "";
        }

        List<Article> articleList = articleRepository.findByTitleOrContent(keyword, page, size);

        //1_2. totalCount 조회
        long totalCount = articleRepository.countByTitleOrContent(keyword);

        //1_3. 각 Article별로 로그인 한 그 User가 이 Article에 대해 좋아요를 눌렀는지의 여부를 가져옴 (ACTIVE 한 User가 로그인 한 경우에 한함)
        List<Boolean> isLikedList = new ArrayList<>();

        if (Objects.nonNull(userId)) {
            User user = getUser(userId, Status.User.ACTIVE);
            isLikedList = articleList.stream()
                    .map(article -> likedRepository.existsByUserAndAndArticleAndStatus(user, article, Status.Like.ACTIVE))
                    .collect(Collectors.toList());
        } else {
            for (int i = 0; i < articleList.size(); i++) {
                isLikedList.add(false); /** 로그인 하지 않았다면 , 좋아요 여부는 모두 false */
            }
        }

        //1_4. 각 Article 별로 좋아요 개수를 가져옴
        List<Long> likedCountList = articleList.stream()
                .map(article -> likedRepository.countByArticleAndStatus(article, Status.Like.ACTIVE))
                .collect(Collectors.toList());


        //2. 결과 반환
        return ArticleResponse.Search.of(ArticleResponse.Summary.of(articleList, isLikedList, likedCountList), totalCount);
    }

    /**
     * 특정 User가 Follow 한 Friend들이 올린 Article List를 조회 하는 서비스
     */

    public ArticleResponse.Search getFollowArticleList(Pageable pageable) {

        //1.로그인 한 ACTIVE 한 user 조회
        Long userId = loginService.getLoginUserId();
        User user = getUser(userId, Status.User.ACTIVE);

        //2_1. 그 user가 follow 한 friendIdList 조회 후
        List<Long> friendIdList = user.getUserFriendList().stream()
                .filter(userFriend -> userFriend.getStatus().equals(Status.UserFriend.FOLLOW))
                .map(UserFriend::getFriendId)
                .collect(Collectors.toList());


        //2_2 in절을 사용하여 하여 그 friend들이 쓴 articleList 조회
        List<Article> articleList = articleRepository.findFollowArticleList(friendIdList, pageable);

        //2_3. 총 articleList의 개수 조회
        long totalCount = articleRepository.countFollowArticleList(friendIdList);

        //2_4. articleList의 각 Article 별로 -> 좋아요 여부 조회
        User finalUser = user;
        List<Boolean> isLikedList = articleList.stream()
                .map(article -> likedRepository.existsByUserAndAndArticleAndStatus(finalUser, article, Status.Like.ACTIVE))
                .collect(Collectors.toList());

        //2_5. articleList의 각 Article 별로 -> 좋아요 개수 조회
        List<Long> likedCountList = articleList.stream()
                .map(article -> likedRepository.countByArticleAndStatus(article, Status.Like.ACTIVE))
                .collect(Collectors.toList());


        //3. 조회한 값 리턴
        return ArticleResponse.Search.of(ArticleResponse.Summary.of(articleList, isLikedList, likedCountList), totalCount);

    }


    /**---------------------------------------------------------------------------------------------------------------*/

    /**
     * 좋아요 증가/취소
     * 좋아요 테이블 저장/삭제 처리
     */

    @Transactional
    public void like(Long articleId) {

        //1. 로그인 한 Active한 사용자인지 판별
        Long userId = loginService.getLoginUserId();
        User user = getUser(userId, Status.User.ACTIVE);

        //2. 좋아요를 생성 또는 조회
        Article article = getArticle(articleId, Status.Article.ACTIVE); // 삭제된 게시물에는 좋아요를 할 수 없게 막는 효과도 존재
        Liked liked = likedRepository.findByUserAndArticle(user, article).orElse(
                Liked.builder().user(user).article(article).status(Status.Like.INACTIVE).build()
        );

        //3. 좋아요 누름에 따라 -> INACTIVE는 ACTIVE 하게 / ACTIVE는 INACTIVE 하게
        if (liked.getStatus().equals(Status.Like.INACTIVE)) {
            liked.changeStatus(Status.Like.ACTIVE);
        } else {
            liked.changeStatus(Status.Like.INACTIVE);
        }

        //4. insert or update
        likedRepository.save(liked);
    }

    public ArticleResponse.Search getLikeArticleList(Pageable pageable) {

        //1. 로그인 한 ACTIVE 회원인지 검증 및 조회
        Long userId = loginService.getLoginUserId();
        User user = getUser(userId, Status.User.ACTIVE);

        //2_1. 이 회원이 좋아요 한 articleList 조회
        List<Article> articleList = likedRepository.findArticleListByUserId(userId, pageable);

        //2_2. 이 회원 이 좋아요 한 articleList의 개수 조회
        long totalCount = likedRepository.countArticleListByUserId(userId);

        //2_3. 각 article별로 좋아요 여부 조회 (당연히 true 이긴 할테지만 - 확실하게 하기 위해)
        User finalUser = user;
        List<Boolean> isLikedList = articleList.stream()
                .map(article -> likedRepository.existsByUserAndAndArticleAndStatus(finalUser, article, Status.Like.ACTIVE))
                .collect(Collectors.toList());

        //2_4. 각 article 별로 좋아요 개수 조회
        List<Long> likedCountList = articleList.stream()
                .map(article -> likedRepository.countByArticleAndStatus(article, Status.Like.ACTIVE))
                .collect(Collectors.toList());

        //3. 응답 리턴
        return ArticleResponse.Search.of(ArticleResponse.Summary.of(articleList, isLikedList, likedCountList), totalCount);
    }

    /**
     * 댓글 기능 구현
     * 1. 댓글 작성
     * 2. 댓글 조회 ( 조회한 게시글 번호에 맞는 댓글 조회 ) -> 게시글 조회 시 같이 조회 -> 후에 게시글 [더보기]를 통해 다시 조회
     * 3. 댓글 수정 ( 댓글을 작성한 유저만 수정 가능 )
     * 4. 댓글 삭제 ( 댓글 또한 Status 를 DELETE 로 변경 후 삭제 )
     */

    @Transactional
    public CommentResponse.BaseResponse registerComment(String content, Long articleId) {

        //1_1. ACTIVE한 User 조회    -> 탈퇴환 회원이 아니어야 함 (어차피 loginService에서 보장 해줌)
        Long userId = loginService.getLoginUserId();
        User user = getUser(userId, Status.User.ACTIVE);

        //1_2. ACTIVE한 Article 조회 -> 삭제되 글이 아니어야 함
        Article article = getArticle(articleId, Status.Article.ACTIVE);

        //2. User, Article, content를 가지고 Comment를 생성하여 save
        Comment comment = Comment.builder()
                .content(content)
                .user(user)
                .article(article)
                .status(Status.Comment.ACTIVE)
                .build();

        commentRepository.save(comment);

        //3. 응답 리턴
        return CommentResponse.BaseResponse.of(comment.getId());
    }

    /**
     * 로그인 여부를 따져줘야 함
     */
    public CommentResponse.Search getCommentList(Long articleId, Pageable pageable) {

        //1_1. userId 조회
        Long userId = loginService.getLoginUserId();

        //1_2. Article의 삭제 여부 확인
        if (!articleRepository.existsByIdAndStatus(articleId, Status.Article.ACTIVE)) {
            throw new NotFoundArticleException();
        }

        //2_1. commentList 조회
        List<Comment> commentList = commentRepository.findByArticleIdAndStatusWithUser(articleId, Status.Comment.ACTIVE, pageable);

        //2_2. totalCount 조회
        long totalCount = commentRepository.countByArticleIdAndStatus(articleId, Status.Comment.ACTIVE);

        //3_1. 응답 리턴 - 로그인 한 경우
        if (Objects.nonNull(userId)) {
            CommentResponse.Search search = getCommentInfo(commentList, totalCount, userId);
            return search;
        }

        //3_2. 응답 리턴 - 로그인 하지 않은 경우
        CommentResponse.Search search = getCommentInfo(commentList, totalCount);
        return search;
    }

    @Transactional
    public CommentResponse.BaseResponse editComment(String content, Long articleId, Long commentId) {

        //1_1. userId 조회
        Long userId = loginService.getLoginUserId();

        //1_2. 글의 삭제 여부 검증
        if (!articleRepository.existsByIdAndStatus(articleId, Status.Article.ACTIVE)) {
            throw new NotFoundArticleException();
        }

        //1_3. 삭제된 댓글은 아닌지 검증 -> ACTIVE 한 댓글 조회
        Comment comment = commentRepository.findByIdAndStatusWithUser(commentId, Status.Comment.ACTIVE).orElseThrow(NotFoundCommentsException::new);

        //1_4. 이 댓글의 작성자가 맞는지 검증
        if (!comment.getUser().getId().equals(userId)) {
            throw new AccessDeniedException();
        }

        //2. 댓글 내용 수정
        comment.changeContent(content);

        //3. 응답
        return CommentResponse.BaseResponse.of(comment.getId());
    }

    @Transactional
    public void removeComment(Long articleId, Long commentId) {

        //1_1. userId 조회
        Long userId = loginService.getLoginUserId();

        //1_2. 글의 삭제 여부 검증
        if (!articleRepository.existsByIdAndStatus(articleId, Status.Article.ACTIVE)) {
            throw new NotFoundArticleException();
        }

        //1_3. 삭제된 댓글은 아닌지 검증 -> ACTIVE 한 댓글 조회
        Comment comment = commentRepository.findByIdAndStatusWithUser(commentId, Status.Comment.ACTIVE).orElseThrow(NotFoundCommentsException::new);

        //1_4. 이 댓글의 작성자가 맞는지 검증
        if (!comment.getUser().getId().equals(userId)) {
            throw new AccessDeniedException();
        }

        //2. 댓글 삭제 (논리적 삭제)
        comment.changeStatus(Status.Comment.INACTIVE);
    }


    /**---------------------------------------------------------------------------------------------------------------*/


    /**
     * [(id, status) 를 가지고 User를 조회하는 private Service 로직]
     */
    private User getUser(Long userId, Status.User status) {

        return userRepository.findByIdAndStatus(userId, status).orElseThrow(NotFoundUserException::new);
    }

    private Article getArticle(Long id, Status.Article status) {

        return articleRepository.findByIdAndStatus(id, status).orElseThrow(NotFoundArticleException::new);
    }


    /**
     * 로그인 하지 않는 경우
     */
    private CommentResponse.Search getCommentInfo(List<Comment> commentList, long commentCount) {

        List<CommentResponse.Detail> commentDetailList = commentList.stream().map(comment -> CommentResponse.Detail.of(comment)).collect(Collectors.toList());
        return CommentResponse.Search.of(commentDetailList, commentCount);
    }

    /**
     * 로그인 한 경우
     */
    private CommentResponse.Search getCommentInfo(List<Comment> commentList, long commentCount, Long authorId) {

        List<CommentResponse.Detail> commentDetailList = commentList.stream().map(comment -> CommentResponse.Detail.of(comment, authorId)).collect(Collectors.toList());
        return CommentResponse.Search.of(commentDetailList, commentCount);
    }


    /**
     * 로그인 하지 않은 경우
     */
    private ArticleResponse.Detail getArticleInfo(Article article, long likedCount, List<Comment> commentList, long commentCount, List<String> urlList) {

        CommentResponse.Search commentInfo = getCommentInfo(commentList, commentCount);
        return ArticleResponse.Detail.of(article, likedCount, commentInfo, urlList);
    }

    /**
     * 로그인 한 경우
     */
    private ArticleResponse.Detail getArticleInfo(Article article, Long authorId, boolean isliked, long likedCount, List<Comment> commentList, long commentCount, List<String> urlList) {

        CommentResponse.Search commentInfo = getCommentInfo(commentList, commentCount, authorId);
        return ArticleResponse.Detail.of(article, authorId, isliked, likedCount, commentInfo, urlList);
    }

    @Transactional
    public void viewCountUp(Long id) {
        articleRepository.findById(id).orElseThrow(NotFoundArticleException::new);
        articleRepository.updateViewCount(id);
    }
}
