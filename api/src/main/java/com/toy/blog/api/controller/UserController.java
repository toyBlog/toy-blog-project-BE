package com.toy.blog.api.controller;

import com.toy.blog.api.model.request.UserRequest;
import com.toy.blog.api.model.response.Response;
import com.toy.blog.api.model.response.UserFriendResponse;
import com.toy.blog.api.model.response.UserResponse;
import com.toy.blog.api.service.UserService;
import com.toy.blog.auth.model.TokenResponseDto;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    /**
     * [API. ] : 마이페이지의 User정보 조회 -> 이 떄 (내가 follow 하는 친구 숫자 + 나를 follow 하는 친구 숫자 + 서로 follow 하는 친구 숫자 조회) 를 함께 조회
     * */
    @GetMapping("/info")
    @PreAuthorize("isAuthenticated()")
    public Response<UserResponse.Info> getUserInfo() {

        return Response.<UserResponse.Info>builder()
                       .code(HttpStatus.OK.value())
                       .data(userService.getUserInfo())
                       .build();
    }

    /**
     * [API. ] : 내가 follow 하는 or 나를 follow 하는 or 서로 follow 하는 ->  친구들의 IdList가 넘어오면 -> 그 친구듪의 정보들을 반환해줌
     * */
    @GetMapping("/info/follower")
    @PreAuthorize("isAuthenticated()")
    public Response<UserResponse.Search> getUserInfoList(@ModelAttribute UserRequest.UserIdList request, Pageable pageable) {

        return Response.<UserResponse.Search>builder()
                .code(HttpStatus.OK.value())
                .data(userService.getUserInfoList(request.getUserIdList(), pageable))
                .build();
    }

    @PostMapping("/join")
    public Response<Void> join(@Validated @RequestBody UserRequest.Join request) {
        userService.join(request);
        return Response.<Void>builder()
                .code(HttpStatus.OK.value())
                .build();
    }

    @PostMapping("/login")
    public Response<TokenResponseDto> login(@Validated @RequestBody UserRequest.Login request) {
        return Response.<TokenResponseDto>builder()
                .code(HttpStatus.OK.value())
                .data(userService.login(request))
                .build();

    }
}
