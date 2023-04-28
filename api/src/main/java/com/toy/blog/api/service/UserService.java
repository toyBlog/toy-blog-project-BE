package com.toy.blog.api.service;


import com.toy.blog.api.exception.user.AlreadyExistUserException;
import com.toy.blog.api.exception.user.AlreadyWithdrawUserException;
import com.toy.blog.api.exception.user.InvalidPasswordException;
import com.toy.blog.api.exception.user.NotFoundUserException;
import com.toy.blog.api.exception.user_friend.NotFoundUserFriend;
import com.toy.blog.api.model.request.UserRequest;
import com.toy.blog.api.model.response.UserResponse;
import com.toy.blog.auth.model.TokenResponseDto;
import com.toy.blog.auth.service.LoginService;
import com.toy.blog.domain.common.Status;
import com.toy.blog.domain.entity.User;
import com.toy.blog.domain.entity.UserFriend;
import com.toy.blog.domain.repository.UserFriendRepository;
import com.toy.blog.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.toy.blog.domain.common.Status.User.ACTIVE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final LoginService loginService;
    private final UserRepository userRepository;
    private final UserFriendRepository userFriendRepository;


    /**
     * User의 정보를 가져오는 서비스
     */
    public UserResponse.Info getUserInfo() {

        //1. ACTIVE 한 User 조회
        Long userId = loginService.getLoginUserId();
        User user = getUser(userId, Status.User.ACTIVE);

        //2_1. 이 User가 Follow 하는 User들의 IdList 조회
        List<UserFriend> followList = userFriendRepository.findFollowList(userId);
        List<Long> followIdList = followList.stream().map(UserFriend::getFriendId).collect(Collectors.toList());

        //2_2. 이 User를 Follow 하는 User들의 IdList 조회
        List<UserFriend> followingList = userFriendRepository.findFollowingList(userId);
        List<Long> followingIdList = followingList.stream().map(uf -> uf.getUser().getId()).collect(Collectors.toList());

        //2_3. 두 IdList를 비교하여 -> 서로 Follow 하는 IdList를 추출
        List<UserFriend> connectingList = new ArrayList<>();

        for (int i = 0; i < followList.size(); i++) {

            for (int j = 0; j < followingList.size(); j++) {

                if (followList.get(i).isConnect(followingList.get(j))) {
                    connectingList.add(followList.get(i));
                    break;
                }
            }
        }

        List<Long> connectingIdList = connectingList.stream()
                .map(UserFriend::getFriendId)
                .collect(Collectors.toList());

        //3. 응답값 리턴
        return UserResponse.Info.of(user, followIdList, followingIdList, connectingIdList);
    }

    /**
     * [(id, status) 를 가지고 User를 조회하는 private Service 로직]
     */
    private User getUser(Long userId, Status.User status) {

        return userRepository.findByIdAndStatus(userId, status).orElseThrow(NotFoundUserException::new);
    }

    /** --------------------------------------------------------------------------------------------------------------*/

    /**
     * [해당 idList에 해당하는 모든 User들의 정보를 조회해주는 서비스]
     */
    public UserResponse.Search getUserInfoList(List<Long> userIdList, Pageable pageable) {

        //1. 요청을 보낸 User가 ACTIVE한 user인지 판별
        Long userId = loginService.getLoginUserId();

        //인가 검사
        if (!userRepository.existsByIdAndStatus(userId, ACTIVE)) {
            throw new NotFoundUserFriend();
        }

        //2_1. userIdList들에 있는 각 Id를 기반으로 UserList를 조회하여 -> UserInfoList로 변환
        List<User> userList = userRepository.findUserList(userIdList, pageable);

        //2_2. totalCount 조회
        long totalCount = userRepository.countUserList(userIdList);


        //3. 응답값 리턴
        return UserResponse.Search.of(UserResponse.SummaryInfo.of(userList), totalCount);
    }

    @Transactional
    public void join(UserRequest.Join request) {
        userRepository.findByEmail(request.getEmail()).ifPresent(user -> {
            throw new AlreadyExistUserException();
        });

        userRepository.save(request.toEntity());
    }

    @Transactional
    public TokenResponseDto login(UserRequest.Login request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(NotFoundUserException::new);

        if (!user.getStatus().equals(ACTIVE)) {
            throw new AlreadyWithdrawUserException();
        }

        if (!request.getPassword().equals(user.getPassword())) {
            throw new InvalidPasswordException();
        }

        return loginService.generateTokenResponse(user);
    }

}
