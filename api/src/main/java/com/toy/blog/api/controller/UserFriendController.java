package com.toy.blog.api.controller;

import com.toy.blog.api.model.request.UserFriendRequest;
import com.toy.blog.api.model.response.Response;
import com.toy.blog.api.model.response.UserFriendResponse;
import com.toy.blog.api.service.UserFriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-friend")
public class UserFriendController {

    private final UserFriendService userFriendService;

    /**
     * [API. ] : 친구 맺기 or 친구 끊기
     */
    @PostMapping("")
    @PreAuthorize("isAuthenticated()")
    public Response<UserFriendResponse.Info> followFriend(@Validated @RequestBody UserFriendRequest.FollowUserFriend request) {

        return Response.<UserFriendResponse.Info>builder()
                .code(HttpStatus.OK.value())
                .data(userFriendService.follow(request.getFriendId()))
                .build();
    }

    /**
     * [API. ] : 친구 차단 or 차단 해제
     */
    @PatchMapping("")
    @PreAuthorize("isAuthenticated()")
    public Response<UserFriendResponse.Info> blockFriend(@Validated @RequestBody UserFriendRequest.BlockUserFriend request) {

        return Response.<UserFriendResponse.Info>builder()
                .code(HttpStatus.OK.value())
                .data(userFriendService.block(request.getFriendId()))
                .build();
    }

}
