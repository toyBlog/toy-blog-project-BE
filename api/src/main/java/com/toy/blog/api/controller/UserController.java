package com.toy.blog.api.controller;

import com.toy.blog.api.model.request.UserRequest;
import com.toy.blog.api.model.response.Response;
import com.toy.blog.api.model.response.UserFriendResponse;
import com.toy.blog.api.model.response.UserResponse;
import com.toy.blog.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;


    /**
     * [API. ] : 마이페이지의 User정보 조회 -> 이 떄 (내가 follow 하는 친구 숫자 + 나를 follow 하는 친구 숫자 + 서로 follow 하는 친구 숫자 조회) 를 함께 조회
     * */
    @GetMapping("/info/{userId}")
    public Response<UserResponse.Info> getUserInfo(@PathVariable Long userId) {

        return Response.<UserResponse.Info>builder()
                       .data(userService.getUserInfo(userId))
                       .code(HttpStatus.OK.value())
                       .build();
    }

    /**
     * [API. ] : 내가 follow 하는 or 나를 follow 하는 or 서로 follow 하는 ->  친구들의 IdList가 넘어오면 -> 그 친구듪의 정보들을 반환해줌
     * */
    @GetMapping("/info/list/{userId}")
    public Response<List<UserResponse.SummaryInfo>> getUserInfoList(@PathVariable Long userId, @ModelAttribute UserRequest.UserIdList request, Pageable pageable) {

        return Response.<List<UserResponse.SummaryInfo>>builder()
                       .data(userService.getUserInfoList(userId, request.getUserIdList(), pageable))
                       .code(HttpStatus.OK.value())
                       .build();
    }
}
