package com.toy.blog.api.service;

import com.toy.blog.api.exception.user.AlreadyExistUserException;
import com.toy.blog.api.exception.user.InvalidPasswordException;
import com.toy.blog.api.exception.user.NotFoundUserException;
import com.toy.blog.api.model.request.UserRequest;
import com.toy.blog.auth.model.TokenResponseDto;
import com.toy.blog.auth.service.LoginService;
import com.toy.blog.domain.entity.User;
import com.toy.blog.domain.repository.UserFriendRepository;
import com.toy.blog.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final LoginService loginService;
    private final UserRepository userRepository;
    private final UserFriendRepository userFriendRepository;

    public void join(UserRequest.Join request) {
        userRepository.findByEmail(request.getEmail()).ifPresent(user -> {
            throw new AlreadyExistUserException();
        });

        userRepository.save(request.toEntity());
    }

    public TokenResponseDto login(UserRequest.Login request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(NotFoundUserException::new);
        if (!request.getPassword().equals(user.getPassword())) {
            throw new InvalidPasswordException();
        }

        return loginService.generateTokenResponse(user);
    }
}
