package com.toy.blog.api.model.response;

import com.toy.blog.domain.common.Status;
import com.toy.blog.domain.entity.UserFriend;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserFriendResponse {

    @Getter
    @Setter
    @SuperBuilder
    @AllArgsConstructor
    public static class UserFriendBase {

        Long id;

        Status.UserFriend status;
    }

    @Getter
    @Setter
    @SuperBuilder
    public static class Info extends UserFriendBase{

        public static Info of(UserFriend userFriend) {

            return Info.builder()
                    .id(userFriend.getId())
                    .status(userFriend.getStatus())
                    .build();
        }
    }

}
