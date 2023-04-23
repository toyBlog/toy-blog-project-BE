package com.toy.blog.api.controller;

import com.toy.blog.api.model.request.UserFriendRequest;
import com.toy.blog.api.model.response.Response;
import com.toy.blog.api.model.response.UserFriendResponse;
import com.toy.blog.api.service.UserFriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-friend")
public class UserFriendController {

    private final UserFriendService userFriendService;

    /**
     * [API. ] : 친구 맺기 or 친구 끊기
     * */
    @PostMapping("/{userId}")
    public Response<UserFriendResponse.Info> followFriend(@PathVariable Long userId, @Validated @RequestBody UserFriendRequest.FollowUserFriend request) {

        return Response.<UserFriendResponse.Info>builder()
                .data(userFriendService.followFriend(userId, request.getFriendId()))
                .code(HttpStatus.OK.value())
                .build();
    }

    /**
     * [API. ] : 친구 차단 or 차단 해제
     * */
    @PatchMapping("/{userId}")
    public Response<UserFriendResponse.Info> blockFriend(@PathVariable Long userId, @Validated @RequestBody UserFriendRequest.BlockUserFriend request) {

        return Response.<UserFriendResponse.Info>builder()
                .data(userFriendService.blockFriend(userId, request.getFriendId()))
                .code(HttpStatus.OK.value())
                .build();
        }
}
