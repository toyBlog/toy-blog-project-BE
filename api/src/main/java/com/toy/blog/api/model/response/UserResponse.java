package com.toy.blog.api.model.response;


import com.toy.blog.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class UserResponse {

    @Getter
    @Setter
    @SuperBuilder
    @AllArgsConstructor
    public static class UserBase{

        Long id;

        String email;

        String nickname;

    }

    @Getter
    @Setter
    @SuperBuilder
    public static class Detail extends UserBase {

        public static Detail of(User user) {

           return Detail.builder()
                   .id(user.getId())
                   .email(user.getEmail())
                   .nickname(user.getNickname())
                   .build();
        }

        public static List<Detail> of(List<User> userList) {

            return userList.stream().map(Detail::of).collect(Collectors.toList());
        }
    }

}
