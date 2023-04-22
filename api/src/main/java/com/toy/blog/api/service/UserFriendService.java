package com.toy.blog.api.service;

import com.toy.blog.api.exception.user.NotFoundUserException;
import com.toy.blog.api.exception.user_friend.BlockedUserFriendException;
import com.toy.blog.api.model.response.UserFriendResponse;
import com.toy.blog.domain.common.Status;
import com.toy.blog.domain.entity.User;
import com.toy.blog.domain.entity.UserFriend;
import com.toy.blog.domain.repository.UserFriendRepository;
import com.toy.blog.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.toy.blog.domain.common.Status.User.ACTIVE;
import static com.toy.blog.domain.common.Status.UserFriend.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserFriendService {

    private final UserRepository userRepository;
    private final UserFriendRepository userFriendRepository;


    /**
     * [특정 Id의 User를 Follow or UnFollow 하는 서비스]
     *  -> 결과 status를 보내야 , 클라단에서 차단인지 차단 해제인지 알 수 있음
     * */
    @Transactional
    public UserFriendResponse.Detail followFriend(Long userId, Long friendId) {

        //1. ACTIVE 한 User를 조회
        User user = getUser(userId, ACTIVE);

        //2. userId와 friendId를 가지로 기존에 있을 수도 있는 UserFriend를 조회 -> 없으면 조회한 ACTIVE한 User를 가지고 새 UserFriend를 생성
        UserFriend userFriend = userFriendRepository.findByUserIdAndFriendId(userId, friendId).orElse(
                UserFriend.builder().status(UNFOLLOW).user(user).friendId(friendId).build()
        );

        /** 만약 조회한 userFriend가 차단한 userFriend라면 -> 팔로우도 / 언팔로우도 안되는 경우니 Exception */
        if(userFriend.getStatus().equals(BLOCKED)){
            throw new BlockedUserFriendException();
        }

        //3. 누름 효과에 따라 조회한 or 생성한 UserFriend의 상태를 변경 (FOLLOW -> UNFOLLOW / UNFOLLOW -> FOLLOW)
        if (userFriend.getStatus().equals(Status.UserFriend.FOLLOW)) {
            userFriend.changeStatus(UNFOLLOW);
        } else {
            userFriend.changeStatus(FOLLOW);
        }

        //4. save() 를 호출하므로써 update or insert
        userFriendRepository.save(userFriend);

        //5. 결과 리턴
        return UserFriendResponse.Detail.of(userFriend);
    }

    /** [(id, status) 를 가지고 User를 조회하는 private Service 로직]*/
    private User getUser(Long userId, Status.User status){

        return userRepository.findByIdAndStatus(userId, status).orElseThrow(NotFoundUserException::new);
    }

    /** --------------------------------------------------------------------------------------------------------------*/

    /**
     * [특정 userId와 friendId로 이루어진 UserFriend의 팔로우를 "차단 or 차단 해제" 하는 서비스]
     *  -> 결과 status를 보내야 , 클라단에서 차단인지 차단 해제인지 알 수 있음
     * */
    @Transactional
    public UserFriendResponse.Detail blockFriend(Long userId, Long friendId) {

        //1. ACTIVE 한 User를 조회
        User user = getUser(userId, ACTIVE);

        //2. userId의 User가 Friend를 Follow 하여 생성된 UserFriend를 조회 -> 없다면 BLOCKED 상태로 생성  (그래야 팔로우를 하지 않은 상태여도 차단 가능)
        UserFriend userFriend = userFriendRepository.findByUserIdAndFriendId(userId, friendId).orElse(
                UserFriend.builder().status(UNFOLLOW).user(user).friendId(friendId).build()
        );

        //3. BLOCKED 상태가 아니라면 -> BLOCKED 상태로 / BLOCKED 상태라면 -> UNFOLLOW 상태로 변경
        if(userFriend.getStatus().equals(BLOCKED)){
            userFriend.changeStatus(UNFOLLOW);
        } else{
            userFriend.changeStatus(BLOCKED);
        }

        //4. 결과 리턴
        return UserFriendResponse.Detail.of(userFriend);
    }
}
