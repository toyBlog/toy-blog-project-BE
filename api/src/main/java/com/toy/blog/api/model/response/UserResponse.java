package com.toy.blog.api.model.response;


import com.toy.blog.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class UserResponse {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class UserInfo{

        Long id;

        String email;

        String nickname;

        public static UserInfo of(User user) {

            return UserInfo.builder()
                           .id(user.getId())
                           .email(user.getEmail())
                           .nickname(user.getNickname())
                           .build();
        }

        public static List<UserInfo> of(List<User> userList) {

            return userList.stream()
                    .map(UserInfo::of)
                    .collect(Collectors.toList());
        }
    }
}
