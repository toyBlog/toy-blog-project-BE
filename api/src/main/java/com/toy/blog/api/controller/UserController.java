package com.toy.blog.api.controller;

import com.toy.blog.api.model.response.Response;
import com.toy.blog.api.model.response.UserFriendResponse;
import com.toy.blog.api.model.response.UserResponse;
import com.toy.blog.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private UserService userService;


    /**
     * [API. ] : 내가 follow 하는 친구 숫자 + 나를 follow 하는 친구 숫자 + 서로 follow 하는 친구 숫자 조회
     * */

}
