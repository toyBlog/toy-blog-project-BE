package com.toy.blog.api.controller;

import com.toy.blog.api.model.request.UserRequest;
import com.toy.blog.api.model.response.Response;
import com.toy.blog.api.model.response.UserFriendResponse;
import com.toy.blog.api.model.response.UserResponse;
import com.toy.blog.api.service.UserService;
import com.toy.blog.auth.model.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;


    /**
     * [API. ] : 내가 follow 하는 친구 숫자 + 나를 follow 하는 친구 숫자 + 서로 follow 하는 친구 숫자 조회
     * */

    @PostMapping("/join")
    public Response<Void> join(UserRequest.Join request) {
        userService.join(request);
        return Response.<Void>builder()
                .code(HttpStatus.OK.value())
                .build();
    }

    @PostMapping("/login")
    public Response<TokenResponseDto> login(UserRequest.Login request) {
        return Response.<TokenResponseDto>builder()
                .code(HttpStatus.OK.value())
                .data(userService.login(request))
                .build();
    }
}
